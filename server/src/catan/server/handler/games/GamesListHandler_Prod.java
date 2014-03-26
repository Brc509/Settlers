package catan.server.handler.games;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import catan.model.ClientModel;
import catan.server.Games;
import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.sun.net.httpserver.HttpExchange;

/**
 * Handles requests to /games/list.
 * 
 * @author Spencer Bench
 */
public class GamesListHandler_Prod implements GamesListHandler {

	// Static constants
	private static final String SAMPLE = "[{\"title\":\"Default Game\",\"id\":0,\"players\":[{\"color\":\"orange\",\"name\":\"Sam\",\"id\":0},{\"color\":\"blue\",\"name\":\"Brooke\",\"id\":1},{\"color\":\"red\",\"name\":\"Pete\",\"id\":10},{\"color\":\"green\",\"name\":\"Mark\",\"id\":11}]},{\"title\":\"AI Game\",\"id\":1,\"players\":[{\"color\":\"orange\",\"name\":\"Pete\",\"id\":10},{\"color\":\"yellow\",\"name\":\"Miguel\",\"id\":-2},{\"color\":\"green\",\"name\":\"Scott\",\"id\":-2},{\"color\":\"purple\",\"name\":\"Ken\",\"id\":-2}]},{\"title\":\"Empty Game\",\"id\":2,\"players\":[{\"color\":\"orange\",\"name\":\"Sam\",\"id\":0},{\"color\":\"blue\",\"name\":\"Brooke\",\"id\":1},{\"color\":\"red\",\"name\":\"Pete\",\"id\":10},{\"color\":\"green\",\"name\":\"Mark\",\"id\":11}]}]";

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
			
			Games games = Games.get();
			Map<Integer, ClientModel> gameList = games.getGames();
			for(int i = 0; i < gameList.size(); i++){
				ClientModel model = gameList.get(i);
				
			}
			
			if (Server.isDebugEnabled()) System.out.println("  /games/list");
			HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, SAMPLE);
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /games/list.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
