package pt.up.fe.ssin.androidsecuremesh.ui;

import pt.up.fe.ssin.androidsecuremesh.utils.Chat;
import pt.up.fe.ssin.androidsecuremesh.utils.User;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends Activity {

	private TextView specialGreetings;
	private Button createChat;
	private Button enterChat;
	public static String userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		// Show the Up button in the action bar.

		specialGreetings = (TextView) findViewById(R.id.specialGreetings);

		if(userName == null)
		{
			Intent intent = getIntent();
			userName = intent.getStringExtra(Login.userName);
		}
		specialGreetings.setText("Hello, " + userName + "!");

		createChat = (Button) findViewById(R.id.createChat);
		enterChat = (Button) findViewById(R.id.enterChat);


		createChat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this, CreateChat.class);
				intent.putExtra(userName, userName);
				startActivity(intent);
			}
		});


		enterChat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this, EnterChatRoom.class);
				intent.putExtra(userName, userName);
				startActivity(intent);
			}
		});
	}



	
	
	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Logout")
			.setMessage("Are you sure you really want to leave the mesh?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			    	userName = null;
			    	EnterChatRoom.chosenChat = null;
			    	Login.main.getChatList().clear();
			    	Login.main.getUserList().clear();

			    	MainMenu.this.finish();
			    }
			
			})
			.setNegativeButton("No", null)
			.show();
	    }
    	return super.onKeyDown(keyCode, event);
	}

}
