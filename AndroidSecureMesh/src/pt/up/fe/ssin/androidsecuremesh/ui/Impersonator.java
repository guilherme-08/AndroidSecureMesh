package pt.up.fe.ssin.androidsecuremesh.ui;

import pt.up.fe.ssin.androidsecuremesh.utils.Chat;
import pt.up.fe.ssin.androidsecuremesh.utils.Main;
import pt.up.fe.ssin.androidsecuremesh.utils.PacketFactory;
import pt.up.fe.ssin.androidsecuremesh.utils.SendDataThread;
import pt.up.fe.ssin.androidsecuremesh.utils.User;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class Impersonator extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.impersonator);
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				String name = ((EditText) findViewById(R.id.editText1)).getText().toString();
				String text = ((EditText) findViewById(R.id.editText3)).getText().toString();
				String chat = ((EditText) findViewById(R.id.editText2)).getText().toString();

				Chat ch = Main.getChatByName(chat);
				
				SendDataThread.datagramsArray.add(PacketFactory.sendTextToChat(ch, text, name, ch.getKey(), SendDataThread.inetAddress, SendDataThread.port));
				
			}});
	}

}
