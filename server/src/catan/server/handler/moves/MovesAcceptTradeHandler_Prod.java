package catan.server.handler.moves;

import java.io.IOException;

import catan.server.command.moves.MovesAcceptTradeCommand;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

public class MovesAcceptTradeHandler_Prod implements MovesAcceptTradeHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
	    java.util.Scanner s = new java.util.Scanner(arg0.getRequestBody()).useDelimiter("\\A");
	    Gson g = new Gson();
	    JsonObject json = g.fromJson(s.hasNext() ? s.next() : "", JsonObject.class);		
		MovesAcceptTradeCommand test = new MovesAcceptTradeCommand(arg0, json);
	}
}
