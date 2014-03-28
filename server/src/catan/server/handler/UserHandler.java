package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import catan.server.Cookie;
import catan.server.RegisteredUser;
import catan.server.RegisteredUsers;
import catan.server.Server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class UserHandler implements HttpHandler {

	private static final Gson gson = new Gson();

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		Server.println("\n" + this.getClass().getSimpleName() + ":");

		// Check the request method
		String requestMethod = exchange.getRequestMethod().toUpperCase();
		if (requestMethod.equals("POST")) {

			// Get the list of registered users
			List<RegisteredUser> users = RegisteredUsers.get().getUsers();

			// Get the incoming username and password
			String requestBodyStr = HandlerUtils.inputStreamToString(exchange.getRequestBody());
			Map<String, String> requestParams = HandlerUtils.decodeQueryString(requestBodyStr);
			String username = requestParams.get("username");
			String password = requestParams.get("password");

			// Based on the request path, execute the appropriate operation
			RegisteredUser user = null;
			String endpoint = exchange.getRequestURI().getPath();
			if (endpoint.equals("/user/login")) {

				// Login the user
				Server.println("  " + endpoint);
				user = login(exchange, users, username, password);

			} else if (endpoint.equals("/user/register")) {

				// Register the user
				Server.println("  " + endpoint);
				user = register(exchange, users, username, password);

			} else {

				// Invalid path
				Server.println("  Path not found: \"" + endpoint + "\".");
				HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_NOT_FOUND);
				return;
			}

			// If the operation was successful, set the cookie and send the response
			if (user != null) {
				Cookie cookie = new Cookie(user.getName(), user.getPassword(), user.getPlayerID());
				String cookieStr = URLEncoder.encode(gson.toJson(cookie), "UTF-8");
				Server.println("  Successful login: \"" + user.getName() + "\", \"" + user.getPassword() + "\", " + user.getPlayerID() + ".");
				HandlerUtils.addCookie(exchange, "catan.user", cookieStr);
				HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_OK, "Success");
			} else {
				Server.println("  Failed: \"" + endpoint + "\".");
				HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR);
			}

		} else {
			Server.println("  Bad request method: \"" + requestMethod + "\".");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_METHOD);
		}
	}

	private RegisteredUser login(HttpExchange exchange, List<RegisteredUser> users, String username, String password) {

		// If the user is registered, return the user
		for (RegisteredUser user : users) {
			if (user.getName().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}

		return null;
	}

	private RegisteredUser register(HttpExchange exchange, List<RegisteredUser> users, String username, String password) {

		// Make sure the username is not already taken
		for (RegisteredUser user : users) {
			if (user.getName().equals(username)) {
				return null;
			}
		}

		// Register a new user
		int userID = users.size();
		RegisteredUser newUser = new RegisteredUser(username, password, userID);
		users.add(newUser);

		return newUser;
	}
}
