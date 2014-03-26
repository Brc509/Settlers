package catan.server.handler.moves;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import catan.server.command.moves.MovesBuildCityCommand;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class MovesBuildCityHandler_Prod implements MovesBuildCityHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {

		Map<String, String> cookies = HandlerUtils.getCookies(arg0);
		Gson gson = new Gson();
		InputStream is = arg0.getRequestBody();
		String json = HandlerUtils.inputStreamToString(is);
		
		MovesBuildCityCommand mbcc = gson.fromJson(json, MovesBuildCityCommand.class);
		
	}
}