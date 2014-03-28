package catan.z.oldCode.server.handler.moves;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.sun.net.httpserver.HttpExchange;

public class MovesSoldierHandler_Test implements MovesSoldierHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Server.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			Server.println("  /moves/Soldier");
			HandlerUtils.sendSampleModel(exchange, HttpURLConnection.HTTP_OK);
		} else {
			Server.println("  Bad request to /moves/Soldier.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
