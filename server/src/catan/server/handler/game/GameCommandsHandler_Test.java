package catan.server.handler.game;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.sun.net.httpserver.HttpExchange;

public class GameCommandsHandler_Test implements GameCommandsHandler {

	// Static constants
	private static final String SAMPLE = "[{\"type\":\"rollNumber\",\"playerIndex\":0,\"number\":8},{\"type\":\"finishTurn\",\"playerIndex\":0}]";

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Server.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
			Server.println("  /game/commands");
			HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, SAMPLE);
		} else if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			Server.println("  /game/commands");
			HandlerUtils.sendSampleModel(exchange, HttpURLConnection.HTTP_OK);
		} else {
			Server.println("  Bad request to /game/commands.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
