package pt.up.fe.ssin.androidsecuremesh.utils;

import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.spongycastle.util.Arrays;

public class Chat {
	
	static {
	    Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
	}
	
	private static SecureRandom r = new SecureRandom();
	public String name;
	private SecretKeySpec key;
	private List<User> usersList = new ArrayList<User>();
	private User owner;
	public String ownerIp;
	
	public Chat(String _name)
	{
		if (_name.indexOf(0) != -1)
			name = _name.substring(0, _name.indexOf(0));
		else
			name = _name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SecretKeySpec getKey() {
		return key;
	}

	public void setKey(SecretKeySpec key) {
		this.key = key;
	}
	
	public String toString()
	{
		return name;
		
	}

	public List<User> getUsersList() {
		return usersList;
	}

	public void addToUsersList(User user) {
		if(!this.usersList.contains(user))
			this.usersList.add(user);
	}
	
	public void removeFromUsersList(User user)
	{
		this.usersList.remove(user);
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public Chat(String _name, byte[] key)
	{
		if (_name.indexOf(0) != -1)
			name = _name.substring(0, _name.indexOf(0));
		else
			name = _name;
	}
	
	public SecretKeySpec generateKey(byte[] k)
	{
		if (key != null)
			return key;
		
		if (k == null)
		{
			k = new byte[16];
			r.nextBytes(k);
		}
		
		key = new SecretKeySpec(k, "AES/ECB/NoPadding");
		return key;
	
	}

	public boolean hasKey() {
		return (key != null);
	}
	
}
