package catan.server;

import java.util.ArrayList;

public class GameListGames {

	private String name;
	private int id;
	private ArrayList<GameListPlayer> players;
	
	public ArrayList<GameListPlayer> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<GameListPlayer> players) {
		this.players = players;
	}

	public GameListGames(String n, int id){
		this.name = n;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
