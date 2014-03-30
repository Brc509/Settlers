package catan.test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import catan.model.DevCardList;
import catan.model.Hex;
import catan.model.HexLocation;
import catan.model.Model;
import catan.model.Number;
import catan.model.Player;
import catan.model.ResourceList;
import catan.model.TurnTracker;

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
	public void movesYearOfPlenty_good1() {
		// Set configuration
		getPlayer(0).getAsJsonObject("oldDevCards").addProperty("yearOfPlenty", 1);
		game.getModel().getAsJsonObject("bank").addProperty("brick", 1);
		game.getModel().getAsJsonObject("bank").addProperty("sheep", 1);
		// Test configuration
		movesYearOfPlenty_test(0, "brick", "sheep", true);
	}

	@Test
	public void movesYearOfPlenty_good2() {
		// Set configuration
		getPlayer(0).getAsJsonObject("oldDevCards").addProperty("yearOfPlenty", 1);
		game.getModel().getAsJsonObject("bank").addProperty("brick", 2);
		// Test configuration
		movesYearOfPlenty_test(0, "brick", "brick", true);
	}

	@Test
	public void movesYearOfPlenty_bad_noDevCard() {
		// Set configuration
		getPlayer(0).getAsJsonObject("oldDevCards").addProperty("yearOfPlenty", 0);
		game.getModel().getAsJsonObject("bank").addProperty("brick", 1);
		game.getModel().getAsJsonObject("bank").addProperty("sheep", 1);
		// Test configuration
		movesYearOfPlenty_test(0, "brick", "sheep", false);
	}

	@Test
	public void movesYearOfPlenty_bad_noBank1() {
		// Set configuration
		getPlayer(0).getAsJsonObject("oldDevCards").addProperty("yearOfPlenty", 1);
		game.getModel().getAsJsonObject("bank").addProperty("brick", 1);
		game.getModel().getAsJsonObject("bank").addProperty("sheep", 0);
		// Test configuration
		movesYearOfPlenty_test(0, "brick", "sheep", false);
	}

	@Test
	public void movesYearOfPlenty_bad_noBank2() {
		// Set configuration
		getPlayer(0).getAsJsonObject("oldDevCards").addProperty("yearOfPlenty", 1);
		game.getModel().getAsJsonObject("bank").addProperty("brick", 1);
		// Test configuration
		movesYearOfPlenty_test(0, "brick", "brick", false);
	}

	private void movesYearOfPlenty_test(int pIndex, String r1, String r2, boolean expected) {

		JsonObject playerR = getPlayer(pIndex).getAsJsonObject("resources");
		JsonObject playerODC = getPlayer(pIndex).getAsJsonObject("oldDevCards");
		JsonObject bank = game.getModel().getAsJsonObject("bank");
		JsonObject deck = game.getModel().getAsJsonObject("deck");

		// Get old values
		int oldPlayerR1 = playerR.get(r1).getAsInt();
		int oldPlayerR2 = playerR.get(r2).getAsInt();
		int oldPlayerYOP = playerODC.get("yearOfPlenty").getAsInt();
		int oldBankR1 = bank.get(r1).getAsInt();
		int oldBankR2 = bank.get(r2).getAsInt();
		int oldDeckYOP = deck.get("yearOfPlenty").getAsInt();

		// Play a Year of Plenty card
//		boolean actual = game.yearOfPlenty(pIndex, r1, r2);

		// Get new values
		int newPlayerR1 = playerR.get(r1).getAsInt();
		int newPlayerR2 = playerR.get(r2).getAsInt();
		int newPlayerYOP = playerODC.get("yearOfPlenty").getAsInt();
		int newBankR1 = bank.get(r1).getAsInt();
		int newBankR2 = bank.get(r2).getAsInt();
		int newDeckYOP = deck.get("yearOfPlenty").getAsInt();

		// Compare old and new values
		if (expected) {
			// If the move is expected to succeed
			int rDelta = r1.equals(r2) ? 2 : 1;
//			assertTrue(actual);
			assertTrue(newPlayerR1 == oldPlayerR1 + rDelta);
			assertTrue(newPlayerR2 == oldPlayerR2 + rDelta);
			assertTrue(newPlayerYOP == oldPlayerYOP - 1);
			assertTrue(newBankR1 == oldBankR1 - rDelta);
			assertTrue(newBankR2 == oldBankR2 - rDelta);
			assertTrue(newDeckYOP == oldDeckYOP + 1);
		} else {
			// If the move is expected to fail
//			assertFalse(actual);
			assertTrue(newPlayerR1 == oldPlayerR1);
			assertTrue(newPlayerR2 == oldPlayerR2);
			assertTrue(newPlayerYOP == oldPlayerYOP);
			assertTrue(newBankR1 == oldBankR1);
			assertTrue(newBankR2 == oldBankR2);
			assertTrue(newDeckYOP == oldDeckYOP);
		}
	}

	private JsonObject getPlayer(int playerIndex) {
		return game.getModel().getAsJsonArray("players").get(playerIndex).getAsJsonObject();
	}

	private class TestGame implements Model {

		public TestGame() {
//			super("TestGame", false, false, false);
		}

		@Override
		public JsonObject getModel() {
//			return super.getModel();
			return null;
		}

		@Override
		public boolean setPlayer(int orderNumber, int userID, String name, String color) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void initializeMap(boolean b, boolean c, boolean d) {
			// TODO Auto-generated method stub

		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Number[] getNumbers(int number) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Player[] getPlayers() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void addLogEntry(int playerIndex, String message) {
			// TODO Auto-generated method stub

		}

		@Override
		public HexLocation getRobberPosition() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public TurnTracker getTurnTracker() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public void addChatEntry(int playerIndex, String content) {
			// TODO Auto-generated method stub

		}

		@Override
		public ResourceList getBank() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setBank(ResourceList bank) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public DevCardList getDeck() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getGamesListJSON(int id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Hex getHex(HexLocation location) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getModelJSON() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getModelJSONForRevision(int revision) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Player getPlayerByIndex(int playerIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ResourceList getPlayerResources(int playerIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void initGame(String string, boolean b, boolean c, boolean d) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void rollNumber(int number) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setHex(HexLocation location, Hex hex) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setPlayerResources(int playerIndex, ResourceList resources) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setTurnTracker(TurnTracker track) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void updatePlayer(int playerIndex, Player player) {
			// TODO Auto-generated method stub
			
		}

	}
}
