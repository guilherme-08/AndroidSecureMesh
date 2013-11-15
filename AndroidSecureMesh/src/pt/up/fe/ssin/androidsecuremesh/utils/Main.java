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

	public List<User> getUserList() {
		return userList;
	}

	public void addToUserList(User user) {
		this.userList.add(user);
	}
}
