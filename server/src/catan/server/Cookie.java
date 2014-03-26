package catan.server;

public class Cookie {

	private String name;
	private String password;
	private int playerID;

	public Cookie(String username, String password, int id) {
		this.name = username;
		this.password = password;
		this.playerID = id;
	}

	public String getUsername() {
		return name;
	}

	public void setUsername(String username) {
		this.name = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return playerID;
	}

	public void setId(int id) {
		this.playerID = id;
	}
	
	
}
