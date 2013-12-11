package pt.up.fe.ssin.androidsecuremesh.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.spongycastle.util.Arrays;

import pt.up.fe.ssin.androidsecuremesh.ui.ChatConversation;
import pt.up.fe.ssin.androidsecuremesh.ui.EnterChatRoom;
import android.os.AsyncTask;

public class ProcessTextChatPacket extends AsyncTask<byte[], Void, String>{

	@Override
	protected String doInBackground(byte[]... packet) {
		byte[] ChatNameByte = new byte[ReversePacketFactory.ChatNameSize];

		byte[] UserNameByte = new byte[ReversePacketFactory.UserNameSize];

		//TODO keys missing

		byte[] TextByte = new byte[ReversePacketFactory.TextSize];

		for(int i=ReversePacketFactory.IntSize; i<(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize); i++)
			ChatNameByte[i - ReversePacketFactory.IntSize] = packet[0][i];


		String chatName = new String(ChatNameByte);
		if(!chatName.equals(EnterChatRoom.chosenChat.getName()))
			return null;
		
		for(int i=(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize); i<(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize + ReversePacketFactory.UserNameSize); i++)
			UserNameByte[i -(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize)] = packet[0][i];


		for(int i=(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize + ReversePacketFactory.UserNameSize); i<(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize + ReversePacketFactory.UserNameSize + ReversePacketFactory.TextSize); i++)
			TextByte[i -(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize + ReversePacketFactory.UserNameSize)] = packet[0][i];
		
		byte[] decrypted = null;
		try {
			byte[] keyBytes = new byte[32];
			
			//r.nextBytes(keyBytes);
			//same key for everyone!
			Arrays.fill(keyBytes, (byte) 32);
			
			SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
			Cipher ciph = Cipher.getInstance("AES");
			ciph.init(Cipher.DECRYPT_MODE, key);
			
			decrypted = ciph.doFinal(TextByte);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String userName = new String(UserNameByte);
		String text = new String(decrypted);

		
			return text;
		}

	@Override
	protected void onPostExecute(String text) {
		if (text == null)
			return;
		
		ChatConversation.messagesList.add(text);
		ChatConversation.messagesListAdapter.notifyDataSetChanged();
	}
}
