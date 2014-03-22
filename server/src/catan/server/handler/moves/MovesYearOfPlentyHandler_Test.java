package catan.server.handler.moves;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.sun.net.httpserver.HttpExchange;

public class MovesYearOfPlentyHandler_Test implements MovesYearOfPlentyHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			if (Server.isDebugEnabled()) System.out.println("  /moves/Year_of_Plenty");
			HandlerUtils.sendSampleModel(exchange, HttpURLConnection.HTTP_OK);
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /moves/Year_of_Plenty.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
