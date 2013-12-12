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

		try {
			srvAddress = InetAddress.getByName(Storage.getIPAddress(true));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			socket = new Socket(srvAddress, port);

			while(true)
			{
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(textList.size() != 0)
				{
					out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
					out.println(textList.get(0));
					textList.remove(0);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
