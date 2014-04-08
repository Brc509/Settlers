package catan.server.handler.game;

import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;

import catan.model.GameModel;
import catan.server.Games;
import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class GameModelHandler_Prod implements GameModelHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		int gameId = Integer.parseInt(HandlerUtils.getCookies(exchange).get("catan.game"));
		GameModel model = Games.get().getGames().get(gameId);
		HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, model.toString());
	}
}
