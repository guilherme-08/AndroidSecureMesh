package pt.up.fe.ssin.androidsecuremesh.utils;

import java.util.ArrayList;
import java.util.List;

public class Main {

	private List<Chat> chatList;
	private List<User> userList;
	
	public Main()
	{
		chatList = new ArrayList<Chat>();
		userList = new ArrayList<User>();
	}
	
	public List<Chat> getChatList() {
		return chatList;
	}

	public void addToChatList(Chat chat) {
		this.chatList.add(chat);
	}
	
	public Chat getChatByName(String chatName) {
		for(Chat chat: chatList)
			if(chat.getName().equals(chatName))
				return chat;
		return null;
	}
	
	
	

	public List<User> getUserList() {
		return userList;
	}

	public void addToUserList(String userName) {
		User user = new User(userName);
		this.userList.add(user);
	}
	
	public boolean containsInUserList(String username)
	{
		for(User user: userList)
			if(user.getName().equals(username))
				return true;
		return false;
	}

	public void deleteInUserList(String username) {
		for(int i=0; i<userList.size(); i++)
			if(userList.get(i).getName().equals(username))
				userList.remove(i);
	}

	public User getUserByUsername(String userName) {
		for(User user: userList)
			if(user.getName().equals(userName))
				return user;
		return null;
	}
}
