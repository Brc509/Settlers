package catan.server.handler.game;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.model.ClientModel;
import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class GameModelHandler_Prod implements GameModelHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
			if (Server.isDebugEnabled()) System.out.println("  /game/model");
			ClientModel cm = new ClientModel();
			try {
				
				// Create default map and some player values
				cm.initializeMap(false, false, false);
				cm.getPlayers()[0].setPlayerID(0);
				cm.getPlayers()[0].setName("Pete");
				cm.getPlayers()[1].setName("Peter");
				cm.getPlayers()[2].setName("Petey");
				cm.getPlayers()[3].setName("Cole");
				cm.getPlayers()[0].setColor("red");
				cm.getPlayers()[1].setColor("orange");
				cm.getPlayers()[2].setColor("blue");
				cm.getPlayers()[3].setColor("green");
				
				Gson gson = new Gson();
				String model = gson.toJson(cm);
				HandlerUtils.sendStringAsJSON(exchange,  HttpURLConnection.HTTP_OK, model);
			} 
			catch (Exception e) {
				
				e.printStackTrace();
				
			}
			
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /game/model.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
