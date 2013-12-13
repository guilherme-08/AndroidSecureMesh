package pt.up.fe.ssin.androidsecuremesh.utils;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class CryptoUtils {

	public static byte[] encrypt(PublicKey k, byte[] plainText) //RSA!
	{
		Cipher rsaCipher = null;
		try {
			rsaCipher = Cipher.getInstance("RSA", "SC");
			rsaCipher.init(Cipher.ENCRYPT_MODE, k);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		byte[] cipherText = null;
		try {
			cipherText = rsaCipher.doFinal(plainText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		
		
		return cipherText;
	}
	
	public static byte[] decrypt(PrivateKey k, byte[] plainText) //RSA!
	{

		byte[] newPlainText = { 0, 0, 0};
		Cipher rsaCipher = null;
		try {
			rsaCipher = Cipher.getInstance("RSA", "SC");
			rsaCipher.init(Cipher.DECRYPT_MODE, k);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		
		try {
			newPlainText = rsaCipher.doFinal(plainText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		
		return newPlainText;
		
		}
	
	
	public static String sanitize(String dirty)
	{
		return dirty.substring(0, dirty.indexOf((char) 0));
		
	}
	
	
}
