package pt.up.fe.ssin.androidsecuremesh.ui;


import java.util.ArrayList;
import java.util.List;

import pt.up.fe.ssin.androidsecuremesh.utils.User;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ChatUsersList extends Fragment {
	
	public ListView usersListView;
	public static List<User> usersList; //to add chats
	public static ArrayAdapter<User> usersListAdapter;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.chat_users_list, container, false);
		
		
		usersListView = (ListView) view.findViewById(R.id.usersList);
		usersList = new ArrayList<User>();
		
		usersListAdapter = new ArrayAdapter<User>(view.getContext(), android.R.layout.simple_list_item_1, usersList);
		
		usersListView.setAdapter(usersListAdapter);
		
	/*	for(User user: EnterChatRoom.chosenChat.getUsersList())
			usersList.add(user);*/
		
		//Testing examples
	/*	User u1 = new User("guilha"); User u2 = new User("joao");
		usersList.add(u1);usersList.add(u2);*/
		
		usersListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long id) {
				User user = (User) usersListView.getItemAtPosition(pos);
				String userName = user.getName();
				String text = "Username: " + userName + "\nRating: " + Integer.toString(user.rating) + "\nPublic Key: " + new String(user.publicKey.getEncoded());
			/*	Toast test = Toast.makeText(arg1.getContext(), text, Toast.LENGTH_SHORT);
				test.show();*/
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						arg1.getContext());
				
				alertDialogBuilder
				.setTitle("User Information")
				.setMessage(text)
				.setNegativeButton("Ok", null)
				.show();
				
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
				
				return false;
			}
		});
		
		return view;
	}
}
