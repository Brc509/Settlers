package catan.server.command.moves;

import catan.server.command.Command;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class MovesFinishTurnCommand implements Command{
	
	private String type;
	private int playerIndex;

	public MovesFinishTurnCommand(){
	}

	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
