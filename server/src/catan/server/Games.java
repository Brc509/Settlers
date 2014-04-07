package catan.server;

import java.util.HashMap;
import java.util.Map;

import catan.model.Model;
import catan.model.Model;

public class Games {

	private static Games gamesSingleton;

	public Map<Integer, Model> games;

	private Games() {

		games = new HashMap<Integer, Model>();
		Model newGame = new Model(true);
		newGame.initGame("Default Game", false, false, false);
		games.put(1, newGame);
	}

	public static Games get() {
		if (gamesSingleton == null) {
			gamesSingleton = new Games();

		}
		return gamesSingleton;
	}

	public Map<Integer, Model> getGames() {
		return games;
	}

	public void addGame(Model cm) {
		games.put(games.size() + 1, cm);
	}

}
