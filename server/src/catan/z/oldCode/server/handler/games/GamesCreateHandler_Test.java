package catan.z.oldCode.server.handler.games;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.sun.net.httpserver.HttpExchange;

public class GamesCreateHandler_Test implements GamesCreateHandler {

	// Static constants
	private static final String SAMPLE = "{\"title\":\"testgame\",\"id\":3,\"players\":[{},{},{},{}]}";

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Server.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			Server.println("  /games/create");
			HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, SAMPLE);
		} else {
			Server.println("  Bad request to /games/create.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
