package pt.up.fe.ssin.androidsecuremesh.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.ArrayList;


public class User {

	private String name;
	public PrivateKey privateKey;
	public PublicKey publicKey;

	public int rating=100;
	public ArrayList<Chat> ownedChats = new ArrayList<Chat>();
	public ArrayList<Chat> nextOwnedChats = new ArrayList<Chat>();
	
	public User(String name)
	{
		this.name = name;
		
		//time to key up, standard is RSA 4096bits
		SecureRandom random = new SecureRandom(); 
		RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4); 
		KeyPairGenerator generator = null;	
		try {
			generator = KeyPairGenerator.getInstance("RSA", "SC");
	
			generator.initialize(spec, random);
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (NoSuchProviderException e1) {
			e1.printStackTrace();
		}  
		KeyPair a = generator.generateKeyPair();
		publicKey = a.getPublic();
		privateKey = a.getPrivate();
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString()
	{
		return this.name;
	}

	public boolean ownsChat(String string) 
	{
		for (Chat c : ownedChats)
			if (c.getName() == string)
				return true;
		
		return false;
	}
	
	public static String bytesToHex(byte[] bytes) {
		 StringBuilder sb = new StringBuilder();
		   for(int i=0; i<bytes.length; i++)
		   {
		      sb.append(String.format("%02x", bytes[i]&0xff));
		      if((i+1) != bytes.length)
		    	  sb.append(':');
		   }
		   return sb.toString();
	}
}
