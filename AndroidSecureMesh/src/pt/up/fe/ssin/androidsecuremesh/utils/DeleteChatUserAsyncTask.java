package pt.up.fe.ssin.androidsecuremesh.utils;

import pt.up.fe.ssin.androidsecuremesh.ui.ChatUsersList;
import pt.up.fe.ssin.androidsecuremesh.ui.EnterChatRoom;
import android.os.AsyncTask;

public class DeleteChatUserAsyncTask extends AsyncTask<String, Void, Void>{

	@Override
	protected Void doInBackground(String... params) {
		for(User user : EnterChatRoom.chosenChat.getUsersList())
			if(user.getName().equals(params[0]))
				EnterChatRoom.chatList.remove(user);
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		ChatUsersList.usersListAdapter.notifyDataSetChanged();
	}

}

