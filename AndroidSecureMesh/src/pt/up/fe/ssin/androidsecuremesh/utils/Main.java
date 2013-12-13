package pt.up.fe.ssin.androidsecuremesh.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pt.up.fe.ssin.androidsecuremesh.ui.EnterChatRoom;
import pt.up.fe.ssin.androidsecuremesh.ui.Login;

public class Main {

	private SendDataThread sendDataThread;
	private ReceiveDataThread receiveDataThread;
	private SendTCPThread sendTCPThread;
	private ReceiveTCPThread receiveTCPThread;
	private SendUserInfoThread sendUserInfoThread;

public Main() throws IOException
	{
		receiveDataThread = new ReceiveDataThread();
		receiveDataThread.start();
		sendDataThread = new SendDataThread();
		sendDataThread.start();
		
		sendUserInfoThread = new SendUserInfoThread();
		sendUserInfoThread.start();
		
		//TCP Sockets for HandShake and Security
		sendTCPThread = new SendTCPThread();
		sendTCPThread.start();
		receiveTCPThread = new ReceiveTCPThread();
		receiveTCPThread.start();
		
	}

	public List<Chat> getChatList() {
		return Storage.chatsIn;
	}

	public boolean addToChatList(Chat chat) {

		boolean success = true;

		if(!containsInChatList(chat))
		{
			Storage.chatsIn.add(chat);	
		}
		else
		{
			success = false;
		}

		return success;
	}

	public static Chat getChatByName(String chatName) {
		for(Chat chat: Storage.chatsIn)
			if(chat.getName().equals(chatName))
				return chat;
		return null;
	}




	public List<User> getUserList() {
		return Storage.users;
	}

	public void addToUserList(String userName, int rating) {
		User user = new User(userName);
		user.rating = rating;
		Storage.users.add(user);
	}

	public boolean containsInUserList(String username)
	{
		for(User user: Storage.users)
			if(user.name.equals(username))
				return true;
		return false;
	}

	public boolean containsInChatList(Chat chatName)
	{
		for(Chat chat: Storage.chatsIn)
			if(chat.getName().equals(chatName))
				return true;
		return false;
	}
	
	public void deleteInChatList(Chat chat, String userN)
	{
		for(Chat ch: Storage.chatsIn)
			if(ch.getName().equals(ch.getName()))
				for(User user: ch.getUsersList())
					if(user.name.equals(userN))
						ch.removeFromUsersList(user);
	}
	
	

	public void deleteInUserList(String username) {
		for(int i=0; i<Storage.users.size(); i++)
			if(Storage.users.get(i).name.equals(username))
				Storage.users.remove(i);
	}

	public User getUserByUsername(String userName) {
		for(User user: Storage.users)
			if(user.name.equals(userName))
				return user;
		return null;
	}

	public void deleteChatItem(Chat theChat) {
		//theChat.setName(theChat.getName().replaceAll("\u0000.*", ""));
		for(Chat chat: Storage.chatsIn)
			if(chat.getName().equals(theChat.getName()))
				Storage.chatsIn.remove(chat);
	}

	public void changeUserRating(String userName, int rating) {

		for(User user: Storage.users)
			if(user.getName().equals(userName))
				user.rating = rating;
	}
}
