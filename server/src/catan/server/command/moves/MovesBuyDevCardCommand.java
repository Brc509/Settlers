package catan.server.command.moves;

import catan.server.command.Command;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class MovesBuyDevCardCommand implements Command{
	
	HttpExchange arg0;
	JsonObject json;

	public MovesBuyDevCardCommand(HttpExchange arg0,JsonObject json){
		// TODO Auto-generated constructor stub
		this.arg0 = arg0;
		this.json = json;
		//execute();
	}

	@Override
	public Object execute(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
