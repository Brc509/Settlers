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
import catan.model.Model;
import catan.model.Player;
import catan.model.TurnTracker;
import catan.server.Games;
import catan.server.command.games.GamesJoinCommand;
import catan.server.command.moves.FinishTurnCommand;
import catan.server.command.moves.RollNumberCommand;
import catan.server.handler.MovesHandler;
import catan.server.handler.user.UserLoginHandler_Prod;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

public class MovesHandlerTests {
	private HttpExchange exchange;
	private MovesHandler movesHandler;
	private UserLoginHandler_Prod userHandler;
	Gson gson = new Gson();


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
	public void finishTurn() {
		
		FinishTurnCommand ftc = gson.fromJson("{\"type\":\"finishTurn\", \"playerIndex\": 0}", FinishTurnCommand.class);
		ftc.execute((Integer)1);
		Model game = Games.get().getGames().get(1);
		TurnTracker tt = game.getTurnTracker();
		assertEquals(tt.getCurrentTurn(), 1);
		assertEquals(tt.getStatus(), "Rolling");
		
	}
	
	@Test
	public void rollNumberGood(){

		Model gamePre = Games.get().getGames().get(1);
		Player[] playersPre = gamePre.getPlayers();

		RollNumberCommand rnc = gson.fromJson("{\"type\": \"rollNumber\",\"playerIndex\": 0,\"number\": 10}", RollNumberCommand.class);
		rnc.execute((Integer)1);
		
		Model gamePost = Games.get().getGames().get(1);
		Player[] playersPost = gamePost.getPlayers();
		
		Player samPre = playersPre[0];
		Player samPost = playersPost[0];
		assertEquals(samPost.getResources().getSheep() - samPre.getResources().getSheep(), 3);
		

	}
	
	@Test
	public void sendChat(){
		
	}
	
	@Test 
	public void yearOfPlenty(){
		
	}
	
	@Test
	public void monopoly(){
		
	}
	

	
}
