package pt.up.fe.ssin.androidsecuremesh.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.openssl.PEMWriter;
import org.spongycastle.util.io.pem.PemObject;


public class PacketFactory {

	static final int NameSize = 64;
	static final int UserNameSize = 64;
	static final int ChatNameSize = 32;
	static final int IntSize = 4;
	static final int IPSize = 15;
	static final int TextSize = 256;



	public static DatagramPacket newChatPacket(String chatName, InetAddress host, int port)
	{
		String IP = Storage.getIPAddress(true);
		if(IP == null)
			return null;

		if(IP.length() <= IPSize)
			IP += '|';

		byte[] packet = new byte[IntSize + ChatNameSize + IPSize];
		ByteBuffer byteBuffer = ByteBuffer.wrap(packet);

		byte[] chatNameByte = new byte[ChatNameSize];
		ByteBuffer byteBufferChatName = ByteBuffer.wrap(chatNameByte);
		byteBufferChatName.put(chatName.getBytes());

		byte[] IPByte = new byte[IPSize];
		ByteBuffer byteBufferIP = ByteBuffer.wrap(IPByte);
		byteBufferIP.put(IP.getBytes());

		byteBuffer.putInt(2);
		byteBuffer.put(chatNameByte);
		byteBuffer.put(IPByte);


		DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length, host, port);

		return datagramPacket;

	}


	static final int ChatKeySize = 256;
	public static final int CHAT_SIZE = NameSize + ChatKeySize + TextSize + IPSize + IntSize;

	public static DatagramPacket createTextPacket(String name, String text, PublicKey ownerKey, SecretKeySpec chatKey, InetAddress host, int port)
	{
		String IP = Storage.getIPAddress(true);
		if(IP == null)
			return null;

		if(IP.length() <= IPSize)
			IP += '|';

		byte[] packet = new byte[IntSize + NameSize + TextSize + ChatKeySize + IPSize];
		ByteBuffer byteBuffer = ByteBuffer.wrap(packet);

		byte[] nameByte = new byte[NameSize];
		ByteBuffer byteBufferName = ByteBuffer.wrap(nameByte);
		byteBufferName.put(name.getBytes());


		byte[] textByte = new byte[TextSize];
		ByteBuffer byteBufferText = ByteBuffer.wrap(textByte);
		byteBufferText.put(text.getBytes());

		byte[] IPByte = new byte[IPSize];
		ByteBuffer byteBufferIP = ByteBuffer.wrap(IPByte);
		byteBufferIP.put(IP.getBytes());
		
		byte[] chatKeyByte = new byte[ChatKeySize];
		ByteBuffer.wrap(chatKeyByte).put(chatKey.getEncoded());
		
		CryptoUtils.encrypt(ownerKey, chatKeyByte);
		

		byteBuffer.putInt(1);
		byteBuffer.put(nameByte);
		byteBuffer.put(textByte);
		byteBuffer.put(IPByte);
		byteBuffer.put(chatKeyByte);
		
		DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length, host, port);

		return datagramPacket;
	}

	}


	public static DatagramPacket sendTextToChat(String chatName, String text, String userName, String key, InetAddress host, int port) //notString
	{
		byte[] packet = new byte[IntSize + ChatNameSize + UserNameSize + TextSize];
		ByteBuffer byteBuffer = ByteBuffer.wrap(packet);

		
		byte[] chatNameByte = new byte[ChatNameSize];
		ByteBuffer byteBufferChatName = ByteBuffer.wrap(chatNameByte);
		byteBufferChatName.put(chatName.getBytes());
		

		byte[] userNameByte = new byte[NameSize];
		ByteBuffer byteBufferName = ByteBuffer.wrap(userNameByte);
		byteBufferName.put(userName.getBytes());


		byte[] textByte = new byte[TextSize];
		ByteBuffer byteBufferText = ByteBuffer.wrap(textByte);
		byteBufferText.put(text.getBytes());

		
		
		byteBuffer.putInt(3);
		byteBuffer.put(chatNameByte);
		byteBuffer.put(userNameByte);
		byteBuffer.put(textByte);


		DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length, host, port);

		return datagramPacket;
	}
}
