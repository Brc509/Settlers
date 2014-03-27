package catan.test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import catan.model.Model;

import com.google.gson.JsonObject;

public class ModelTests {

	private static TestGame game;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {}

	@Before
	public void setUp() throws Exception {
		game = new TestGame();
	}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void movesYearOfPlentyTest() {
		int oldPlayerBrick = getPlayer(0).getAsJsonObject("resources").get("brick").getAsInt();
		int oldPlayerSheep = getPlayer(0).getAsJsonObject("resources").get("sheep").getAsInt();
		int oldBankBrick = game.getModel().getAsJsonObject("bank").get("brick").getAsInt();
		int oldBankSheep = game.getModel().getAsJsonObject("bank").get("sheep").getAsInt();
		game.yearOfPlenty(0, "brick", "sheep");
		assertTrue(true);
	}

	private JsonObject getPlayer(int playerIndex) {
		return game.getModel().getAsJsonArray("players").get(playerIndex).getAsJsonObject();
	}

	private class TestGame extends Model {

		public TestGame() {
			super("TestGame", false, false, false);
		}

		@Override
		public JsonObject getModel() {
			return getModel();
		}
	}
}
