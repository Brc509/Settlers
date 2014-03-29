package catan.server.handler;

import java.io.IOException;

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
	}

}
