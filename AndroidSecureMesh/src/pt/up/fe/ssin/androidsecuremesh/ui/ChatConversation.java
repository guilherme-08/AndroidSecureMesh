package pt.up.fe.ssin.androidsecuremesh.ui;

import java.util.ArrayList;
import java.util.List;

import pt.up.fe.ssin.androidsecuremesh.utils.User;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class ChatConversation extends Fragment {

	private ListView messagesListView;
	private List<String> messagesList; //to add messages to the chat
	private ArrayAdapter<String> messagesListAdapter;
	private EditText currentMessage;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.chat_conversation, container, false);


		messagesListView = (ListView) view.findViewById(R.id.messagesList);
		messagesList = new ArrayList<String>();

		messagesListAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, messagesList);

		messagesListView.setAdapter(messagesListAdapter);

		//Testing examples
		messagesList.add("test"); messagesList.add("message2");

		
		currentMessage = (EditText) view.findViewById(R.id.messageText);

		currentMessage.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					String message = MainMenu.userName + ":" + currentMessage.getText().toString();
					messagesList.add(message);
					currentMessage.setText("");
					return true;
				}
				return false;
			}
		});

		return view;
	}
}

