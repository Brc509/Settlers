package catan.z.oldCode.server.handler.moves;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Games;
import catan.server.Server;
import catan.server.command.moves.SendChatCommand;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

/**
 * Handles requests to /moves/sendChat.
 * 
 * @author Spencer Bench
 */
public class MovesSendChatHandler_Prod implements MovesSendChatHandler {

	private static final Gson gson = new Gson();

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Server.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			if (HandlerUtils.authorizeUser(exchange)) {
				String requestBody = HandlerUtils.inputStreamToString(exchange.getRequestBody());
				SendChatCommand command = gson.fromJson(requestBody, SendChatCommand.class);
				int gameID = Integer.parseInt(HandlerUtils.getCookies(exchange).get("catan.game"));
				boolean success = command.execute(gameID);
				if (success) {
					Server.println("  Successful request to /moves/sendChat.");
					HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, Games.get().getGames().get(gameID).toString());
				} else {
					Server.println("  Invalid request to /moves/sendChat.");
					HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Your move to send a chat message was invalid.");
				}
			} else {
				Server.println("  Unauthorized request to /moves/sendChat.");
				HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_UNAUTHORIZED);
			}
		} else {
			Server.println("  Bad request to /moves/sendChat.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
