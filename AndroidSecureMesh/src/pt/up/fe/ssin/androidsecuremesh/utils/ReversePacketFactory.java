package pt.up.fe.ssin.androidsecuremesh.utils;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class ReversePacketFactory {
	
	private static final int NameSize = PacketFactory.NameSize;
	private static final int IPSize = PacketFactory.IPSize;
	private static final int TextSize = PacketFactory.TextSize;
	public static Context ctx = null;
	private static final int IntSize = PacketFactory.IntSize;

	
//String name, String text, AsymmetricKeyParameter userKey, SecretKeySpec chatKey, InetAddress host, int port
	public static void getPacketText(DatagramPacket datagramPacket)
	{
		Looper.prepare();
		byte[] packet = datagramPacket.getData();
		
		byte[] NameByte = new byte[NameSize];
		//TODO miss Keys
		byte[] IPByte = new byte[IPSize];
		byte[] TextByte = new byte[TextSize];
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(packet);
		
		for(int i=IntSize; i<(IntSize + NameSize); i++)
			NameByte[i - IntSize] = packet[i];
	
		for(int i=(IntSize + NameSize); i<(IntSize + NameSize + TextSize); i++)
			TextByte[i -(IntSize + NameSize)] = packet[i];
		
		for(int i=(IntSize + NameSize + TextSize); i<(IntSize + NameSize + TextSize + IPSize); i++)
			IPByte[i - (IntSize + NameSize + TextSize)] = packet[i];
		
		int ID = byteBuffer.getInt(0);
		String Name = new String(NameByte);
		String Text = new String(TextByte);
		String IP = new String(IPByte);
		
		Toast toast = Toast.makeText(ctx, "ID: " + ID + "Name: " + Name + "IP: " + IP, Toast.LENGTH_LONG);
		toast.show();
		
	}


}
