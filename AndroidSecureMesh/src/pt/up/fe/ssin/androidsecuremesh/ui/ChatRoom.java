package pt.up.fe.ssin.androidsecuremesh.ui;

import pt.up.fe.ssin.androidsecuremesh.utils.Chat;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.MenuItem;

public class ChatRoom extends FragmentActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_room);
		// Show the Up button in the action bar.


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
			    	Login.main.deleteInChatList(EnterChatRoom.chosenChat, MainMenu.userName);
			    	ChatRoom.this.finish();
			    }
			
			})
			.setNegativeButton("No", null)
			.show();
	    }
    	return super.onKeyDown(keyCode, event);
	}



}
