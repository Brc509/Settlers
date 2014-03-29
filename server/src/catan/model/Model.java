package catan.model;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Model {

	private static final String NEWGAMEFILE = "server/newGame.json";
	private static final String DEFAULTGAMEFILE = "server/defaultGame.json";
	private static final Gson gson = new Gson();

	private static final Set<String> resourceNames;

	static {
		resourceNames = new HashSet<>();
		resourceNames.add("brick");
		resourceNames.add("ore");
		resourceNames.add("sheep");
		resourceNames.add("wheat");
		resourceNames.add("wood");
	}

	private JsonObject model;
	private String gameName;
	private int revision = 0;

	public Model(String name, boolean randomTokens, boolean randomHexes, boolean randomPorts) {
		this.gameName = name;
		try {
			FileReader file = new FileReader(DEFAULTGAMEFILE);
			model = gson.fromJson(file, JsonObject.class);
			System.out.println(model);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * THIS METHOD IS TO BE USED BY TEST CLASSES ONLY!
	 * 
	 * @return The <code>JsonObject</code> internal model.
	 */
	protected JsonObject getModel() {
		return model;
	}

	public String getName() {
		return gameName;
	}

	public void initializeMap(boolean b, boolean c, boolean d) {
		// TODO Auto-generated method stub
	}

	public boolean setPlayer(int orderNumber, int userID, String name, String color) {
		// TODO Auto-generated method stub
		return false;
	}

	// *** MOVES METHODS START HERE *** //

	public boolean buildCity(int playerIndex, int x, int y, String direction, boolean free) {
		// TODO Auto-generated method stub
		return false;
	}

	public JsonObject finishTurn(int playerIndex) {
		JsonObject turnTracker = model.getAsJsonObject("turnTracker");
		int nextPlayer = turnTracker.get("currentTurn").getAsInt();

		System.out.println(model.get("players").getAsJsonArray().size());

		if (nextPlayer < (model.get("players").getAsJsonArray().size()) - 1) {
			nextPlayer++;
		} else { 
			nextPlayer = 0; 
		}

		System.out.println(nextPlayer);

		turnTracker.addProperty("currentTurn", nextPlayer);
//		addLogEntry(playerIndex, "'s turn just ended");
		return model;
	}

	public boolean maritimeTrade(String type, int playerIndex, int ratio, String inputResource, String outputResource) {
		return false;
	}

	public boolean monopoly(String type, String resource, int playerIndex) {
		return false;
	}

	public boolean monument(String type, int playerIndex) {
		return false;
	}

	public boolean offerTrade(String type, int playerIndex, ResourceList offer, int receiver) {
		return false;
	}

	public boolean roadBuilding(String type, int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		return false;
	}

	public boolean robPlayer(String type, int playerIndex, int victimIndex, HexLocation location) {
		boolean verdict = false;
		if (playerIndex >= 0 && playerIndex < 4 && victimIndex >= 0 && victimIndex < 4) {
			// This method is also used by soldier().
			boolean movedRobberAndRobbed = moveRobberAndRob(playerIndex, victimIndex, location);
			if (movedRobberAndRobbed) {
				// Add the log entry
				JsonArray players = model.getAsJsonArray("players");
				String victimName = players.get(victimIndex).getAsJsonObject().get("name").getAsString();
//				addLogEntry(playerIndex, " rolled a 7 and robbed " + victimName + ".");
				verdict = true;
			}
		}
		return verdict;
	}
	
	public boolean rollNumber ( String type, int playerIndex, int number) {
		return true;
	}

	public boolean sendChat(int playerIndex, String content) {
		boolean verdict = false;
		if (playerIndex >= 0 && playerIndex < 4) {
			String name = model.getAsJsonArray("players").get(playerIndex).getAsJsonObject().get("name").getAsString();
//			model.getAsJsonObject("chat").getAsJsonArray("lines").add(createEntry(name, content));
			verdict = true;
		}
		return verdict;
	}

	public boolean soldier(int playerIndex, int victimIndex, HexLocation location) {
		boolean verdict = false;
		if (playerIndex >= 0 && playerIndex < 4 && victimIndex >= 0 && victimIndex < 4) {

			JsonArray players = model.getAsJsonArray("players");
			JsonObject player = players.get(playerIndex).getAsJsonObject();

			// Make sure player has a Soldier card to play
			JsonObject oldDevCards = player.getAsJsonObject("oldDevCards");
			int oldPlayerSoldier = oldDevCards.get("soldier").getAsInt();
			if (oldPlayerSoldier > 0) {

				// Move the robber and rob the victim
				boolean movedRobberAndRobbed = moveRobberAndRob(playerIndex, victimIndex, location);
				if (movedRobberAndRobbed) {

					// Move the Soldier card back to the deck
					oldDevCards.addProperty("soldier", oldPlayerSoldier - 1);
					JsonObject deck = model.getAsJsonObject("deck");
					int oldDeckSoldier = deck.get("soldier").getAsInt();
					deck.addProperty("soldier", oldDeckSoldier + 1);

					// Add the log entry
					String victimName = players.get(victimIndex).getAsJsonObject().get("name").getAsString();
//					addLogEntry(playerIndex, " played a Soldier card and robbed " + victimName + ".");

					verdict = true;
				}
			}
		}
		return verdict;
	}

	private boolean moveRobberAndRob(int playerIndex, int victimIndex, HexLocation location) {
		boolean verdict = false;

		JsonObject robber = model.getAsJsonObject("map").getAsJsonObject("robber");

		// Make sure robber is moved from last location
		int oldX = robber.get("x").getAsInt();
		int oldY = robber.get("y").getAsInt();
		if (!(oldX == Integer.parseInt(location.getX()) && oldY == Integer.parseInt(location.getY()))) {

			// Make sure victim is valid for new location
			int newX = Integer.parseInt(location.getX());
			int newY = Integer.parseInt(location.getY());
			JsonObject newHex = model.getAsJsonObject("map").getAsJsonObject("hexGrid").getAsJsonArray("hexes").get(newX).getAsJsonArray().get(newY).getAsJsonObject();
			JsonArray vertexes = newHex.getAsJsonArray("vertexes");
			boolean foundVictim = false;
			for (JsonElement e : vertexes) {
				JsonObject vertexValue = e.getAsJsonObject().getAsJsonObject("value");
				int ownerID = vertexValue.get("ownerID").getAsInt();
				if (ownerID == victimIndex) {
					foundVictim = true;
					break;
				}
			}
			if (foundVictim) {
				// TODO Transfer a random resource from victim to player
			}
		}
		return verdict;
	}

	public boolean yearOfPlenty(int playerIndex, String resource1, String resource2) {

		// Guilty until proven innocent
		boolean verdict = false;

		// Check validity of playerIndex and resource names
		if (playerIndex >= 0 && playerIndex < 4 && resourceNames.contains(resource1) && resourceNames.contains(resource2)) {

			JsonArray players = model.getAsJsonArray("players");
			JsonObject player = players.get(playerIndex).getAsJsonObject();

			// Make sure player has a Year of Plenty card to play
			JsonObject oldDevCards = player.getAsJsonObject("oldDevCards");
			int oldPlayerYearOfPlenty = oldDevCards.get("yearOfPlenty").getAsInt();
			if (oldPlayerYearOfPlenty > 0) {

				// Get old bank resource values
				JsonObject bank = model.getAsJsonObject("bank");
				int bankNum1 = bank.get(resource1).getAsInt();
				int bankNum2 = bank.get(resource2).getAsInt();

				// Make sure the bank has resources to give
				boolean bankSufficient;
				int resourceDelta;
				if (resource1.equals(resource2)) {
					bankSufficient = bankNum1 > 1;
					resourceDelta = 2;
				} else {
					bankSufficient = bankNum1 > 0 && bankNum2 > 0;
					resourceDelta = 1;
				}
				if (bankSufficient) {

					// Get old player resource values
					JsonObject resources = player.getAsJsonObject("resources");
					int playerNum1 = resources.get(resource1).getAsInt();
					int playerNum2 = resources.get(resource2).getAsInt();

					// Increment the player's resources
					resources.addProperty(resource1, playerNum1 + resourceDelta);
					resources.addProperty(resource2, playerNum2 + resourceDelta);

					// Decrement the bank's resources
					bank.addProperty(resource1, bankNum1 - resourceDelta);
					bank.addProperty(resource2, bankNum2 - resourceDelta);

					// Move the Year of Plenty card back to the deck
					oldDevCards.addProperty("yearOfPlenty", oldPlayerYearOfPlenty - 1);
					JsonObject deck = model.getAsJsonObject("deck");
					int oldDeckYearOfPlenty = deck.get("yearOfPlenty").getAsInt();
					deck.addProperty("yearOfPlenty", oldDeckYearOfPlenty + 1);

					// Add the log entry
//					addLogEntry(playerIndex, " played a Year of Plenty card.");

					// Innocent
					verdict = true;
				}
			}
		}

		// Return the verdict
		return verdict;
	}

	// *** MOVES METHODS END HERE *** //

	public String getGameInfo(int id) {
		String s = "{ \"title\": \"" + gameName + "\", \"id\": " + id + ", \"players\": " + model.get("players") + "}";
		return s;
	}

	@Override
	public String toString() {
		return model.toString();
	}

	public JsonObject buildSettlement(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
