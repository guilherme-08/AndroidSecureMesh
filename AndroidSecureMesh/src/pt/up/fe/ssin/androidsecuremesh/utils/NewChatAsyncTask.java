package pt.up.fe.ssin.androidsecuremesh.utils;

import pt.up.fe.ssin.androidsecuremesh.ui.EnterChatRoom;
import android.os.AsyncTask;

//params,progress,result
public class NewChatAsyncTask extends AsyncTask<Chat, Void, Void>{

	@Override
	protected Void doInBackground(Chat... params) {
		
		boolean found = false;
		for (Chat c : EnterChatRoom.chatList)
			if (c.name.equals(params[0].name))
				found = true;
		
		
		
		if (!found)
			EnterChatRoom.chatList.add(params[0]);
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		EnterChatRoom.chatListAdapter.notifyDataSetChanged();
	}

}
