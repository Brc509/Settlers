package catan.server.handler.games;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GamesCreateHandler implements HttpHandler {

	// Static constants
	private static final String SAMPLE = "{\"title\":\"testgame\",\"id\":3,\"players\":[{},{},{},{}]}";

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			if (Server.isDebugEnabled()) System.out.println("  /games/create");
			HandlerUtils.sendJSONString(exchange, HttpURLConnection.HTTP_OK, SAMPLE);
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /games/create.");
			HandlerUtils.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
