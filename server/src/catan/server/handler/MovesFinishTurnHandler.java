package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MovesFinishTurnHandler implements HttpHandler {

	public MovesFinishTurnHandler(Server server) {
		// TODO
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			if (Server.isDebugEnabled()) System.out.println("  /moves/finishTurn");
			HandlerUtils.sendSampleModel(exchange, HttpURLConnection.HTTP_OK);
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /moves/finishTurn.");
			HandlerUtils.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
