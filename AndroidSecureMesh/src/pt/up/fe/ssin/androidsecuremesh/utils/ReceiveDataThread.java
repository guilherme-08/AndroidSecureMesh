package pt.up.fe.ssin.androidsecuremesh.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import android.os.AsyncTask;

public class ReceiveDataThread  extends AsyncTask<Void, Boolean, Boolean>{

	private String message;
	private byte[] data;
	private InetAddress inetAddress;
	private int port;
	private MulticastSocket multicastSocket;
	private DatagramPacket datagramPacket;
	private String host;
	

	@Override
	protected Boolean doInBackground(Void... params) {
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
		
		data = new byte[11];
		datagramPacket = new DatagramPacket(data, data.length);
		
		try {
			multicastSocket.receive(datagramPacket);
		} catch (IOException e) {
			success = false;
		}
		
		
		message = datagramPacket.getData().toString();
		
		return null;
	}


	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
}
