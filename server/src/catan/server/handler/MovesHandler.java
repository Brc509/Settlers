package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.model.GameModel;
import catan.server.Games;
import catan.server.command.Command;

// GSON
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MovesHandler implements HttpHandler {

	private Gson gson = new Gson();

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		int gameID = Integer.parseInt(HandlerUtils.getCookies(exchange).get("catan.game"));
		GameModel gameModel = Games.get().getGames().get(gameID);
		String endpoint = exchange.getRequestURI().getPath();
		String json = HandlerUtils.inputStreamToString(exchange.getRequestBody());
		String commandType = getCommandType(json);
		Class<? extends Command> commandClass = HandlerUtils.getCommandClassForEndpoint(endpoint);

		if (commandClass == null) {
			System.out.println("MovesHandler. commandType: " + commandType + ". Sent Bad Request");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
			return; //DON'T DO ANYTHING ELSE
		}

		Command c = gson.fromJson(json, commandClass);
		c.execute(gameModel);

		//if (error) send error response
		//else { send back the model appropriate to the game }
		// TODO
		HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, gameModel.getModelJSON());
	}

	private String getCommandType(String json) {
		JsonObject command = gson.fromJson(json, JsonObject.class);
		String type = command.get("type").getAsString();
		System.out.println("MovesHandler:COMMAND TYPE = " + type);
		return type;
	}

}
