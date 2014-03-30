package catan.z.oldCode.server.handler.moves;

import java.io.IOException;

import catan.server.command.moves.BuildRoadCommand;
import catan.server.command.moves.BuyDevCardCommand;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class MovesBuildRoadHandler_Prod implements MovesBuildRoadHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
	    java.util.Scanner s = new java.util.Scanner(arg0.getRequestBody()).useDelimiter("\\A");
	    Gson g = new Gson();
	    JsonObject json = g.fromJson(s.hasNext() ? s.next() : "", JsonObject.class);		
		//BuildRoadCommand test = new BuildRoadCommand(arg0, json);
		//test.execute(null);
	}
}
