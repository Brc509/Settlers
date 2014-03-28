package catan.server.handler.moves;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import catan.model.Model;
import catan.server.Games;
import catan.server.command.moves.BuildSettlementCommand;
import catan.server.command.moves.FinishTurnCommand;
import catan.server.command.moves.MaritimeTradeCommand;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class MovesFinishTurnHandler_Prod implements MovesFinishTurnHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		
		Map<String, String> cookies = HandlerUtils.getCookies(arg0);
		
		InputStream is = arg0.getRequestBody();
		String json = HandlerUtils.inputStreamToString(is);
		
		Gson gson = new Gson();
		FinishTurnCommand command = gson.fromJson(json, FinishTurnCommand.class);
		
		int gameId = Integer.parseInt(HandlerUtils.getCookies(arg0).get("catan.game"));
		
		System.out.println("gameId: "+gameId);

		JsonObject returnModel = (JsonObject) command.execute(gameId);
		
//		Gson g = new Gson();
//		String model = g.toJson(returnModel);
		
		System.out.println("model: "+returnModel.toString());
		
		try {
			HandlerUtils.sendStringAsJSON(arg0, 200, returnModel.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
