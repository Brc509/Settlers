package catan.server.handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;

import catan.model.JsonPlugin;
import catan.model.Model;
import catan.server.GameListGames;
import catan.server.GameListPlayer;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.games.GamesJoinCommand;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GamesHandler implements HttpHandler {
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		//TODO CHECK FOR BAD REQUESTS
		String endpoint = exchange.getRequestURI().getPath();
		System.out.println(endpoint);
		try {
			switch (endpoint) {
			case "/games/list":
				if (HandlerUtils.checkRequestMethod("GET", exchange)){ listGames(exchange); }
				break;
			case "/games/create":
				if (HandlerUtils.checkRequestMethod("POST", exchange)){ createGame(exchange); }
				break;
			case "/games/join":
				if (HandlerUtils.checkRequestMethod("POST", exchange)){ joinGame(exchange); }
				break;
			default: 
				System.out.println("gameHandler. Endpoint: " + endpoint + ". Sent Bad Request");
				HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void listGames (HttpExchange exchange) throws IOException {
		Server.println("\n" + this.getClass().getSimpleName() + ":");
		Games games = Games.get();
		
		Map<Integer, Model> gameList = games.getGames();
		
		String jsonString = "[";
		int gameNum = 1;
		for (Map.Entry<Integer, Model> e : gameList.entrySet()) {
			Model game = e.getValue();
			jsonString += game.getGamesListJSON(e.getKey());
			if (gameNum < gameList.size()) {
				jsonString += ",";
			}
			gameNum++;
		}
		jsonString += "]";
		
		Server.println("  /games/list");
		HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, jsonString);
	}
	
	private void createGame (HttpExchange exchange) throws IOException {			
		
		InputStream headers = exchange.getRequestBody();
		String gameCreateInfo = HandlerUtils.inputStreamToString(headers);
		Map<String,String> gameCreateInfoMap = HandlerUtils.decodeQueryString(gameCreateInfo);
		
		String name = gameCreateInfoMap.get("name");
		Boolean randomNumbers = Boolean.parseBoolean(gameCreateInfoMap.get("randomNumbers"));
		Boolean randomTiles = Boolean.parseBoolean(gameCreateInfoMap.get("randomTiles"));
		Boolean randomPorts = Boolean.parseBoolean(gameCreateInfoMap.get("randomPorts"));
		
		Model model = new JsonPlugin();
		model.initGame(name, randomNumbers ,randomTiles, randomPorts);
			
		Games catanGames = Games.get();
		catanGames.addGame(model);
		
		Gson gson = new Gson();
		
		ArrayList<GameListPlayer> glp = new ArrayList<GameListPlayer>();
		for(int i = 0; i < 4; i++){
			glp.add(new GameListPlayer());
		}
		
		GameListGames g = new GameListGames(name, catanGames.getGames().size(), glp);
		
		String jsonString = gson.toJson(g);
		Server.println("  /games/create");
		HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, jsonString);
	}
	
	private void joinGame (HttpExchange exchange) throws IOException {
		Server.println("\n" + this.getClass().getSimpleName() + ":");
		System.out.println("Request type: " + exchange.getRequestMethod().toUpperCase());
		if (!HandlerUtils.authorizeUser(exchange)) {
			Server.println("  Unauthorized request to /games/join.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_UNAUTHORIZED);
			return;
		}
		String formdataStr = HandlerUtils.inputStreamToString(exchange.getRequestBody());
		Map<String, String> formdata = HandlerUtils.decodeQueryString(formdataStr);
		String color = formdata.get("color");
		int gameID = Integer.parseInt(formdata.get("id"));
		String name = HandlerUtils.getCookie(exchange).getUsername();
		GamesJoinCommand command = new GamesJoinCommand(HandlerUtils.getCookie(exchange).getId(), gameID, color, name);
		if (command.execute(null)) {
			HandlerUtils.addCookie(exchange, "catan.game", String.valueOf(gameID));
			HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_OK, "Success! You have joined the game.");
		} else {
			HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Failed to join game.");
		}
	}
}

/*
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		Server.println("\n" + this.getClass().getSimpleName() + ":");

		// Based on the request path, execute the appropriate operation
		String requestMethod = exchange.getRequestMethod().toUpperCase();
		String endpoint = exchange.getRequestURI().getPath();
		if (endpoint.equals("/games/list")) {
			if (requestMethod.equals("GET")) {

				// List existing games
				// TODO

			} else badMethod(exchange, requestMethod);
		} else if (endpoint.equals("/games/create")) {
			if (requestMethod.equals("POST")) {

				// Create a new game
				// TODO

			} else badMethod(exchange, requestMethod);
		} else if (endpoint.equals("/games/join")) {
			if (requestMethod.equals("POST")) {

				// Join an existing game
				// TODO

			} else badMethod(exchange, requestMethod);
		} else {

			// Invalid path
			Server.println("  Path not found: \"" + endpoint + "\".");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_NOT_FOUND);
			return;
		}
	}

	private void badMethod(HttpExchange exchange, String requestMethod) throws IOException {
		Server.println("  Bad request method: \"" + requestMethod + "\".");
		HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_METHOD);
	}
}

 */