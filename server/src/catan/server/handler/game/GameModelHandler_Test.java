package catan.server.handler.game;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.sun.net.httpserver.HttpExchange;

public class GameModelHandler_Test implements GameModelHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
			if (Server.isDebugEnabled()) System.out.println("  /game/model");
			HandlerUtils.sendSampleModel(exchange, HttpURLConnection.HTTP_OK);
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /game/model.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
