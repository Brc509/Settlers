package catan.z.oldCode.server.handler.moves;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import catan.server.command.moves.OfferTradeCommand;
import catan.server.command.moves.RoadBuildingCommand;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class MovesRoadBuildingHandler_Prod implements MovesRoadBuildingHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		Map<String, String> cookies = HandlerUtils.getCookies(arg0);
		
		InputStream is = arg0.getRequestBody();
		String json = HandlerUtils.inputStreamToString(is);
		
		Gson gson = new Gson();
		RoadBuildingCommand command = gson.fromJson(json, RoadBuildingCommand.class);
		int gameId = Integer.parseInt(HandlerUtils.getCookies(arg0).get("catan.game"));
		command.execute(gameId);
	}
}
