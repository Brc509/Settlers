package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

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
		if (endpoint.equals("/game/model") && requestMethod.equals("GET")) {

			Server.println("  /game/model");
			int gameID = Integer.parseInt(HandlerUtils.getCookies(exchange).get("catan.game"));
			Model game = Games.get().getGames().get(gameID);

			// Find the specified revision (if any)
			Integer revision = null;
			String queryStr = exchange.getRequestURI().getQuery();
			if (queryStr != null) {
				Map<String, String> queryParams = HandlerUtils.decodeQueryString(exchange.getRequestURI().getQuery());
				if (queryParams.containsKey("revision")) {
					try {
						revision = Integer.parseInt(queryParams.get("revision"));
					} catch (NumberFormatException e) {
						Server.println("  Client specified invalid revision.");
					}
				}
			}

			// Send the appropriate response
			if (revision != null) {
				Server.println("  Client has revision " + revision + ".");
				HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, game.getModelJSONForRevision(revision));
			} else {
				Server.println("  Client did not specify revision.");
				HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, game.getModelJSON());
			}
		}
	}
}
