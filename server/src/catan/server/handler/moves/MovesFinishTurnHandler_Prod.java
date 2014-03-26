package catan.server.handler.moves;

import java.io.IOException;

import catan.server.command.moves.MovesBuildSettlementCommand;
import catan.server.command.moves.MovesFinishTurnCommand;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class MovesFinishTurnHandler_Prod implements MovesFinishTurnHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
	    java.util.Scanner s = new java.util.Scanner(arg0.getRequestBody()).useDelimiter("\\A");
	    Gson g = new Gson();
	    JsonObject json = g.fromJson(s.hasNext() ? s.next() : "", JsonObject.class);		
		MovesFinishTurnCommand test = new MovesFinishTurnCommand(arg0, json);
		test.execute();
	}
}
