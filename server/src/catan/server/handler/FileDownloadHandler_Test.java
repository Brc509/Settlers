package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.server.Server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.sun.net.httpserver.HttpExchange;

public class FileDownloadHandler_Test implements FileDownloadHandler {

	// Instance constants
	private final String context;
	private final String root;

	@Inject
	public FileDownloadHandler_Test(@Assisted("context") String context, @Assisted("root") String root) {
		// Ensure proper formatting
		if (context.endsWith("/")) {
			context = context.substring(0, context.length() - 1);
		}
		if (!context.startsWith("/")) {
			context = "/" + context;
		}
		this.context = context;
		this.root = root;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + " (Context: \"" + context + "\", Root: \"" + root + "\"):");
		String relativePath = exchange.getRequestURI().getPath().substring(context.length());
		if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
			if (Server.isDebugEnabled()) System.out.println("  " + relativePath);
			HandlerUtils.sendResponse(exchange, HttpURLConnection.HTTP_OK);
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to " + relativePath + ".");
			HandlerUtils.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
