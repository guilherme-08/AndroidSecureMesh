package pt.up.fe.ssin.androidsecuremesh.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pt.up.fe.ssin.androidsecuremesh.ui.EnterChatRoom;

public class Main {

	private List<Chat> chatList;
	private List<User> userList;
	private SendDataThread sendDataThread;
	private ReceiveDataThread receiveDataThread;

	public Main() throws IOException
	{
		chatList = new ArrayList<Chat>();
		userList = new ArrayList<User>();
		sendDataThread = new SendDataThread();
		receiveDataThread = new ReceiveDataThread();
		receiveDataThread.start();
		sendDataThread = new SendDataThread();
		sendDataThread.start();
	}

	public List<Chat> getChatList() {
		return chatList;
	}

	public boolean addToChatList(Chat chat) {

		boolean success = true;

		if(!containsInChatList(chat))
		{
			this.chatList.add(chat);	
		}
		else
		{
			success = false;
		}

		return success;
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

	public boolean containsInChatList(Chat chatName)
	{
		for(Chat chat: chatList)
			if(chat.getName().equals(chatName))
				return true;
		return false;
	}
	
	public void deleteInChatList(Chat chat, String userN)
	{
		for(Chat ch: chatList)
			if(ch.getName().equals(ch.getName()))
				for(User user: ch.getUsersList())
					if(user.getName().equals(userN))
						ch.removeFromUsersList(user);
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
