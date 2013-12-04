package pt.up.fe.ssin.androidsecuremesh.utils;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class PacketFactory {
	
	static final int NameSize = 64;
	static final int IntSize = 4;
	static final int IPSize = 15;
	static final int TextSize = 256;
	
	public static DatagramPacket createTextPacket(String name, String text, AsymmetricKeyParameter userKey, SecretKeySpec chatKey, InetAddress host, int port)
	{
		String IP = Storage.getIPAddress(true);
		if(IP == null)
			return null;
		
		if(IP.length() <= IPSize)
			IP += '|';
		
		byte[] packet = new byte[IntSize + NameSize + TextSize + IPSize];
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
		
		//TODO putKeys
		
		byteBuffer.putInt(1);
		byteBuffer.put(nameByte);
		byteBuffer.put(textByte);
		byteBuffer.put(IPByte);
		
	
		DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length, host, port);
		
		return datagramPacket;
	}
	
}
