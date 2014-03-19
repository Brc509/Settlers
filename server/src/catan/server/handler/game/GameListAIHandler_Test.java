package catan.server.handler.game;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.sun.net.httpserver.HttpExchange;

public class GameListAIHandler_Test implements GameListAIHandler {

	// Static constants
	private static final String SAMPLE = "[\"LARGEST_ARMY\"]";

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
			if (Server.isDebugEnabled()) System.out.println("  /game/listAI");
			HandlerUtils.sendJSONString(exchange, HttpURLConnection.HTTP_OK, SAMPLE);
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /game/listAI.");
			HandlerUtils.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
