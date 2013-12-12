package pt.up.fe.ssin.androidsecuremesh.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.spongycastle.util.Arrays;



import pt.up.fe.ssin.androidsecuremesh.ui.ChatConversation;
import pt.up.fe.ssin.androidsecuremesh.ui.ChatUsersList;
import pt.up.fe.ssin.androidsecuremesh.ui.EnterChatRoom;
import pt.up.fe.ssin.androidsecuremesh.ui.Login;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class ReversePacketFactory {

	static final int chatKeySize = PacketFactory.ChatKeySize;
	static final int NameSize = PacketFactory.NameSize;
	 static final int IPSize = PacketFactory.IPSize;
	static final int TextSize = PacketFactory.TextSize;
	public static Context ctx = null;
	static final int IntSize = PacketFactory.IntSize;
	private static boolean once = false;
	static int UserNameSize = PacketFactory.UserNameSize;
	static final int ChatNameSize = PacketFactory.ChatNameSize;
	public static boolean existsUser = false;
	public static boolean answer = false; 

	//String name, String text, AsymmetricKeyParameter userKey, SecretKeySpec chatKey, InetAddress host, int port
	public static void getPacketText(DatagramPacket datagramPacket)
	{
		if(!once)
		{
			Looper.prepare();
			once = true;
		}
		byte[] packet = datagramPacket.getData();

		ByteBuffer byteBuffer = ByteBuffer.wrap(packet);

		int ID = byteBuffer.getInt(0);
		Log.i("HELLO REC", "Id is:" + ID);
		getPacketDataByID(packet, ID);		
	}


	public static void getPacketDataByID(byte[] packet, int iD) {
		byte[] ChatNameByte; 
		byte[] IPByte;
		byte[] TextByte;
		String IP;
		String chatName;
		String Text;

		switch(iD)
		{
		case 1: 
			byte[] NameByte = new byte[NameSize];
			//TODO miss Keys
			IPByte = new byte[IPSize];
			TextByte = new byte[TextSize];

			for(int i=IntSize; i<(IntSize + NameSize); i++)
				NameByte[i - IntSize] = packet[i];

			for(int i=(IntSize + NameSize); i<(IntSize + NameSize + TextSize); i++)
				TextByte[i -(IntSize + NameSize)] = packet[i];

			for(int i=(IntSize + NameSize + TextSize); i<(IntSize + NameSize + TextSize + IPSize); i++)
				IPByte[i - (IntSize + NameSize + TextSize)] = packet[i];


			String Name = new String(NameByte);
			Text = new String(TextByte);
			IP = new String(IPByte);
			break;

		case 2: 
			newChatRequest(packet);
			break;

		case 3: 
			sendTextToChat(packet);
			break;
		case 4:
			newChatUser(packet);
			break;
		case 5:
			deleteChatRequest(packet);
			break;
		case 6:
			deleteChatUser(packet);
			break;
		case 7:
			newMeshUser(packet);
			break;
		case 8:
			changeUserRating(packet);
			break;
		case 9:
			secondOwnerRequest(packet);
			break;
		case 10:
			quitSecondOwner(packet);
			break;
		}

	}


	private static void quitSecondOwner(byte[] packet) {
		
		byte[] ChatNameByte = new byte[ChatNameSize];
		byte[] UserNameByte = new byte[UserNameSize];
		

		for(int i=IntSize; i<(IntSize + ChatNameSize); i++)
			ChatNameByte[i - IntSize] = packet[i];
		
		for(int i=(IntSize + ChatNameSize); i< (IntSize + UserNameSize + ChatNameSize); i++)
			UserNameByte[i-(IntSize+ChatNameSize)] = packet[i];
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String chatName = new String(ChatNameByte);
		String userName = new String(UserNameByte);
		
		User user = Login.main.getUserByUsername(userName);
		Chat chat = Login.main.getChatByName(chatName);
		
		user.nextOwnedChats.remove(chat);
		
	}


	private static void secondOwnerRequest(byte[] packet) {
		
		byte[] ChatNameByte = new byte[ChatNameSize];
		byte[] UserNameByte = new byte[UserNameSize];
		

		for(int i=IntSize; i<(IntSize + ChatNameSize); i++)
			ChatNameByte[i - IntSize] = packet[i];
		
		for(int i=(IntSize + ChatNameSize); i< (IntSize + UserNameSize + ChatNameSize); i++)
			UserNameByte[i-(IntSize+ChatNameSize)] = packet[i];
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String chatName = new String(ChatNameByte);
		String userName = new String(UserNameByte);
		
		User user = Login.main.getUserByUsername(userName);
		Chat chat = Login.main.getChatByName(chatName);
		
		user.nextOwnedChats.add(chat);
		
	}


	private static void changeUserRating(byte[] packet) {
		
		byte[] UserNameByte = new byte[UserNameSize];
		byte[] UserRatingByte = new byte[IntSize];
		

		for(int i=IntSize; i<(IntSize + UserNameSize); i++)
			UserNameByte[i - IntSize] = packet[i];
		
		for(int i=(IntSize + UserNameSize); i< (IntSize + UserNameSize + IntSize); i++)
			UserRatingByte[i-(IntSize+UserNameSize)] = packet[i];
		
		int rating = ByteBuffer.wrap(UserRatingByte).getInt();
		String userName = new String(UserNameByte);
		userName = userName.replaceAll("\u0000.*", "");
	/*	for(User user: Login.main.getUserList())
		{
			if(user.getName().equals(userName))
			{
				answer = true;
				existsUser = true;
				return;
			}
		}*/
		
		Login.main.changeUserRating(userName, rating);
		
	}


	private static void newMeshUser(byte[] packet) {

		byte[] UserNameByte = new byte[UserNameSize];
		byte[] UserRatingByte = new byte[IntSize];
		

		for(int i=IntSize; i<(IntSize + UserNameSize); i++)
			UserNameByte[i - IntSize] = packet[i];
		
		for(int i=(IntSize + UserNameSize); i< (IntSize + UserNameSize + IntSize); i++)
			UserRatingByte[i-(IntSize+UserNameSize)] = packet[i];
		
		int rating = ByteBuffer.wrap(UserRatingByte).getInt();
		String userName = new String(UserNameByte);
		userName = userName.replaceAll("\u0000.*", "");
	/*	for(User user: Login.main.getUserList())
		{
			if(user.getName().equals(userName))
			{
				answer = true;
				existsUser = true;
				return;
			}
		}*/
		
		for(User user: Login.main.getUserList())
			if(user.getName().equals(userName))
				return;
		
		Login.main.addToUserList(userName, rating);
		
	}


	private static void deleteChatUser(byte[] packet) {
		byte[] ChatNameByte = new byte[ChatNameSize];
		byte[] UserNameByte = new byte[UserNameSize];
		

		for(int i=IntSize; i<(IntSize + ChatNameSize); i++)
			ChatNameByte[i - IntSize] = packet[i];

		for(int i=(IntSize+ChatNameSize); i<(IntSize+ChatNameSize+UserNameSize); i++)
			UserNameByte[i-(IntSize+ChatNameSize)] = packet[i];
		

		String chatName = new String(ChatNameByte);
		Chat theChat = new Chat(chatName);
		String userName = new String(UserNameByte);
		
		userName = userName.replaceAll("\u0000.*", "");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Chat chat: Login.main.getChatList())
			if(chat.getName().equals(theChat.getName()))
				for(User user: chat.getUsersList())
					if(user.getName().equals(userName))
					{
						chat.removeFromUsersList(user);
						return;
					}
		
		if(ChatUsersList.usersList != null && EnterChatRoom.chosenChat != null && chatName.equals(EnterChatRoom.chosenChat.getName()))
		{
			DeleteChatUserAsyncTask deleteChatUserAsyncTask = new DeleteChatUserAsyncTask();
			deleteChatUserAsyncTask.execute(userName);
		}
		
	}


	private static void deleteChatRequest(byte[] packet) {
		
		byte[] ChatNameByte = new byte[ChatNameSize];
		
		for(int i=IntSize; i<(IntSize + ChatNameSize); i++)
			ChatNameByte[i - IntSize] = packet[i];

		String chatName = new String(ChatNameByte);
		CryptoUtils.sanitize(chatName);
		CryptoUtils.sanitize(chatName);
		Chat theChat = new Chat(chatName);
		
		Login.main.deleteChatItem(theChat);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(EnterChatRoom.chatList != null)
			new DeleteChatAsyncTask().execute(theChat);
		
	}


	private static void newChatUser(byte[] packet) {
		byte[] ChatNameByte = new byte[ChatNameSize];

		byte[] UserNameByte = new byte[UserNameSize];

		for(int i=IntSize; i<(IntSize + ChatNameSize); i++)
			ChatNameByte[i - IntSize] = packet[i];

		for(int i=(IntSize + ChatNameSize); i<(IntSize + ChatNameSize + UserNameSize); i++)
			UserNameByte[i -(IntSize + ChatNameSize)] = packet[i];


		String chatName = new String(ChatNameByte);
		String userName = new String(UserNameByte);
		chatName = CryptoUtils.sanitize(chatName);
		userName = CryptoUtils.sanitize(userName);
		
		

		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		userName = userName.replaceAll("\u0000.*", "");
		for(Chat chat: Login.main.getChatList())
			if(chat.getName().equals(chatName))
				for(User user: Login.main.getUserList())
					if(user.getName().equals(userName))
						chat.addToUsersList(user);
	
	
		
//		User theUser = null;
//	
//				theUser = user;
//		
//		theChat.addToUsersList(theUser);
		
		
		
		boolean canBeSecondOwner = true;
		
		
		for(User user: Login.main.getUserList())
			for(Chat chat: user.ownedChats)
				if(chat.name.equals(chatName))
					canBeSecondOwner = false;
		
		
		for(User user: Login.main.getUserList()) 
			if(user.getName().equals(userName))
				for(User user2: ChatUsersList.usersList)
				{
					if(!user2.getName().equals(user))
					{
						if(user2.rating > user.rating)
							canBeSecondOwner = false;
						else if(user2.rating == user.rating && user2.getName().length() > user.getName().length())
							canBeSecondOwner = false;
					}
				}
				
		
		boolean isThereAnyOtherSecondOwner = true;
		
		for(User user: Login.main.getUserList())
			for(Chat chat: user.nextOwnedChats)
				if(chat.name.equals(chatName))
					isThereAnyOtherSecondOwner = false;
		
		
		if(canBeSecondOwner)
		{
			while (SendDataThread.inetAddress == null)
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			DatagramPacket datagram = PacketFactory.secondOwner(chatName, userName, SendDataThread.inetAddress, SendDataThread.port);
			
			SendDataThread.datagramsArray.add(datagram);
		}
		
		
		if(isThereAnyOtherSecondOwner)
		{
			while (SendDataThread.inetAddress == null)
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			DatagramPacket datagram = PacketFactory.quitSecondOwner(chatName, userName, SendDataThread.inetAddress, SendDataThread.port);
			
			SendDataThread.datagramsArray.add(datagram);
		}
		
		
		if(ChatUsersList.usersList != null && chatName.equals(EnterChatRoom.chosenChat.getName()))
		{
			NewChatUserAsyncTask sendChatTextAsyncTask = new NewChatUserAsyncTask();
			sendChatTextAsyncTask.execute(userName);
		}
		
	}


	private static void sendTextToChat(byte[] packet) {
		new ProcessTextChatPacket().execute(packet);
	}


	private static void newChatRequest(byte[] packet) {

		byte[] ChatNameByte = new byte[ChatNameSize];

		byte[] IPByte = new byte[IPSize];

		for(int i=IntSize; i<(IntSize + ChatNameSize); i++)
			ChatNameByte[i - IntSize] = packet[i];

		for(int i=(IntSize + ChatNameSize); i<(IntSize + ChatNameSize + IPSize); i++)
			IPByte[i -(IntSize + ChatNameSize)] = packet[i];



		String chatName = new String(ChatNameByte);
		chatName = CryptoUtils.sanitize(chatName);
		
		String IP = new String(IPByte);
		Chat newChat = new Chat(chatName);
		
		for(Chat chat: Login.main.getChatList())
			if(chat.getName().equals(chatName))
				return;
		
		Login.main.addToChatList(newChat);

		if(EnterChatRoom.chatList != null)
		{
			NewChatAsyncTask newChatAsyncTask = new NewChatAsyncTask();
			newChatAsyncTask.execute(newChat);
		}
		//		Toast toast = Toast.makeText(ctx, "ID: " + chatName + "Name: " + Ip, Toast.LENGTH_LONG);
		//toast.show()

	}


}
