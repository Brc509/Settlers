package catan.server.handler.games;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.sun.net.httpserver.HttpExchange;

/**
 * Handles requests to /games/create.
 * 
 * @author Spencer Bench
 */
public class GamesCreateHandler_Prod implements GamesCreateHandler {

	// Static constants
	private static final String SAMPLE = "{\"title\":\"testgame\",\"id\":3,\"players\":[{},{},{},{}]}";

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			if (Server.isDebugEnabled()) System.out.println("  /games/create");
			HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, SAMPLE);
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /games/create.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
