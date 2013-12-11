package pt.up.fe.ssin.androidsecuremesh.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.spongycastle.util.Arrays;

public class Chat {
	private SecureRandom r = new SecureRandom();
	public String name;
	private SecretKeySpec key;
	private List<User> usersList = new ArrayList<User>();
	private User owner;
	
	public Chat(String _name)
	{
		if (_name.indexOf(0) != -1)
			name = _name.substring(0, _name.indexOf(0));
		else
			name = _name;
		byte[] keyBytes = new byte[64];
		
		//r.nextBytes(keyBytes);
		//same key for everyone!
		Arrays.fill(keyBytes, (byte) 68);
		
		key = new SecretKeySpec(keyBytes, "AES");
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
	
}
