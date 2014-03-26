package catan.server.handler.games;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import catan.server.Server;
import catan.server.command.games.GamesJoinCommand;
import catan.server.handler.HandlerUtils;

import com.sun.net.httpserver.HttpExchange;

/**
 * Handles requests to /games/join.
 * 
 * @author Spencer Bench
 */
public class GamesJoinHandler_Prod implements GamesJoinHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
//			if (HandlerUtils.authorizeUser(exchange)) {
			String formdataStr = HandlerUtils.inputStreamToString(exchange.getRequestBody());
			Map<String, String> formdata = HandlerUtils.decodeQueryString(formdataStr);
			String color = formdata.get("color");
			int gameID = Integer.parseInt(formdata.get("id"));
			GamesJoinCommand command = new GamesJoinCommand(HandlerUtils.getCookie(exchange).getId(), gameID, color);
			boolean success = command.execute();
			if (success) {
				HandlerUtils.addCookie(exchange, "catan.game", String.valueOf(gameID));
				HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_OK, "Success! You have joined the game.");
			} else {
				HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Failed to join game.");
			}
//			} else {
//				if (Server.isDebugEnabled()) System.out.println("  Unauthorized request to /games/join.");
//				HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_UNAUTHORIZED);
//			}
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /games/join.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
