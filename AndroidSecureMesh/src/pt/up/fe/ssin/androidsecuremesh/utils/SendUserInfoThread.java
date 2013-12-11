package pt.up.fe.ssin.androidsecuremesh.utils;

import java.net.DatagramPacket;

import pt.up.fe.ssin.androidsecuremesh.ui.Login;
import pt.up.fe.ssin.androidsecuremesh.ui.MainMenu;

public class SendUserInfoThread extends Thread{

	@Override
	public void run() {
		super.run();

		while(true)
		{

			while (SendDataThread.inetAddress == null)
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			if(MainMenu.userName != null)
			{
				int rating = 0;
				for(User user: Login.main.getUserList())
					if(user.getName().equals(MainMenu.userName))
						rating = user.rating;
				
				DatagramPacket datagram = PacketFactory.newMeshUser(MainMenu.userName, rating, SendDataThread.inetAddress, SendDataThread.port);
				SendDataThread.datagramsArray.add(datagram);
			}			

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
