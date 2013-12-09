package pt.up.fe.ssin.androidsecuremesh.utils;

import pt.up.fe.ssin.androidsecuremesh.ui.EnterChatRoom;
import android.os.AsyncTask;

public class DeleteChatAsyncTask extends AsyncTask<Chat, Void, Void>{

	@Override
	protected Void doInBackground(Chat... params) {
		for(Chat chat : EnterChatRoom.chatList)
			if(chat.getName().equals(params[0].getName()))
				EnterChatRoom.chatList.remove(chat);
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		EnterChatRoom.chatListAdapter.notifyDataSetChanged();
	}

}
