package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;

import com.sun.net.httpserver.HttpExchange;

public class FileDownloadHandler_Test implements FileDownloadHandler {

	// Instance constants
	private final String context;

	public FileDownloadHandler_Test(String context, String root) {
		// Ensure proper formatting
		if (context.endsWith("/")) {
			context = context.substring(0, context.length() - 1);
		}
		if (!context.startsWith("/")) {
			context = "/" + context;
		}
		this.context = context;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		String relativePath = exchange.getRequestURI().getPath().substring(context.length());
		if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
			if (Server.isDebugEnabled()) System.out.println("  " + relativePath);
			HandlerUtils.sendSampleModel(exchange, HttpURLConnection.HTTP_OK);
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to " + relativePath + ".");
			HandlerUtils.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
