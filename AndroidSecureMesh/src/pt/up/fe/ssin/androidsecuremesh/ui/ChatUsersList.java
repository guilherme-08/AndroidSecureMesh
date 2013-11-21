package pt.up.fe.ssin.androidsecuremesh.ui;


import java.util.ArrayList;
import java.util.List;

import pt.up.fe.ssin.androidsecuremesh.utils.Chat;
import pt.up.fe.ssin.androidsecuremesh.utils.User;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ChatUsersList extends Fragment {
	
	private ListView usersListView;
	private List<User> usersList; //to add chats
	private ArrayAdapter<User> usersListAdapter;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.chat_users_list, container, false);
		
		
		usersListView = (ListView) view.findViewById(R.id.usersList);
		usersList = new ArrayList<User>();
		
		usersListAdapter = new ArrayAdapter<User>(view.getContext(), android.R.layout.simple_list_item_1, usersList);
		
		usersListView.setAdapter(usersListAdapter);
		
		for(User user: EnterChatRoom.chosenChat.getUsersList())
			usersList.add(user);
		
		//Testing examples
		User u1 = new User("guilha"); User u2 = new User("joao");
		usersList.add(u1);usersList.add(u2);
		
		usersListView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		return view;
	}
}
