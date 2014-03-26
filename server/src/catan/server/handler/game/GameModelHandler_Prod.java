package catan.server.handler.game;

import java.io.IOException;

import catan.server.command.game.GameModelCommand;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class GameModelHandler_Prod implements GameModelHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		// TODO Auto-generated method stub
		
		Headers headers = httpExchange.getRequestHeaders();
		//if headers are good
		//get current model
		
		GameModelCommand gmc = new GameModelCommand();
		gmc.execute();
	}
}
