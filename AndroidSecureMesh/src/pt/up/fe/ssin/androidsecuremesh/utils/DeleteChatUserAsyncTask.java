package pt.up.fe.ssin.androidsecuremesh.utils;

import pt.up.fe.ssin.androidsecuremesh.ui.ChatUsersList;
import pt.up.fe.ssin.androidsecuremesh.ui.EnterChatRoom;
import android.os.AsyncTask;

public class DeleteChatUserAsyncTask extends AsyncTask<String, Void, Void>{

	@Override
	protected Void doInBackground(String... params) {
		for(User user : ChatUsersList.usersList)
			if(user.name.equals(params[0]))
				ChatUsersList.usersList.remove(user);
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		ChatUsersList.usersListAdapter.notifyDataSetChanged();
	}

}

