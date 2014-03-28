package catan.server.handler.moves;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Games;
import catan.server.Server;
import catan.server.command.moves.YearOfPlentyCommand;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

/**
 * Handles requests to /moves/Year_of_Plenty.
 * 
 * @author Spencer Bench
 */
public class MovesYearOfPlentyHandler_Prod implements MovesYearOfPlentyHandler {

	private static final Gson gson = new Gson();

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			if (HandlerUtils.authorizeUser(exchange)) {
				String requestBody = HandlerUtils.inputStreamToString(exchange.getRequestBody());
				YearOfPlentyCommand command = gson.fromJson(requestBody, YearOfPlentyCommand.class);
				int gameID = Integer.parseInt(HandlerUtils.getCookies(exchange).get("catan.game"));
				boolean success = command.execute(gameID);
				if (success) {
					if (Server.isDebugEnabled()) System.out.println("  Successful request to /moves/Year_of_Plenty.");
					HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, Games.get().getGames().get(gameID).toString());
				} else {
					if (Server.isDebugEnabled()) System.out.println("  Invalid request to /moves/Year_of_Plenty.");
					HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Your move to play a Year of Plenty card was invalid.");
				}
			} else {
				if (Server.isDebugEnabled()) System.out.println("  Unauthorized request to /moves/Year_of_Plenty.");
				HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_UNAUTHORIZED);
			}
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /moves/Year_of_Plenty.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
