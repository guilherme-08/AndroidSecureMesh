package pt.up.fe.ssin.androidsecuremesh.ui;

import java.util.ArrayList;
import java.util.List;

import pt.up.fe.ssin.androidsecuremesh.utils.Chat;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EnterChatRoom extends Activity {

	private ListView chatListView;
	private List<Chat> chatList; //to add chats
	private ArrayAdapter<Chat> chatListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_chat_room);
		// Show the Up button in the action bar.
		setupActionBar();

		chatListView = (ListView) findViewById(R.id.chatList);
		chatList = new ArrayList<Chat>();
		chatListAdapter = new ArrayAdapter<Chat>(this, android.R.layout.simple_list_item_1, chatList);

		chatListView.setAdapter(chatListAdapter);

		Chat test = new Chat("Room222"), test2 = new Chat("Soccer");
		chatList.add(test); chatList.add(test2);
		//TODO a for with all created chat rooms (Static var somewhere stored)



		chatListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3)
			{
				Chat thechat = chatList.get(position);

				String message = "Are you sure do you want to enter in the chat room \"" + thechat + "\"?";
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EnterChatRoom.this);

				alertDialogBuilder.setTitle("Enter a Chat Room");

				alertDialogBuilder
				.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {


					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
						

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();


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
