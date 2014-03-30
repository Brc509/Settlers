package catan.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import catan.model.JsonPlugin;
import catan.server.Games;
import catan.server.command.games.GamesJoinCommand;
import catan.server.handler.MovesHandler;
import catan.server.handler.user.UserLoginHandler_Prod;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

public class MovesHandlerTests {
	private HttpExchange exchange;
	private MovesHandler movesHandler;
	private UserLoginHandler_Prod userHandler;

	@Before 
	public void beforeTest() {
		exchange = new MockExchange();
		movesHandler = new MovesHandler();
		userHandler = new UserLoginHandler_Prod();
		Games.get().getGames().put(1, new JsonPlugin(JsonPlugin.DEFAULTGAMEFILE));

	}
	

	@Test
	public void gamesGoodJoin(){
		
		GamesJoinCommand gjc = new GamesJoinCommand(0, 1, "orange", "Sam");
		boolean didJoin = gjc.execute(gjc);
		assertTrue(didJoin);
		

	}
	
	@Test
	public void gamesBadJoin(){
		
		GamesJoinCommand gjc = new GamesJoinCommand(0, 1, "orange", "Samantha");
		boolean didJoin = gjc.execute(gjc);
		assertFalse(didJoin);
	}
	
	@Test
	public void rollNumber() {
		
	}

}
