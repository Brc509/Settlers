package catan.model;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class JsonPlugin implements Model {

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
	
	public JsonPlugin() {

		try {
			FileReader file = new FileReader(NEWGAMEFILE);
			model = gson.fromJson(file, JsonObject.class);
			System.out.println(model);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JsonPlugin(boolean defaultGame) {
		try {
			FileReader file = new FileReader(DEFAULTGAMEFILE);
			model = gson.fromJson(file, JsonObject.class);
			System.out.println(model);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initGame(String name, boolean randomTokens, boolean randomHexes, boolean randomPorts) {
		gameName = name;
		initializeMap(randomTokens, randomHexes, randomPorts);
	}

	@Override
	public JsonObject getModel() {
		return model;
	}

	@Override
	public String getModelJSON() {
		model.addProperty("revision", revision);
		return model.toString();
	}

	@Override
	public String getModelJSONForRevision(int revision) {
		if (revision == this.revision) {
			return "\"true\"";
		}
		return getModelJSON();
	}

	@Override
	public String getName() {
		return gameName;
	}

	@Override
	public String getGamesListJSON(int id) {
		String s = "{\"title\":\"" + gameName + "\",\"id\":" + id + ",\"players\":[";
		JsonArray players = model.getAsJsonArray("players");
		int n = 1;
		for (JsonElement player : players) {
			JsonObject playerObj = player.getAsJsonObject();
			String name = playerObj.get("name").getAsString();
			String color = playerObj.get("color").getAsString();
			int playerID = playerObj.get("playerID").getAsInt();
			if (playerID != -1) {
				s += "{\"name\":\"" + name + "\",\"color\":\"" + color + "\",\"id\":" + playerID + "}";
			} else {
				s += "{}";
			}
			if (n < players.size()) {
				s += ",";
			}
			n++;
		}
		s += "]}";
		return s;
	}

	@Override
	public boolean setPlayer(int orderNumber, int userID, String name, String color) {
		boolean verdict = false;
		if (0 <= orderNumber && orderNumber <= 3) {

			JsonObject jsonPlayer = model.getAsJsonArray("players").get(orderNumber).getAsJsonObject();
			jsonPlayer.addProperty("playerID", userID);
			jsonPlayer.addProperty("name", name);
			jsonPlayer.addProperty("color", color);
			verdict = true;
		}
		return verdict;
		//Is it guaranteed that the players will be in the order of their orderNumber?
		/*
		JsonArray players = model.getAsJsonArray("players");
		for (JsonElement player : players) {
			if (((JsonObject) player).get("playerID").getAsInt() == userID)
				System.out.println("setPLAYER " + userID);
		}
		return false;
		*/
	}

	@Override
	public void initializeMap(boolean randomTokens, boolean randomHexes, boolean randomPorts) {

		if (randomTokens) randomizeTokens();
		if (randomHexes) randomizeHexes();
		if (randomPorts) randomizePorts();
	}

	/**
	 * Take the tokens (number values) from the default game and shuffle them
	 */
	public void randomizeTokens() {

		//TODO Implement
	}

	/**
	 * Take the hexes (land values) from the default game and shuffle them
	 */
	public void randomizeHexes() {

		//TODO Implement
	}

	/**
	 * Take the ports (9 port values) from the default game and shuffle them
	 */
	public void randomizePorts() {

		//TODO Implement
	}

	@Override
	public Player getPlayerByIndex(int playerIndex) {

		JsonObject jsonPlayer = model.getAsJsonArray("players").get(playerIndex).getAsJsonObject();
		return gson.fromJson(jsonPlayer, Player.class);
	}

	// ------------------------------
	// AWESOME HELPER METHODS
	// ------------------------------
	@Override
	public void rollNumber(int number) {
		//TODO AutoGenerated
		JsonArray numbers = model.getAsJsonObject("map").getAsJsonObject("numbers").getAsJsonArray(Integer.toString(number));
		JsonArray hexes = model.getAsJsonObject("map").getAsJsonObject("hexGrid").getAsJsonArray("hexes");
		
		for (JsonElement hexLocation : numbers) {
			JsonObject location = gson.fromJson(hexLocation, JsonObject.class);
			int x =  location.get("x").getAsInt();
			int y =  location.get("y").getAsInt();
			System.out.println("location" + location);
			
			// TODO get the hexes corresponding to the number's location
			// TODO iterate through those vertexes and find 1.)which players 2.)how much to reward them
//			JsonArray vertexes = getVertexes(hexes, x, y);
//			for (final JsonElement vertex : vertexes) {
//				JsonElement value = ((JsonObject) vertex).get("value");
//				int ownerId = ((JsonObject)value).get("ownerID").getAsInt();
//				int worth = ((JsonObject)value).get("worth").getAsInt();
//				if (ownerId != -1) {
//					System.out.println("Reward player: " + ownerId + " with " + worth + " of (resource?)");
//					System.out.println(getPlayerByIndex(playerIndex));
//					//TODO reward all of the players that are on the vertexes
//				}
//				else {
//					System.out.println("Nothing: " + ownerId);
//				}
//			}
		}
	}
	
	public Hex[][] getHexes() {

		JsonArray jsonHexes = model.getAsJsonObject("map").getAsJsonObject("hexGrid").getAsJsonArray("hexes");
		Type hexesType = new TypeToken<Hex[][]>() {}.getType();
		return gson.fromJson(jsonHexes, hexesType);
	}

	public JsonObject buildSettlement(int playerIndex) {
		return model;
	}

	@Override
	public Player[] getPlayers() {

		JsonArray jsonArray = model.getAsJsonArray("players");
		Type playerArrayType = new TypeToken<Player[]>() {}.getType();
		return gson.fromJson(jsonArray, playerArrayType);
	}

	@Override
	public HexLocation getRobberPosition() {

		JsonObject jsonRobber = model.getAsJsonObject("map").getAsJsonObject("robber");
		return gson.fromJson(jsonRobber, HexLocation.class);
	}

	@Override
	public Hex getHex(HexLocation location) {

		//TODO
		return null;
	}

	@Override
	public TurnTracker getTurnTracker() {
		JsonObject jsonTurnTracker = model.getAsJsonObject("turnTracker");
		return gson.fromJson(jsonTurnTracker, TurnTracker.class);
	}

	@Override
	public void addLogEntry(int playerIndex, String message) {
		String name = getPlayerByIndex(playerIndex).getName();
		message = name + message;
		model.getAsJsonObject("log").getAsJsonArray("lines").add(createEntry(name, message));
		revision ++;
	}

	@Override
	public void addChatEntry(int playerIndex, String content) {

		String name = getPlayerByIndex(playerIndex).getName();
		model.getAsJsonObject("chat").getAsJsonArray("lines").add(createEntry(name, content));
		revision ++;
	}

	private JsonElement createEntry(String source, String message) {
		return gson.fromJson("{\"source\":\"" + source + "\",\"message\":\"" + message + "\"}", JsonElement.class);
	}

	@Override
	public void setTurnTracker(TurnTracker track) {
		JsonObject jsonTurnTracker = model.getAsJsonObject("turnTracker");
		jsonTurnTracker.addProperty("status", track.getStatus());
		jsonTurnTracker.addProperty("currentTurn", track.getCurrentTurn());
	}
	
	@Override
	public DevCardList getDeck() {
		JsonObject jsonDeck = model.getAsJsonObject("deck");
		return gson.fromJson(jsonDeck, DevCardList.class);
	}

	@Override
	public void updatePlayer(int playerIndex, Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHex(HexLocation location, Hex hex) {
		// TODO Auto-generated method stub
		
	}
}
