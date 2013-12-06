package pt.up.fe.ssin.androidsecuremesh.ui;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.spongycastle.crypto.engines.RSAEngine;
import org.spongycastle.jce.provider.BouncyCastleProvider;


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

	
	static {
	    Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crypto_test);
		final TextView output = (TextView) findViewById(R.id.output);
		final EditText input = (EditText) findViewById(R.id.input);

		Security.insertProviderAt(new BouncyCastleProvider(), 1);
		final User me = Storage.myData;
		final boolean[] isEncrypted = { false };
		final Cipher[] rsaCipher = { null } ;
		byte[] test1 = null;
			try {
				rsaCipher[0] = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding", "SC");
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchProviderException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchPaddingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		final byte[][] args = {new byte[256], new byte[256]};
		
		
		findViewById(R.id.crypt).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				args[0] = input.getText().toString().getBytes();
				try {
					rsaCipher[0].init(Cipher.ENCRYPT_MODE, me.publicKey);
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					args[1] = rsaCipher[0].doFinal(args[0]);
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				output.setText(new String(args[1]));
			}
			
		});

		findViewById(R.id.toggle).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				byte[] temp = args[0];
				args[0] = args[1];
				args[1] = temp;
				input.setText(new String(args[0]));
				output.setText("reset!");
			}
			
		});
		
		findViewById(R.id.decrypt).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				try {
					rsaCipher[0].init(Cipher.DECRYPT_MODE, me.privateKey);
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					args[1] = rsaCipher[0].doFinal(args[0]);
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				output.setText(new String(args[1]));
			}
			
		});
		
		
	}
}
