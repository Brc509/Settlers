package catan.server.handler.moves;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import catan.server.command.moves.BuildCityCommand;
import catan.server.command.moves.BuildSettlementCommand;
import catan.server.command.moves.FinishTurnCommand;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class MovesBuildSettlementHandler_Prod implements MovesBuildSettlementHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
		Map<String, String> cookies = HandlerUtils.getCookies(arg0);
		
		InputStream is = arg0.getRequestBody();
		String json = HandlerUtils.inputStreamToString(is);
		
		Gson gson = new Gson();
		BuildSettlementCommand command = gson.fromJson(json, BuildSettlementCommand.class);
		
		int gameId = Integer.parseInt(HandlerUtils.getCookies(arg0).get("catan.game"));
		
		System.out.println("gameId: "+gameId);

		JsonObject returnModel = (JsonObject) command.execute(gameId);
		
		System.out.println("model: "+returnModel.toString());
		
		try {
			HandlerUtils.sendStringAsJSON(arg0, 200, returnModel.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
