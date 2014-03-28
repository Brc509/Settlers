package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class UserHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		String requestMethod = exchange.getRequestMethod().toUpperCase();
		if (requestMethod.equals("POST")) {
			String endpoint = exchange.getRequestURI().getPath();
			switch (endpoint) {
				case "/user/login":
					login(exchange);
					break;
				case "/user/register":
					register(exchange);
					break;
				default:
					if (Server.isDebugEnabled()) System.out.println("  Path not found: \"" + endpoint + "\".");
					HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_NOT_FOUND);
			}
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request method: \"" + requestMethod + "\".");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_METHOD);
		}
	}

	private void login(HttpExchange exchange) {
		// TODO
	}

	private void register(HttpExchange exchange) {
		// TODO
	}
}
