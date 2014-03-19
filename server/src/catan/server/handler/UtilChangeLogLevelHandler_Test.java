package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;

import com.sun.net.httpserver.HttpExchange;

public class UtilChangeLogLevelHandler_Test implements UtilChangeLogLevelHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			if (Server.isDebugEnabled()) System.out.println("  /util/changeLogLevel");
			HandlerUtils.sendResponse(exchange, HttpURLConnection.HTTP_OK, "Success");
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /util/changeLogLevel.");
			HandlerUtils.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
