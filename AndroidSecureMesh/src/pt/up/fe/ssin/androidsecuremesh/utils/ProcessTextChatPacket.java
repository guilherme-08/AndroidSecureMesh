package pt.up.fe.ssin.androidsecuremesh.utils;

import java.net.DatagramPacket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.spongycastle.util.Arrays;

import pt.up.fe.ssin.androidsecuremesh.ui.ChatConversation;
import pt.up.fe.ssin.androidsecuremesh.ui.EnterChatRoom;
import android.os.AsyncTask;
import android.util.Log;

public class ProcessTextChatPacket extends AsyncTask<byte[], Void, String>{

	@Override
	protected String doInBackground(final byte[]... packet) {
		byte[] cryptChatNameByte = new byte[ReversePacketFactory.CryptedChatNameSize];
		byte[] ChatNameByte = new byte[ReversePacketFactory.ChatNameSize];
		byte[] usernameByte = packet[1];
		byte[] TextByte = new byte[ReversePacketFactory.TextSize];

		for(int i=ReversePacketFactory.IntSize; i<(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize); i++)
			ChatNameByte[i - ReversePacketFactory.IntSize] = packet[0][i];

		for(int i=(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize + ReversePacketFactory.UserNameSize); i<(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize + ReversePacketFactory.UserNameSize + ReversePacketFactory.CryptedChatNameSize); i++)
			cryptChatNameByte[i -(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize + ReversePacketFactory.UserNameSize)] = packet[0][i];

		

		String username = new String(usernameByte);
		username = CryptoUtils.sanitize(username);
		User thisGuy = Main.getUserByUsername(username);
		
		
		String chatName = new String(ChatNameByte);
		chatName = CryptoUtils.sanitize(chatName);
		if(!chatName.equals(EnterChatRoom.chosenChat.getName()))
			return null;

		
		
		for(int i=(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize + ReversePacketFactory.UserNameSize + ReversePacketFactory.CryptedChatNameSize); i<(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize + ReversePacketFactory.UserNameSize + ReversePacketFactory.CryptedChatNameSize + ReversePacketFactory.TextSize); i++)
			TextByte[i -(ReversePacketFactory.IntSize + ReversePacketFactory.ChatNameSize + ReversePacketFactory.CryptedChatNameSize + ReversePacketFactory.UserNameSize)] = packet[0][i];

		byte[] decrypted = null;
		String temp = null;
		try {
			byte[] keyBytes = new byte[32];

			//r.nextBytes(keyBytes);
			//same key for everyone!
			Arrays.fill(keyBytes, (byte) 32);

			SecretKeySpec key = EnterChatRoom.chosenChat.getKey();
			Cipher ciph = Cipher.getInstance("AES/ECB/NoPadding", "SC");
			ciph.init(Cipher.DECRYPT_MODE, key);

			
			decrypted = ciph.doFinal(TextByte);
			//decrypted = TextByte;
		if (thisGuy != null)
			if (thisGuy.publicKey != null)
			{
				ciph = Cipher.getInstance("RSA", "SC");
				ciph.init(Cipher.DECRYPT_MODE, thisGuy.publicKey);
				
				temp = CryptoUtils.sanitize(new String(ciph.doFinal(cryptChatNameByte)));
				
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String text = new String(decrypted);
		
		Chat chatOn = Main.getChatByName(chatName);
		if (chatOn != null)
		{
			if (chatOn.isMine())
			{
				if (!temp.equals(chatName))
				{
					DatagramPacket datagram = PacketFactory.changeUserRating(username, 0, Storage.myData.publicKey, null, SendDataThread.inetAddress, SendDataThread.port);
					Log.e("CRYPTOGUARD", "User " + username + " is not who he claims he is!");
					SendDataThread.datagramsArray.add(datagram);
				}
				else Log.e("CRYPTOGUARD", "User validated.");
			}
		}

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
