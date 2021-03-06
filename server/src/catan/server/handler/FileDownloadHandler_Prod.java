package catan.server.handler;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import catan.server.Server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.sun.net.httpserver.HttpExchange;

/**
 * Handles requests to download files from the server.
 * 
 * @author Spencer Bench
 */
public class FileDownloadHandler_Prod implements FileDownloadHandler {

	// Instance constants
	private final String context;
	private final String root;

	@Inject
	public FileDownloadHandler_Prod(@Assisted("context") String context, @Assisted("root") String root) {
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
//		Server.println("\n" + this.getClass().getSimpleName() + " (Context: \"" + context + "\", Root: \"" + root + "\"):");
		URI uri = exchange.getRequestURI();
		String relativePath = uri.getPath().substring(context.length());
		// Ensure proper formatting
		if (!relativePath.startsWith("/")) {
			relativePath = "/" + relativePath;
		}
		// Test for the proper request method
		if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
//			if (Server.isDebugEnabled()) {
//				System.out.println("  Resource requested: \"" + uri.toString() + "\".");
//				System.out.println("  Relative to root: \"" + relativePath + "\".");
//			}
			File target = new File(System.getProperty("user.dir") + "/" + root + relativePath);
			// Test for target existence
			if (target.exists()) {
				if (target.isFile()) {
//					Server.println("  Resource is file.");
					// If the file has a trailing slash, redirect the client to the correct location
					if (uri.getPath().endsWith("/")) {
						String redirectStr = removeTailFromPath(uri, 1);
//						Server.println("  File has trailing slash. Redirecting client to \"" + redirectStr + "\".");
						exchange.getResponseHeaders().set("Location", redirectStr);
						HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_MOVED_PERM);
						return;
					}
					// Otherwise, send the target file
//					Server.println("  File found at \"" + target.getPath() + "\".");
					fileFound(exchange, target);
					return;
				} else if (target.isDirectory()) {
//					Server.println("  Resource is directory.");
					// If the directory is missing a trailing slash, redirect the client to the correct location
					if (!uri.getPath().endsWith("/")) {
						String redirectStr = addTailToPath(uri, "/");
//						Server.println("  Directory is missing trailing slash. Redirecting client to \"" + redirectStr + "\".");
						exchange.getResponseHeaders().set("Location", redirectStr);
						HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_MOVED_PERM);
						return;
					}
					// Otherwise, look for a default file in the directory and send it
					for (File child : target.listFiles()) {
						if (child.isFile() && child.getName().equals("index.html")) {
//							Server.println("  Default file found at \"" + child.getPath() + "\".");
							fileFound(exchange, child);
							return;
						}
					}
				}
			}
			// If any tests fail, report the file not found
			Server.println("  Could not find resource \"" + uri.toString() + "\".");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_NOT_FOUND);
		} else {
			Server.println("  Bad request to \"" + uri.toString() + "\".");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}

	private void fileFound(HttpExchange exchange, File file) throws IOException {
		// Generate ETag
		String eTag = String.valueOf(file.lastModified());
//		Server.println("  ETag: \"" + eTag + "\".");
		// Check for a requested ETag
		if (exchange.getRequestHeaders().containsKey("If-None-Match")) {
			String requestETag = exchange.getRequestHeaders().getFirst("If-None-Match");
			// Report if the file has not been modified
			if (requestETag.equals(eTag)) {
//				Server.println("  Client already has latest version.");
				HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_NOT_MODIFIED);
				return;
			}
		}
		// If there is no requested ETag or the file has been modified, send the file and its current ETag
		exchange.getResponseHeaders().set("ETag", eTag);
		HandlerUtils.sendFile(exchange, HttpURLConnection.HTTP_OK, file);
	}

	private String addTailToPath(URI uri, String tail) {
		StringBuilder sb = new StringBuilder();
		if (uri.getScheme() != null) sb.append(uri.getScheme() + ":");
		if (uri.getAuthority() != null) sb.append("//");
		if (uri.getUserInfo() != null) sb.append(uri.getUserInfo() + "@");
		if (uri.getHost() != null) sb.append(uri.getHost());
		if (uri.getPort() != -1) sb.append(":" + uri.getPort());
		if (uri.getPath() != null) sb.append(uri.getPath() + tail);
		if (uri.getQuery() != null) sb.append("?" + uri.getQuery());
		if (uri.getFragment() != null) sb.append("#" + uri.getFragment());
		return sb.toString();
	}

	private String removeTailFromPath(URI uri, int tailLength) {
		StringBuilder sb = new StringBuilder();
		if (uri.getScheme() != null) sb.append(uri.getScheme() + ":");
		if (uri.getAuthority() != null) sb.append("//");
		if (uri.getUserInfo() != null) sb.append(uri.getUserInfo() + "@");
		if (uri.getHost() != null) sb.append(uri.getHost());
		if (uri.getPort() != -1) sb.append(":" + uri.getPort());
		if (uri.getPath() != null) sb.append(uri.getPath().substring(0, uri.getPath().length() - tailLength));
		if (uri.getQuery() != null) sb.append("?" + uri.getQuery());
		if (uri.getFragment() != null) sb.append("#" + uri.getFragment());
		return sb.toString();
	}
}
