package catan.server.handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;
import catan.model.Model;
import catan.server.GameListGames;
import catan.server.GameListPlayer;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.games.GamesJoinCommand;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GameHandler implements HttpHandler {
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		//TODO CHECK FOR BAD REQUESTS
		String endpoint = exchange.getRequestURI().getPath();
		System.out.println(endpoint);
		try {
			switch (endpoint) {
			case "/games/list":
				if (checkRequestMethod("GET", exchange)){ listGames(exchange); }
				break;
			case "/games/create":
				if (checkRequestMethod("POST", exchange)){ createGame(exchange); }
				break;
			case "/games/join":
				if (checkRequestMethod("POST", exchange)){ joinGame(exchange); }
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
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		Games games = Games.get();
		
		Map<Integer, Model> gameList = games.getGames();
		
		String jsonString = "[";
		int gameNum = 1;
		for (Map.Entry<Integer, Model> e : gameList.entrySet()) {
			Model game = e.getValue();
			jsonString += game.getGameInfo(e.getKey());
			if (gameNum < gameList.size()) {
				jsonString += ",";
			}
			gameNum++;
		}
		jsonString += "]";
		
		if (Server.isDebugEnabled()) System.out.println("  /games/list");
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
		
		Model model = new Model(name, randomNumbers ,randomTiles, randomPorts);
			
		Games catanGames = Games.get();
		catanGames.addGame(model);
		
		Gson gson = new Gson();
		
		ArrayList<GameListPlayer> glp = new ArrayList<GameListPlayer>();
		for(int i = 0; i < 4; i++){
			glp.add(new GameListPlayer());
		}
		
		GameListGames g = new GameListGames(name, catanGames.getGames().size(), glp);
		
		String jsonString = gson.toJson(g);
		if (Server.isDebugEnabled()) System.out.println("  /games/create");
		HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, jsonString);
	}
	
	private void joinGame (HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		System.out.println("Request type: " + exchange.getRequestMethod().toUpperCase());
		if (!HandlerUtils.authorizeUser(exchange)) {
			if (Server.isDebugEnabled()) System.out.println("  Unauthorized request to /games/join.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_UNAUTHORIZED);
			return;
		}
		String formdataStr = HandlerUtils.inputStreamToString(exchange.getRequestBody());
		Map<String, String> formdata = HandlerUtils.decodeQueryString(formdataStr);
		String color = formdata.get("color");
		int gameID = Integer.parseInt(formdata.get("id"));
		GamesJoinCommand command = new GamesJoinCommand(HandlerUtils.getCookie(exchange).getId(), gameID, color);
		if (command.execute(null)) {
			HandlerUtils.addCookie(exchange, "catan.game", String.valueOf(gameID));
			HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_OK, "Success! You have joined the game.");
		} else {
			HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Failed to join game.");
		}
	}
	
	private boolean checkRequestMethod (String type, HttpExchange exchange) throws IOException {
		if (exchange.getRequestMethod().toUpperCase().equals(type)) {
			return true;
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /games/list." + exchange.getRequestMethod().toUpperCase());
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
			return false;
		}
	}
}