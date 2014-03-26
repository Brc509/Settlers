package catan.server.command.moves;

import catan.server.command.Command;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class MovesDiscardCardsCommand implements Command{
	
	HttpExchange arg0;
	JsonObject json;

	public MovesDiscardCardsCommand(HttpExchange arg0,JsonObject json) {
		// TODO Auto-generated constructor stub
		this.arg0 = arg0;
		this.json = json;
		execute();
	}

	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
