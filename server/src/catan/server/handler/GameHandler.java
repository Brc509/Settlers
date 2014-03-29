package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.model.Model;
import catan.server.Games;
import catan.server.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GameHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		Server.println("\n" + this.getClass().getSimpleName() + ":");

		// Based on the request path, execute the appropriate operation
		String requestMethod = exchange.getRequestMethod().toUpperCase();
		String endpoint = exchange.getRequestURI().getPath();
		
		System.out.println(endpoint);
		switch (endpoint) {
		case "/game/model" :
			if (HandlerUtils.checkRequestMethod("GET", exchange)){ getModel(exchange); }
			break;
		case "/game/reset" :
			break;
		case "/game/commands" :
			break;
		case "/game/AddAI" :
			break;
		case "/game/listAI" :
			break;
		default: 
			System.out.println("gameHandler. Endpoint: " + endpoint + ". Sent Bad Request");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
		//game/model
		//game/reset
		//game/commands
		//game/AddAI
		//game/listAI
		
		
	}
	
	private void getModel (HttpExchange exchange) throws IOException {
		int gameId = Integer.parseInt(HandlerUtils.getCookies(exchange).get("catan.game"));
		Model model = Games.get().getGames().get(gameId);
		HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, model.toString());
	}
	
	private boolean checkRequestMethod (String type, HttpExchange exchange) throws IOException {
		if (exchange.getRequestMethod().toUpperCase().equals(type)) {
			return true;
		} else {
			Server.println("  Bad request to /games/list." + exchange.getRequestMethod().toUpperCase());
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
			return false;
		}
	}

}
