package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import catan.server.Server;
import catan.server.command.UtilChangeLogLevelCommand;

import com.sun.net.httpserver.HttpExchange;

public class UtilChangeLogLevelHandler_Prod implements UtilChangeLogLevelHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			String queryStr = HandlerUtils.inputStreamToString(exchange.getRequestBody());
			Map<String, String> params = HandlerUtils.decodeQueryString(queryStr);
			UtilChangeLogLevelCommand command = new UtilChangeLogLevelCommand(params.get("logLevel"));
			String response = command.execute();
			HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_OK, response);
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /util/changeLogLevel.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
