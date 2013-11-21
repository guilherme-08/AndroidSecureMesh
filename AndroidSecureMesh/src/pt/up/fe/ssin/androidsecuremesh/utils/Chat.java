package pt.up.fe.ssin.androidsecuremesh.utils;

import java.util.ArrayList;
import java.util.List;

public class Chat {

	private String name;
	private String key;
	private static List<User> usersList = new ArrayList<User>();
	
	public Chat(String name)
	{
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String toString()
	{
		return name;
		
	}

	public static List<User> getUsersList() {
		return usersList;
	}

	public void addToUsersList(User user) {
		if(!this.usersList.contains(user))
			this.usersList.add(user);
	}
	
}
