package pt.up.fe.ssin.androidsecuremesh.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

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
import pt.up.fe.ssin.androidsecuremesh.ui.MainMenu;

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
	public static final int CryptedChatNameSize = PacketFactory.CryptedChatNameSize;
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
		
		chatName = CryptoUtils.sanitize(chatName);
		userName = CryptoUtils.sanitize(userName);
		
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
		userName = CryptoUtils.sanitize(userName);
		chatName = CryptoUtils.sanitize(chatName);
		
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
			if(user.name.equals(userName))
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
		chatName = chatName.replaceAll("\u0000.*", "");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Chat chat: Login.main.getChatList())
			if(chat.getName().equals(theChat.getName()))
				for(User user: chat.getUsersList())
					if(user.name.equals(userName))
					{
						chat.removeFromUsersList(user);
						break;
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
					if(user.name.equals(userName))
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
		
		try
		{
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
				
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
		if(ChatUsersList.usersList.isEmpty())
			canBeSecondOwner = false;
		
		boolean isThereAnyOtherSecondOwner = false;
		
		for(User user: Login.main.getUserList())
			for(Chat chat: user.nextOwnedChats)
				if(chat.name.equals(chatName))
					isThereAnyOtherSecondOwner = true;
		
		
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
		
		
		if(!MainMenu.userName.equals(userName) && ChatUsersList.usersList != null && chatName.equals(EnterChatRoom.chosenChat.getName()))
		{
			NewChatUserAsyncTask sendChatTextAsyncTask = new NewChatUserAsyncTask();
			sendChatTextAsyncTask.execute(userName);
		}
		
	}


	private static void sendTextToChat(byte[] packet) {
		byte[] usernameByte = new byte[ReversePacketFactory.UserNameSize];
		for(int i=(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize); i<(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize + ReversePacketFactory.UserNameSize ); i++)
			usernameByte[i -(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize)] = packet[i];

		
		
		new ProcessTextChatPacket().execute(packet, usernameByte);
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
		IP = CryptoUtils.sanitize(IP);
		Chat newChat = new Chat(chatName);
		newChat.ownerIp = IP;
		
		for(Chat chat: Login.main.getChatList())
			if(chat.getName().equals(chatName))
				return;
		
		Login.main.addToChatList(newChat);
		
		if(EnterChatRoom.chatList != null)
		{
			
			new NewChatAsyncTask().execute(newChat);
		}

		
	}


	public static void getTCPPacketDataById(int parseInt, String pckt, String remoteIP) {
		switch (parseInt)
		{
		case 0xDEADBEEF: //chat key request
			Log.e("HELLO CRYPTO", "Handshake 2 begin");
			String[] args = pckt.split("\\|"); 
			String publicKey = null;
			String chatName = args[2];
			String username = args[3];
			publicKey = pckt.substring(pckt.indexOf("|" + username + "|") + username.length() + 2, pckt.indexOf("@|@|@|@")); //I HATE MYSELF
			
			PublicKey actualKey = null;
			try {
				actualKey = 
					    KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey.getBytes("ISO-8859-1")));
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			byte[] chatKey = Main.getChatByName(chatName).getKey().getEncoded();
			
			Cipher rsaCipher = null;
			try {
				rsaCipher = Cipher.getInstance("RSA", "SC");
				rsaCipher.init(Cipher.ENCRYPT_MODE, actualKey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			byte[] cipherText = null;
			try {
				cipherText = rsaCipher.doFinal(chatKey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} 
			
		
			
			String newPacket = PacketFactory.createChatAcceptance(cipherText) + "@|@|@|@" + remoteIP;
			SendTCPThread.textList.add(newPacket);
			Log.e("HELLO CRYPTO", "Handshake 2 end");
			break;
		}
		
	}


}
