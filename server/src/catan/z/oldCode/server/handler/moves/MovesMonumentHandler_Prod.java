package catan.z.oldCode.server.handler.moves;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import catan.server.command.moves.MonumentCommand;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class MovesMonumentHandler_Prod implements MovesMonumentHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		System.out.println("fdjskafjdskal;fjdksalfjdals;");
		Map<String, String> cookies = HandlerUtils.getCookies(arg0);
		
		InputStream is = arg0.getRequestBody();
		String json = HandlerUtils.inputStreamToString(is);
		
		Gson gson = new Gson();
		MonumentCommand command = gson.fromJson(json, MonumentCommand.class);
		int gameId = Integer.parseInt(HandlerUtils.getCookies(arg0).get("catan.game"));
		command.execute(gameId);
	}
}
