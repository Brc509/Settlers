package catan.server.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class UserHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String endPoint = exchange.getRequestURI().getPath();
		System.out.println(endPoint);
		// TODO Auto-generated method stub

	}

}
