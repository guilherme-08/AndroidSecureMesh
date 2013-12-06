package pt.up.fe.ssin.androidsecuremesh.utils;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAKeyGenParameterSpec;

import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.generators.RSAKeyPairGenerator;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.RSAKeyGenerationParameters;


public class User {

	private String name;
	public PrivateKey privateKey;
	public PublicKey publicKey;
	
	public User(String name) //generate privateKey missing
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchProviderException e1) {
			// TODO Auto-generated catch block
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

	public boolean ownsChat(String string) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
