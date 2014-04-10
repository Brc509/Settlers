package catan.server;

import java.util.HashMap;
import java.util.Map;

import catan.model.GameModel;

public class Games {

	private static Games gamesSingleton;

	public Map<Integer, GameModel> games;

	private Games() {

		games = new HashMap<Integer, GameModel>();
		GameModel newGame = new GameModel(true);
		newGame.initGame("Default Game", false, false, false);
		games.put(1, newGame);
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

	public Integer getGameID(GameModel game) {
		for (Map.Entry<Integer, GameModel> e : games.entrySet()) {
			if (e.getValue() == game) {
				return e.getKey();
			}
		}
		return null;
	}

	public void addGame(GameModel cm) {
		games.put(games.size() + 1, cm);
	}

}
