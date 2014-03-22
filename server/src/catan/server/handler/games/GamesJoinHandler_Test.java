package catan.server.handler.games;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.sun.net.httpserver.HttpExchange;

public class GamesJoinHandler_Test implements GamesJoinHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			if (Server.isDebugEnabled()) System.out.println("  /games/join");
			HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_OK, "Success! You have joined the game.");
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /games/join.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
