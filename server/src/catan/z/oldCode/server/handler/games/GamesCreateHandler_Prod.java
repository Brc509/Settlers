package catan.z.oldCode.server.handler.games;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;

import catan.model.Model;
import catan.server.GameListGames;
import catan.server.GameListPlayer;
import catan.server.Games;
import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

/**
 * Handles requests to /games/create.
 * 
 * @author Spencer Bench
 */
public class GamesCreateHandler_Prod implements GamesCreateHandler {

	// Static constants
//	private static final String SAMPLE =""; "{\"title\":\"testgame\",\"id\":3,\"players\":[{},{},{},{}]}";

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Server.println("\n" + this.getClass().getSimpleName() + ":");
//		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
//			
//			InputStream headers = exchange.getRequestBody();
//			String gameCreateInfo = HandlerUtils.inputStreamToString(headers);
//			Map<String,String> gameCreateInfoMap = HandlerUtils.decodeQueryString(gameCreateInfo);
//			
//			String name = gameCreateInfoMap.get("name");
//			Boolean randomNumbers = Boolean.parseBoolean(gameCreateInfoMap.get("randomNumbers"));
//			Boolean randomTiles = Boolean.parseBoolean(gameCreateInfoMap.get("randomTiles"));
//			Boolean randomPorts = Boolean.parseBoolean(gameCreateInfoMap.get("randomPorts"));
//			
//			Model model = new Model(name, randomNumbers ,randomTiles, randomPorts);
//				
//			Games catanGames = Games.get();
//			catanGames.addGame(model);
//			
//			Gson gson = new Gson();
//			
//			ArrayList<GameListPlayer> glp = new ArrayList<GameListPlayer>();
//			for(int i = 0; i < 4; i++){
//				glp.add(new GameListPlayer());
//			}
//			
//			GameListGames g = new GameListGames(name, catanGames.getGames().size(), glp);
//			
//			String jsonString = gson.toJson(g);
//			
//			Server.println("  /games/create");
//			
//			HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, jsonString);
//			
//		} else {
//			Server.println("  Bad request to /games/create.");
//			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
//		}
	}
}
