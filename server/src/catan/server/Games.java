package catan.server;

import java.util.HashMap;
import java.util.Map;

import catan.model.GameModel;

public class Games {

	private static Games gamesSingleton;

	public Map<Integer, GameModel> games;

	private Games() {
		games = new HashMap<Integer, GameModel>();
	}

	public static Games get() {
		if (gamesSingleton == null) {
			gamesSingleton = new Games();
		}
		return gamesSingleton;
	}

	public Map<Integer, GameModel> getGames() {
		return games;
	}

	public void setGames(Map<Integer, GameModel> games) {
		this.games = games;
	}

	public int getGameID(GameModel game) {
		for (Map.Entry<Integer, GameModel> e : games.entrySet()) {
			if (e.getValue() == game) {
				return e.getKey();
			}
		}
		return -1;
	}

	public void addGame(GameModel game) {
		int gameID = games.size() + 1;
		games.put(gameID, game);
		Server.getPP().saveBaseline(gameID, game);
	}

}
