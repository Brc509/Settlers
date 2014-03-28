package catan.server.handler.moves;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Handler;

import catan.server.command.moves.MaritimeTradeCommand;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class MovesMaritimeTradeHandler_Prod implements MovesMaritimeTradeHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Map<String, String> cookies = HandlerUtils.getCookies(exchange);
		
		InputStream is = exchange.getRequestBody();
		String json = HandlerUtils.inputStreamToString(is);
		
		Gson gson = new Gson();
		MaritimeTradeCommand command = gson.fromJson(json, MaritimeTradeCommand.class);
		int gameId = Integer.parseInt(HandlerUtils.getCookies(exchange).get("catan.game"));
		command.execute(gameId);		
		// TODO Auto-generated method stub
	}
}
