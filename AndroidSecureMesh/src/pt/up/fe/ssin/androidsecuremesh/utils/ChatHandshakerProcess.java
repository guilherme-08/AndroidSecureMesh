package pt.up.fe.ssin.androidsecuremesh.utils;

import java.io.UnsupportedEncodingException;

import javax.crypto.spec.SecretKeySpec;

import org.spongycastle.util.Arrays;

import pt.up.fe.ssin.androidsecuremesh.utils.ReceiveTCPThread.TCPRunnable;
import android.os.AsyncTask;
import android.util.Log;

public class ChatHandshakerProcess extends AsyncTask<Object, Void, Void>{

	@Override
	protected Void doInBackground(final Object... params) {
		String IP = ((Chat) params[0]).ownerIp;
		String packetSorta = PacketFactory.askChatAcceptance( ((Chat) params[0]).name) ;
		SendTCPThread.textList.add(packetSorta + "@|@|@|@" + IP);
		Log.e("HELLO CRYPTO", "Handshake 1");
		ReceiveTCPThread.addCallback(0xBAADBEEF, new TCPRunnable(params[0]){

			@Override
			public void run() {
				//id|chatkeyencrypted
				Log.e("HELLO CRYPTO", "Handshake 3 begin");
				String keyEnc = this.packetSortof.substring(packetSortof.indexOf("|") + 1, packetSortof.indexOf("@|@|@|@"));
				byte[] keyDetails = null;
				try {
					keyDetails = keyEnc.getBytes("ISO-8859-1");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				keyDetails = CryptoUtils.decrypt(Storage.myData.privateKey, keyDetails);
				
				myChat.setKey(new SecretKeySpec(keyDetails, "AES/ECB/NoPadding"));
				Log.e("HELLO CRYPTO", "Handshake 3 end");
				synchronized (params)
				{
					params.notifyAll();
				}
			}
			
		});
		
		try {
			synchronized (params)
			{
				params.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return null;
	};

	}


