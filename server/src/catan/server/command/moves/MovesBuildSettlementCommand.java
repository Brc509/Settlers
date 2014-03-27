package catan.server.command.moves;

import java.io.IOException;
import java.util.Map;

import catan.model.ClientModel;
import catan.model.Model;
import catan.server.Cookie;
import catan.server.Games;
import catan.server.command.Command;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class MovesBuildSettlementCommand implements Command{
	
	HttpExchange arg0;
	JsonObject json;

	public MovesBuildSettlementCommand(HttpExchange arg0,JsonObject json){
		// TODO Auto-generated constructor stub
		this.arg0 = arg0;
		this.json = json;
		//execute();
	}

	@Override
	public Object execute(Object obj){
		// TODO Auto-generated method stub
		Map<String, String> cookies = HandlerUtils.getCookies(arg0);
		Cookie playerCookie = HandlerUtils.getCookie(arg0);

		Model currModel = Games.get().getGames().get(cookies.get("catan.game"));
		
		currModel.buildCity(playerCookie.getId(),
				json.get("vertexLocation").getAsJsonObject().get("x").getAsInt(),
				json.get("vertexLocation").getAsJsonObject().get("y").getAsInt(),
				json.get("vertexLocation").getAsJsonObject().get("direction").getAsString(),
				json.get("free").getAsBoolean());
		
		Gson g = new Gson();
		String model = g.toJson(currModel);
		
		try {
			HandlerUtils.sendStringAsJSON(arg0, 200, model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
