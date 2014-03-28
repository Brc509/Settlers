package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;

import com.sun.net.httpserver.HttpExchange;

public class UtilChangeLogLevelHandler_Test implements UtilChangeLogLevelHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Server.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			Server.println("  /util/changeLogLevel");
			HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_OK, "Success");
		} else {
			Server.println("  Bad request to /util/changeLogLevel.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
