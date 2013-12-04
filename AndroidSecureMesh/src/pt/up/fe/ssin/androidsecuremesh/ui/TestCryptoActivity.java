package pt.up.fe.ssin.androidsecuremesh.ui;

import java.security.Security;

import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import pt.up.fe.ssin.androidsecuremesh.utils.Storage;
import pt.up.fe.ssin.androidsecuremesh.utils.User;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class TestCryptoActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crypto_test);
		final TextView output = (TextView) findViewById(R.id.output);
		final EditText input = (EditText) findViewById(R.id.input);

		Security.addProvider(new BouncyCastleProvider());
		final User me = Storage.myData;
		final boolean[] isEncrypted = { false };

		final RSAEngine eng = new RSAEngine();

		byte[] test1 = "Nigga".toString().getBytes();
		eng.init(true, me.privateKey);
		test1 = eng.processBlock(test1, 0, test1.length);

		eng.init(false, me.publicKey);
		test1 = eng.processBlock(test1, 0, test1.length);

		final byte[][] args = {new byte[100], new byte[100]};
		
		
		findViewById(R.id.crypt).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				args[0] = input.getText().toString().getBytes();
				eng.init(true, me.privateKey);
				args[0] = eng.processBlock(args[0], 0, args[0].length);
				output.setText(new String(args[0]));
			}
			
		});

		findViewById(R.id.toggle).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Editable inputtext = input.getText();
				input.setText(output.getText());
				output.setText(inputtext);
			}
			
		});
		
		findViewById(R.id.decrypt).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				eng.init(false, me.publicKey);
				args[0] = eng.processBlock(args[0], 0, args[0].length);
				output.setText(new String(args[0]));
			}
			
		});
		
		
	}
}
