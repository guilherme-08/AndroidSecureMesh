package pt.up.fe.ssin.androidsecuremesh.utils;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

import pt.up.fe.ssin.androidsecuremesh.ui.ChatConversation;
import pt.up.fe.ssin.androidsecuremesh.ui.EnterChatRoom;
import pt.up.fe.ssin.androidsecuremesh.ui.Login;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class ReversePacketFactory {

	private static final int NameSize = PacketFactory.NameSize;
	private static final int IPSize = PacketFactory.IPSize;
	private static final int TextSize = PacketFactory.TextSize;
	public static Context ctx = null;
	private static final int IntSize = PacketFactory.IntSize;
	private static boolean once = false;
	private static int UserNameSize = PacketFactory.UserNameSize;
	static final int ChatNameSize = PacketFactory.ChatNameSize;

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
			ChatNameByte = new byte[ChatNameSize];
			
			IPByte = new byte[IPSize];

			for(int i=IntSize; i<(IntSize + ChatNameSize); i++)
				ChatNameByte[i - IntSize] = packet[i];

			for(int i=(IntSize + ChatNameSize); i<(IntSize + ChatNameSize + IPSize); i++)
				IPByte[i -(IntSize + ChatNameSize)] = packet[i];



			chatName = new String(ChatNameByte);
			IP = new String(IPByte);
			Login.main.addToChatList(new Chat(chatName));
//			Toast toast = Toast.makeText(ctx, "ID: " + chatName + "Name: " + Ip, Toast.LENGTH_LONG);
			//toast.show()
			break;
		case 3:
			ChatNameByte = new byte[ChatNameSize];
			
			byte[] UserNameByte = new byte[UserNameSize];
			
			//TODO keys missing
			
			TextByte = new byte[TextSize];
			
			for(int i=IntSize; i<(IntSize + ChatNameSize); i++)
				ChatNameByte[i - IntSize] = packet[i];
			
			for(int i=(IntSize + ChatNameSize); i<(IntSize + ChatNameSize + UserNameSize); i++)
				UserNameByte[i -(IntSize + ChatNameSize)] = packet[i];
			
			
			for(int i=(IntSize + ChatNameSize + UserNameSize); i<(IntSize + ChatNameSize + UserNameSize + TextSize); i++)
				TextByte[i -(IntSize + ChatNameSize + UserNameSize)] = packet[i];
			
			
			chatName = new String(ChatNameByte);
			String userName = new String(UserNameByte);
			Text = new String(TextByte);
			
			if(chatName.equals(EnterChatRoom.chosenChat.getName()))
			{
				ChatConversation.messagesList.add(Text);
			}
			
			
			break;
		}



		/*Toast toast = Toast.makeText(ctx, "ID: " + ID + "Name: " + Name + "IP: " + IP, Toast.LENGTH_LONG);
		toast.show();*/

	}


}
