package catan.server.command.moves;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import catan.server.handler.*;

import com.sun.net.httpserver.HttpExchange;
import catan.server.command.Command;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

public class AcceptTradeCommand implements Command{
	
	HttpExchange arg0;
	JsonObject json;

	public AcceptTradeCommand(HttpExchange arg0,JsonObject json) {
		// TODO Auto-generated constructor stub
		this.arg0 = arg0;
		this.json = json;
		//execute();
	}

	@Override
	public Object execute(Object obj) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getRequestURI().getPath());
		System.out.println(json.toString());
		
		
		try {
			HandlerUtils.sendStringAsJSON(arg0, 200, json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
