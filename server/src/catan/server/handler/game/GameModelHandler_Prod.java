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
				
				cm.initializeDefaultMap();
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
