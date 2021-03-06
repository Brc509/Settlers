package catan.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import catan.model.GameModel;
import catan.model.Player;
import catan.model.ResourceList;
import catan.model.TurnTracker;
import catan.server.Games;
import catan.server.command.games.GamesJoinCommand;
import catan.server.command.moves.FinishTurnCommand;
import catan.server.command.moves.MaritimeTradeCommand;
import catan.server.command.moves.MonopolyCommand;
import catan.server.command.moves.RollNumberCommand;
import catan.server.command.moves.SendChatCommand;
import catan.server.command.moves.YearOfPlentyCommand;
import catan.server.handler.MovesHandler;
import catan.server.handler.user.UserLoginHandler_Prod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class MovesHandlerTests {

	private HttpExchange exchange;
	private MovesHandler movesHandler;
	private UserLoginHandler_Prod userHandler;
	Gson gson = new Gson();

	@Before
	public void beforeTest() {
		movesHandler = new MovesHandler();
		userHandler = new UserLoginHandler_Prod();
		Games.get().getGames().put(1, new GameModel(GameModel.DEFAULTGAMEFILE));

	}

	@Test
	public void gamesGoodJoin() {

		GamesJoinCommand gjc = new GamesJoinCommand(0, 1, "orange", "Sam");
		boolean didJoin = gjc.execute(null);
		assertTrue(didJoin);

	}

	@Test
	public void gamesBadJoin() {

		GamesJoinCommand gjc = new GamesJoinCommand(0, 1, "orange", "Samantha");
		boolean didJoin = gjc.execute(null);
		assertFalse(didJoin);
	}

	@Test
	public void finishTurn() {

		FinishTurnCommand ftc = gson.fromJson("{\"type\":\"finishTurn\", \"playerIndex\": 0}", FinishTurnCommand.class);
		GameModel game = Games.get().getGames().get(1);
		ftc.execute(game);
		TurnTracker tt = game.getTurnTracker();
		assertEquals(tt.getCurrentTurn(), 1);
		assertEquals(tt.getStatus(), "Rolling");

	}

	@Test
	public void rollNumberGood() {

		GameModel gamePre = Games.get().getGames().get(1);
		Player[] playersPre = gamePre.getPlayers();

		RollNumberCommand rnc = gson.fromJson("{\"type\": \"rollNumber\",\"playerIndex\": 0,\"number\": 10}", RollNumberCommand.class);
		rnc.execute(gamePre);

		GameModel gamePost = Games.get().getGames().get(1);
		Player[] playersPost = gamePost.getPlayers();

		Player samPre = playersPre[0];
		Player samPost = playersPost[0];
		assertEquals(samPost.getResources().getSheep() - samPre.getResources().getSheep(), 3);

	}

	@Test
	public void sendChat() {

		GameModel premodel = Games.get().getGames().get(1);
		JsonObject premodelJson = premodel.getModel();
		JsonObject prechat = premodelJson.getAsJsonObject("chat");
		JsonArray prechatArray = prechat.getAsJsonArray("lines");
		int preSize = prechatArray.size();

		SendChatCommand scc = gson.fromJson("{\"type\": \"sendChat\",\"playerIndex\": 0,\"content\": \"hello world\"}", SendChatCommand.class);
		scc.execute(premodel);

		GameModel model = Games.get().getGames().get(1);
		JsonObject modelJson = model.getModel();
		JsonObject chat = modelJson.getAsJsonObject("chat");
		JsonArray chatArray = chat.getAsJsonArray("lines");
		int postSize = chatArray.size();

		assert (preSize < postSize);

	}

	@Test
	public void yearOfPlenty() {

		GameModel gamePre = Games.get().getGames().get(1);
		Player[] playersPre = gamePre.getPlayers();

		YearOfPlentyCommand yopc = gson.fromJson("{	\"type\": \"Year_of_Plenty\", \"playerIndex\": 0, \"resource1\": \"sheep\", \"resource2\": \"brick\"}", YearOfPlentyCommand.class);
		yopc.execute(gamePre);

		GameModel gamePost = Games.get().getGames().get(1);
		Player[] playersPost = gamePost.getPlayers();

		Player samPre = playersPre[0];
		Player samPost = playersPost[0];

		ResourceList preList = samPre.getResources();
		ResourceList postList = samPost.getResources();

		assertEquals(postList.getSheep() - preList.getSheep(), 1);
		assertEquals(postList.getBrick() - preList.getBrick(), 1);

	}

	@Test
	public void monopoly() {

		GameModel gamePre = Games.get().getGames().get(1);
		Player[] playersPre = gamePre.getPlayers();

		MonopolyCommand mc = gson.fromJson("{\"type\": \"Monopoly\",\"resource\": \"brick\",\"playerIndex\": 0}", MonopolyCommand.class);
		mc.execute(gamePre);

		GameModel gamePost = Games.get().getGames().get(1);
		Player[] playersPost = gamePost.getPlayers();

		Player samPre = playersPre[0];
		Player samPost = playersPost[0];

		ResourceList preList = samPre.getResources();
		ResourceList postList = samPost.getResources();

		assertEquals(postList.getBrick() - preList.getBrick(), 6);

	}

	@Test
	public void maritimeTrade() {

		GameModel gamePre = Games.get().getGames().get(1);
		Player[] playersPre = gamePre.getPlayers();

		MaritimeTradeCommand mtc = gson.fromJson("{\"type\": \"maritimeTrade\",\"playerIndex\": 0,	\"ratio\": 4,\"inputResource\": \"sheep\",\"outputResource\": \"brick\"}", MaritimeTradeCommand.class);
		mtc.execute(gamePre);

		GameModel gamePost = Games.get().getGames().get(1);
		Player[] playersPost = gamePost.getPlayers();

		Player samPre = playersPre[0];
		Player samPost = playersPost[0];

		ResourceList preList = samPre.getResources();
		ResourceList postList = samPost.getResources();

		assertEquals(postList.getBrick() - preList.getBrick(), 1);
		assertEquals(preList.getSheep() - postList.getSheep(), 4);

	}

}
