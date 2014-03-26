package catan.server.handler.user;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;

import catan.server.RegisteredUser;
import catan.server.RegisteredUsers;
import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.sun.net.httpserver.HttpExchange;

public class UserLoginHandler_Prod implements UserLoginHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		RegisteredUsers rUsers = RegisteredUsers.get();

		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			
			InputStream headers = exchange.getRequestBody();
			String loginInfo = HandlerUtils.inputStreamToString(headers);
			Map<String,String> loginInfoMap = HandlerUtils.decodeQueryString(loginInfo);
			
			RegisteredUser foundUser = null;
			
			Boolean didFind = false;
			
			for(int i = 0; i < rUsers.getUsers().size(); i++){
				String username = loginInfoMap.get("username");
				String password = loginInfoMap.get("password");
				
				String uNameToCheck = rUsers.getUsers().get(i).getName();
				String pToCheck = rUsers.getUsers().get(i).getPassword();
				
				if(uNameToCheck.equals(username) && pToCheck.equals(password)){
					didFind = true;
					foundUser = rUsers.getUsers().get(i);
					break;
				}		
			}
			
			if(didFind){

				HandlerUtils.addCookie(exchange, "catanUsername", loginInfoMap.get("username"));
				HandlerUtils.addCookie(exchange, "catanPassword", loginInfoMap.get("password"));
				HandlerUtils.addCookie(exchange, "catanUserID", "" + foundUser.getPlayerID());


				HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_OK, "Success");

			}else{
				HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "Incorrect Username or Password");
			}
						
			if (Server.isDebugEnabled()) System.out.println("  /user/login");
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /user/login.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
