package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GamesHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		Server.println("\n" + this.getClass().getSimpleName() + ":");

		// Based on the request path, execute the appropriate operation
		String requestMethod = exchange.getRequestMethod().toUpperCase();
		String endpoint = exchange.getRequestURI().getPath();
		if (endpoint.equals("/games/list")) {
			if (requestMethod.equals("GET")) {

				// List existing games
				// TODO

			} else badMethod(exchange, requestMethod);
		} else if (endpoint.equals("/games/create")) {
			if (requestMethod.equals("POST")) {

				// Create a new game
				// TODO

			} else badMethod(exchange, requestMethod);
		} else if (endpoint.equals("/games/join")) {
			if (requestMethod.equals("POST")) {

				// Join an existing game
				// TODO

			} else badMethod(exchange, requestMethod);
		} else {

			// Invalid path
			Server.println("  Path not found: \"" + endpoint + "\".");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_NOT_FOUND);
			return;
		}
	}

	private void badMethod(HttpExchange exchange, String requestMethod) throws IOException {
		Server.println("  Bad request method: \"" + requestMethod + "\".");
		HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_METHOD);
	}
}
