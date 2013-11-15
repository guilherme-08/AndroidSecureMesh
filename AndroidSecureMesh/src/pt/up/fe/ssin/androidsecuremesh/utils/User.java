package pt.up.fe.ssin.androidsecuremesh.utils;

public class User {

	private String name;
	private String privateKey;
	
	public User(String name)
	{
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
	public String toString()
	{
		return this.name;
	}
	
}
