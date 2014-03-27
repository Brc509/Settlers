package catan.server.handler.moves;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import catan.model.Model;
import catan.server.Games;
import catan.server.command.moves.MovesBuildSettlementCommand;
import catan.server.command.moves.MovesFinishTurnCommand;
import catan.server.command.moves.MovesMaritimeTradeCommand;
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
		MovesFinishTurnCommand command = gson.fromJson(json, MovesFinishTurnCommand.class);
		int gameId = Integer.parseInt(HandlerUtils.getCookies(arg0).get("catan.game"));
		Model returnModel = (Model)command.execute(gameId);
		
		Gson g = new Gson();
		String model = g.toJson(returnModel);
		
		try {
			HandlerUtils.sendStringAsJSON(arg0, 200, model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
