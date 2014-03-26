package catan.server;

import java.util.ArrayList;

public class GameListGames {

	private String title;
	private int id;
	private ArrayList<GameListPlayer> players;
	
	public GameListGames(String n, int id, ArrayList<GameListPlayer> p){
		this.title = n;
		this.id = id;
		this.players = p;
	}

	
	public ArrayList<GameListPlayer> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<GameListPlayer> players) {
		this.players = players;
	}

	public String getName() {
		return title;
	}

	public void setName(String name) {
		this.title = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
