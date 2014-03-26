package catan.server.handler.moves;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Handler;

import catan.server.command.moves.MovesMaritimeTradeCommand;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class MovesMaritimeTradeHandler_Prod implements MovesMaritimeTradeHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		Map<String, String> cookies = HandlerUtils.getCookies(arg0);
		
		InputStream is = arg0.getRequestBody();
		String json = HandlerUtils.inputStreamToString(is);
		
		Gson gson = new Gson();
		MovesMaritimeTradeCommand command = gson.fromJson(json, MovesMaritimeTradeCommand.class);
		command.execute();		
		// TODO Auto-generated method stub
	}
}
