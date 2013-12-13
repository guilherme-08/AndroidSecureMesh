package pt.up.fe.ssin.androidsecuremesh.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class SendTCPThread extends Thread{

	private Socket socket;
	private String IP;
	private int port;
	private PrintWriter out;
	private InetAddress srvAddress;
	public static List<String> textList;

	@Override
	public void run() {
		super.run();

		port = 8080;
		textList = new ArrayList<String>();


		while(true)
		{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			if(textList.size() != 0)
			{
				String IP = textList.get(0).substring(textList.get(0).lastIndexOf("@|@|@|@") + 7);
				Log.i("HELLO TCP", "Sending packet to IP " + IP);
				if (IP.indexOf("|") != -1)
					IP = IP.substring(0, IP.indexOf("|"));
				try {
					srvAddress = InetAddress.getByName(IP);
				} catch (UnknownHostException e1) {
					
					e1.printStackTrace();
					continue;
				}

				try 
				{
					socket = new Socket(srvAddress, port);

					out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
					out.println(textList.get(0));
					textList.remove(0);			
				} 
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}


