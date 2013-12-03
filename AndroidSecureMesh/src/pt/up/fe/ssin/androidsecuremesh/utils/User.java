package pt.up.fe.ssin.androidsecuremesh.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;

public class User {

	private String name;
	public AsymmetricKeyParameter privateKey;
	public AsymmetricKeyParameter publicKey;
	
	public User(String name) //generate privateKey missing
	{
		this.name = name;
		
		//time to key up, standard is RSA 1024bits
		RSAKeyPairGenerator r = new RSAKeyPairGenerator();
	    RSAKeyGenerationParameters  par = new RSAKeyGenerationParameters(
                BigInteger.valueOf(0x11), new SecureRandom(), 256, 25);	    
	    r.init(par);
	    AsymmetricCipherKeyPair keys = r.generateKeyPair(); 
	    
	    privateKey = keys.getPrivate();
	    publicKey = keys.getPublic();
		
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
	
}
