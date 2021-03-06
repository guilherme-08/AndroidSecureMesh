package pt.up.fe.ssin.androidsecuremesh.ui;

import java.io.IOException;
import java.net.DatagramPacket;

import pt.up.fe.ssin.androidsecuremesh.utils.Main;
import pt.up.fe.ssin.androidsecuremesh.utils.PacketFactory;
import pt.up.fe.ssin.androidsecuremesh.utils.ReversePacketFactory;
import pt.up.fe.ssin.androidsecuremesh.utils.SendDataThread;
import pt.up.fe.ssin.androidsecuremesh.utils.Storage;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	private EditText username;
	private Button getIntoTheMesh;
	public static Main main = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		if(!isWifiAvailable())
		{
			Toast toast = Toast.makeText(getApplicationContext(), "You must be connected to a Wi-fi network!", Toast.LENGTH_LONG);
			toast.show();
		}

		else
		{
			if(main == null){
				try {
					main = new Main();
					ReversePacketFactory.ctx = this;
					
					while (SendDataThread.inetAddress == null)
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				} catch (IOException e) {
					Toast toast = Toast.makeText(getApplicationContext(), "Problem when creating multicastSocket", Toast.LENGTH_LONG);
					toast.show();
				}
			}
			setContentView(R.layout.login);
			Storage.ctx = this;
			username = (EditText) findViewById(R.id.username);
			getIntoTheMesh = (Button) findViewById(R.id.login);
			getIntoTheMesh.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					
					if(username.getText().toString().equals(""))
						username.setHintTextColor(getResources().getColor(R.color.redinc));
					else if(main.containsInUserList(username.getText().toString())){
						Toast toast = Toast.makeText(getApplicationContext(), "Already exists an user with that username", Toast.LENGTH_LONG);
						toast.show();
					}
					else
					{
						

						Storage.myData.name = username.getText().toString();
						while (SendDataThread.inetAddress == null)
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						DatagramPacket datagram = PacketFactory.newMeshUser(username.getText().toString(), 100, SendDataThread.inetAddress, SendDataThread.port);
						
						SendDataThread.datagramsArray.add(datagram);
						Storage.users.add(Storage.myData);
						Intent intent = new Intent(Login.this, MainMenu.class);
						startActivity(intent);
					}
				}
			});

		}
	}

	private boolean isWifiAvailable() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected();
	}


}
