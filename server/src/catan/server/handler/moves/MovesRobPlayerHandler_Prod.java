package catan.server.handler.moves;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import catan.server.command.moves.MovesOfferTradeCommand;
import catan.server.command.moves.MovesRobPlayerCommand;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class MovesRobPlayerHandler_Prod implements MovesRobPlayerHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		Map<String, String> cookies = HandlerUtils.getCookies(arg0);
		
		InputStream is = arg0.getRequestBody();
		String json = HandlerUtils.inputStreamToString(is);
		
		Gson gson = new Gson();
		MovesRobPlayerCommand command = gson.fromJson(json, MovesRobPlayerCommand.class);
		command.execute(null);
	}
}
