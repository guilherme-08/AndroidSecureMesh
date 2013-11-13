package pt.up.fe.ssin.androidsecuremesh.ui;

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
	public static String userName = "";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(!isNetworkAvailable())
		{
			Toast toast = Toast.makeText(getApplicationContext(), "You must be connected to a Wi-fi network!", Toast.LENGTH_LONG);
			toast.show();
		}

		else
		{

			setContentView(R.layout.login);

			username = (EditText) findViewById(R.id.username);
			getIntoTheMesh = (Button) findViewById(R.id.login);

			getIntoTheMesh.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					//TODO also validate if that username is already being used
					if(username.getText().toString().equals(""))
						username.setHintTextColor(getResources().getColor(R.color.redinc));
					else
					{
						Intent intent = new Intent(Login.this, MainMenu.class);
						intent.putExtra(userName, username.getText().toString());
						startActivity(intent);
					}
				}
			});

		}
	}
	
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}


}
