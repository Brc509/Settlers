package catan.server;

import java.util.HashMap;
import java.util.Map;

import catan.model.Model;

public class Games {

	private static Games gamesSingleton;

	public Map<Integer, Model> games;

	private Games() {

		games = new HashMap<Integer, Model>();
		games.put(1, new Model("Default Game", false, false, false));
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
