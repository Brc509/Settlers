package catan.z.oldCode.server.handler.moves;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Games;
import catan.server.Server;
import catan.server.command.moves.SoldierCommand;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

/**
 * Handles requests to /moves/Soldier.
 * 
 * @author Spencer Bench
 */
public class MovesSoldierHandler_Prod implements MovesSoldierHandler {

	private static final Gson gson = new Gson();

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Server.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			if (HandlerUtils.authorizeUser(exchange)) {
				String requestBody = HandlerUtils.inputStreamToString(exchange.getRequestBody());
				SoldierCommand command = gson.fromJson(requestBody, SoldierCommand.class);
				int gameID = Integer.parseInt(HandlerUtils.getCookies(exchange).get("catan.game"));
				boolean success = command.execute(gameID);
				if (success) {
					Server.println("  Successful request to /moves/Soldier.");
					HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, Games.get().getGames().get(gameID).toString());
				} else {
					Server.println("  Invalid request to /moves/Soldier.");
					HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Your move to play a Soldier card was invalid.");
				}
			} else {
				Server.println("  Unauthorized request to /moves/Soldier.");
				HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_UNAUTHORIZED);
			}
		} else {
			Server.println("  Bad request to /moves/Soldier.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
