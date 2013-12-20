package pt.up.fe.ssin.androidsecuremesh.ui;

import java.net.DatagramPacket;
import java.util.Collections;

import pt.up.fe.ssin.androidsecuremesh.utils.Chat;
import pt.up.fe.ssin.androidsecuremesh.utils.PacketFactory;
import pt.up.fe.ssin.androidsecuremesh.utils.SendDataThread;
import pt.up.fe.ssin.androidsecuremesh.utils.Storage;
import pt.up.fe.ssin.androidsecuremesh.utils.User;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class CreateChat extends Activity {

	private String userName;
	private EditText chatRoomName;
	//private EditText chatRoomKey;
	private Button createChat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_chat);
		// Show the Up button in the action bar.
		setupActionBar();
		//get me a keyboard
		((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

		
		
		Intent intent = getIntent();
		userName = Storage.myData.name;
		
		chatRoomName = (EditText) findViewById(R.id.chatName);
		//chatRoomKey = (EditText) findViewById(R.id.chatPassword);
		createChat = (Button) findViewById(R.id.createChat);
		
		createChat.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Chat chat = new Chat(chatRoomName.getText().toString());
				chat.generateKey(null);
				chat.ownerIp = Storage.getIPAddress(true);
				chat.setOwner(Storage.myData);
				
				boolean found = false;
				for (Chat c : Storage.myData.ownedChats)
					if (c.name == c.name)
						found = true;
				
				if (!found)
					Storage.myData.ownedChats.add(chat);
				
				
				DatagramPacket datagram = PacketFactory.newChatPacket(chatRoomName.getText().toString(), SendDataThread.inetAddress, SendDataThread.port);
				
				SendDataThread.datagramsArray.add(datagram);
				
				
				
				
				Login.main.addToChatList(chat);
				
				
				Intent intent = new Intent(CreateChat.this, EnterChatRoom.class);
				startActivity(intent);
				
			}
		});
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
