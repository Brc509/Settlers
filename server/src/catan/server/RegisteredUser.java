package catan.server;

public class RegisteredUser {

	private String name;
	private String password;
	private int playerID;
	
	public RegisteredUser(String n, String p, int pID){
		this.name = n;
		this.password = p;
		this.playerID = pID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	
}
