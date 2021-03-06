package catan.server.handler.user;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.sun.net.httpserver.HttpExchange;

public class UserRegisterHandler_Test implements UserRegisterHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Server.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			Server.println("  /user/register");
			HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_OK, "Success");
		} else {
			Server.println("  Bad request to /user/register.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
