package pt.up.fe.ssin.androidsecuremesh.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;
//		      								  params, progress, return
public class SendDataThread extends Thread/*AsyncTask<String, Boolean, Boolean>*/{

	private String message;
	public static InetAddress inetAddress;
	public static int port;
	private MulticastSocket multicastSocket;
	private DatagramPacket datagramPacket;
	public static String host;
	public static List<DatagramPacket> datagramsArray = new ArrayList<DatagramPacket>();

	@Override
	public void run() {
		super.run();

		message = "teste";


		host = "224.0.2.0";
		port = 8080;

		try {
			multicastSocket = new MulticastSocket(port);
		} catch (IOException e1) {
		}

		try {
			inetAddress = InetAddress.getByName(host);
		} catch (UnknownHostException e3) {
		}


		try {
			multicastSocket.setTimeToLive(1);
		} catch (IOException e2) {
		}


		try {
			multicastSocket.joinGroup(inetAddress);
		} catch (IOException e1) {
		}


		

		while(true)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(datagramsArray.size() != 0)
			{
				try {
					if (datagramsArray.get(0).getData()[3] == 2)
						Log.e("HELLO SEND", "Now broadcasting chat!");
					multicastSocket.send(datagramsArray.get(0));
					datagramsArray.remove(0);
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			for (Chat c : Storage.myData.ownedChats)
			{
				try 
				{
					multicastSocket.send(PacketFactory.newChatPacket(c.getName(), InetAddress.getByName(host), port));
				} 
				catch (UnknownHostException e) 
				{
					// TODO Auto-generated catch block
						e.printStackTrace();
				} 
				catch (IOException e) 
				{
						// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Log.i("HELLO SEND", "Heartbeat!");
		
		}

		//	return success;

	}



	/*@Override
	protected Boolean doInBackground(String... params) {

		message = params[0];

		Boolean success = true;

		host = "224.0.2.0";
		port = 8080;

		try {
			multicastSocket = new MulticastSocket(port);
		} catch (IOException e1) {
			success = false;
		}

		try {
			inetAddress = InetAddress.getByName(host);
		} catch (UnknownHostException e3) {
			success = false;
		}


		try {
			multicastSocket.setTimeToLive(1);
		} catch (IOException e2) {
			success = false;
		}


		try {
			multicastSocket.joinGroup(inetAddress);
		} catch (IOException e1) {
			success = false;
		}

		data = message.getBytes();

		datagramPacket = new DatagramPacket(data, data.length, inetAddress, port);


		try {
			multicastSocket.send(datagramPacket);
		} catch (IOException e) {
			success = false;
		}

		return success;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	 */
}
