package catan.server.handler.user;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Map;

import catan.server.Cookie;
import catan.server.RegisteredUser;
import catan.server.RegisteredUsers;
import catan.server.Server;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class UserRegisterHandler_Prod implements UserRegisterHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		RegisteredUsers rUsers = RegisteredUsers.get();
		
		if (Server.isDebugEnabled()) System.out.println("\n" + this.getClass().getSimpleName() + ":");
		if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
			
			InputStream headers = exchange.getRequestBody();
			String registerInfor = HandlerUtils.inputStreamToString(headers);
			Map<String,String> registerInfoMap = HandlerUtils.decodeQueryString(registerInfor);
			
			Boolean didFind = false;
			
			for(int i = 0; i < rUsers.getUsers().size(); i++){
				String username = registerInfoMap.get("username");
				
				String uNameToCheck = rUsers.getUsers().get(i).getName();
				
				if(uNameToCheck.equals(username)){
					didFind = true;
					break;
				}		
			}
			
			if(!didFind){
				RegisteredUser rUser = new RegisteredUser(registerInfoMap.get("username"),registerInfoMap.get("password"), rUsers.getUsers().size());
				rUsers.addUser(rUser);
			}else{
				HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "Username already taken");
			}
			
			if (Server.isDebugEnabled()) System.out.println("  /user/register");
			
			Cookie cookie = new Cookie(registerInfoMap.get("username"), registerInfoMap.get("password"), rUsers.getUsers().size());
			Gson gson = new Gson();
			String cookieString = gson.toJson(cookie);
			cookieString = URLEncoder.encode(cookieString, "UTF-8");
			HandlerUtils.addCookie(exchange, "catan.user", cookieString);

			
			HandlerUtils.sendString(exchange, HttpURLConnection.HTTP_OK, "Success");
		} else {
			if (Server.isDebugEnabled()) System.out.println("  Bad request to /user/register.");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
}
