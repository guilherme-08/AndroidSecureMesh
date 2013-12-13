package pt.up.fe.ssin.androidsecuremesh.utils;

import pt.up.fe.ssin.androidsecuremesh.ui.ChatUsersList;
import pt.up.fe.ssin.androidsecuremesh.ui.Login;
import android.os.AsyncTask;

public class NewChatUserAsyncTask extends AsyncTask<String, Void, Void>{

	@Override
	protected Void doInBackground(String... params) {
		
		for(User user: Login.main.getUserList()) //TODO must ensure that you know all the users, and their names are uniques
			if(user.name.equals(params[0]))
				ChatUsersList.usersList.add(user);
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		ChatUsersList.usersListAdapter.notifyDataSetChanged();
	}
}