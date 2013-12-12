package pt.up.fe.ssin.androidsecuremesh.utils;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class CryptoUtils {

	public static boolean encrypt(PublicKey k, byte[] plainText) //RSA!
	{
		Cipher rsaCipher = null;
		try {
			rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding", "SC");
			rsaCipher.init(Cipher.ENCRYPT_MODE, k);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		
		try {
			plainText = rsaCipher.doFinal(plainText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		
		return true;
	}
	
	public static boolean decrypt(PrivateKey k, byte[] plainText) //RSA!
	{
		Cipher rsaCipher = null;
		try {
			rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding", "SC");
			rsaCipher.init(Cipher.DECRYPT_MODE, k);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		
		try {
			plainText = rsaCipher.doFinal(plainText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		
		return true;
	}
	
	
	public static String sanitize(String dirty)
	{
		return dirty.substring(0, dirty.indexOf((char) 0));
		
	}
	
	
}
