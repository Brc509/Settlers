package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;

import catan.model.Model;
import catan.server.GameListGames;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.games.GamesJoinCommand;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GameHandler implements HttpHandler {
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String endPoint = exchange.getRequestURI().getPath();
		System.out.println(endPoint);
		// TODO switch statement
	}
	
	private void createGame (HttpExchange exchange) {
		try {
		
			if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
			if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
	
				ArrayList<GameListGames> games = new ArrayList<GameListGames>();
				Games theGames = Games.get();
				
				Map<Integer, Model> gameList = theGames.getGames();
				
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
			} else {
				if (Server.isDebugEnabled()) System.out.println("  Bad request to /games/list.");
				HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void joinGame (HttpExchange exchange) {
		try {
			if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
			System.out.println("Request type: " + exchange.getRequestMethod().toUpperCase());
			if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
				if (HandlerUtils.authorizeUser(exchange)) {
					String formdataStr = HandlerUtils.inputStreamToString(exchange.getRequestBody());
					Map<String, String> formdata = HandlerUtils.decodeQueryString(formdataStr);
					String color = formdata.get("color");
					int gameID = Integer.parseInt(formdata.get("id"));
					GamesJoinCommand command = new GamesJoinCommand(HandlerUtils.getCookie(exchange).getId(), gameID, color);
					boolean success = command.execute(null);
					if (success) {
						HandlerUtils.addCookie(exchange, "catan.game", String.valueOf(gameID));
						HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_OK, "Success! You have joined the game.");
					} else {
						HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Failed to join game.");
					}
				} else {
					if (Server.isDebugEnabled()) System.out.println("  Unauthorized request to /games/join.");
					HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_UNAUTHORIZED);
				}
			} else {
				if (Server.isDebugEnabled()) System.out.println("  Bad request to /games/join.");
				HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
			}
		}
		catch (Exception e) {
			
		}
	}
	
	private void listGames (HttpExchange exchange) {
		try {
			if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
			if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
	
				ArrayList<GameListGames> games = new ArrayList<GameListGames>();
				Games theGames = Games.get();
				
				Map<Integer, Model> gameList = theGames.getGames();
				
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
			} else {
				if (Server.isDebugEnabled()) System.out.println("  Bad request to /games/list.");
				HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
