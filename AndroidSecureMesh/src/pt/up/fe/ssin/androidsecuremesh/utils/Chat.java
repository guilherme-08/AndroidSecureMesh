package pt.up.fe.ssin.androidsecuremesh.utils;

public class Chat {

	private String name;
	private String key;
	
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
	
}
