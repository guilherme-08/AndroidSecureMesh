package pt.up.fe.ssin.androidsecuremesh.utils;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.util.Log;

public class MulticastThread extends Thread
{

	String host = "224.0.2.0";
	int port = 8080;
	
	@Override
	public void run() {
		
		DatagramPacket pk = null;
		while (true)
		{
			
			
			for (Chat c : Storage.myData.ownedChats)
			{
				pk = null;
				try 
				{
					pk = PacketFactory.newChatPacket(c.getName(), InetAddress.getByName(host), port);
				} 
				catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SendDataThread.datagramsArray.add(pk);
				Log.i("HELLO MULTICAST", "heartbeat");
			}
			
			try 
			{
				Thread.sleep(3000);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
