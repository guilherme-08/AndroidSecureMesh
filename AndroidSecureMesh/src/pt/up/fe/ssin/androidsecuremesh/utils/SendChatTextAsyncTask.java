package pt.up.fe.ssin.androidsecuremesh.utils;

import pt.up.fe.ssin.androidsecuremesh.ui.ChatConversation;
import android.os.AsyncTask;

public class SendChatTextAsyncTask extends AsyncTask<String, Void, Void>{

	@Override
	protected Void doInBackground(String... params) {
		ChatConversation.messagesList.add(params[0]);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		ChatConversation.messagesListAdapter.notifyDataSetChanged();
	}
}
