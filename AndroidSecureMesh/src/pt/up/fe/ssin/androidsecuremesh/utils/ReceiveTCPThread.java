package pt.up.fe.ssin.androidsecuremesh.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;

public class ReceiveTCPThread extends Thread{

	private ServerSocket srvSocket;
	private Socket socket;
	private int port;
	private String text;
	private BufferedReader input;

	@Override
	public void run() {
		super.run();

		port = 8080;

		try {
			srvSocket = new ServerSocket(port);
			socket = srvSocket.accept();

			while(true)
			{
				Log.e("HELLO TCP SEND", "Looping!");
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				text = input.readLine();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
