package pt.up.fe.ssin.androidsecuremesh.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;

public class ReceiveTCPThread extends Thread{

	
	abstract public static class TCPRunnable implements Runnable
	{

		Chat myChat;
		String packetSortof;
		TCPRunnable(Object params)
		{
			super();
			myChat = (Chat) params;
		}
		@Override
		public abstract void run();
		
	}
	private ServerSocket srvSocket;
	private Socket socket;
	private int port;
	private String text;
	private MyBufferedReader input;

	private static TCPRunnable callback = null;
	private static int id = -1;
	
	static void addCallback(int reqId, TCPRunnable cb)
	{
		id = reqId;
		callback = cb;
	}
	
	@Override
	public void run() {
		super.run();

		port = 8080;

		try {
			srvSocket = new ServerSocket(port);
		
			while(true)
			{
				socket = srvSocket.accept();
				Log.e("HELLO TCP SEND", "Looping!");
				text = "";
				input = new MyBufferedReader(new InputStreamReader(socket.getInputStream()));
				while (input.ready())
				{
					text += input.readLine();
				}
				
				if (text == "")
					continue;
				
				String thisId = text.substring(0, text.indexOf("|"));
				
				if ((callback != null) && (Integer.parseInt(thisId) == id))
				{
						callback.packetSortof = text;
						callback.run();
						callback = null;
						id = -1;
				}
				else
				{
					String IP = socket.getRemoteSocketAddress().toString();
					IP = IP.substring(1, IP.indexOf(":"));
					ReversePacketFactory.getTCPPacketDataById(Integer.parseInt(thisId), text, IP);
				}
				srvSocket.close();
				srvSocket = new ServerSocket(port);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class MyBufferedReader extends BufferedReader{
		 private Reader delegate;
		 private StringBuilder readBuffer;
		 private int nextCh = SOL;
		 private int readChars;

		 private static final int SOL = -10;   // Start Of Line

		 public MyBufferedReader(Reader delegate) {
	     super(delegate);
		 this.delegate = delegate;
		 readBuffer = new StringBuilder();
		 }

		 /**
		 * Reads all chars of a line, returning also line terminators
		 *
		 * @return  The line text
		 */
		 public String readLine() throws IOException {
		 String res = null;
		 readBuffer.setLength(0);
		 int ch = (char) -10;

		 if (nextCh == -1) {
		 res = null;
		 } else {

		 boolean newLine = false;
		 boolean eof = false;
		 while (!newLine && !eof) {
		 if (nextCh != SOL){
		 readBuffer.append((char)nextCh);
		 }
		 nextCh = SOL;
		 ch = delegate.read();
		 switch (ch) {
		 case '\r':
		 // check for double newline char
		 nextCh = delegate.read();
		 if (nextCh == '\n') {
		 // double line found
		 readBuffer.append("\r\n");
		 newLine = true;
		 nextCh = SOL;
		 } else {
		 readBuffer.append("\r");
		 newLine = true;
		 }
		 break;
		 case '\n':
		 readBuffer.append("\n");
		 newLine = true;
		 break;
		 case -1:
		 eof = true;
		 nextCh = -1;
		 break;
		 default:
		 if (ch != -1) readBuffer.append((char)ch);           
		 }       
		 }
		 res = readBuffer.toString();
		 readChars += res.length();
		 }
		 return res;
		 }

		 public void close() throws IOException{
		 delegate.close();
		 }

		 public int readChars() {
		 return readChars;
		 }
		}
	
}

