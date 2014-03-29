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
	public String getGameInfo(int id) {
		String s = "{ \"title\": \"" + gameName + "\", \"id\": " + id + ", \"players\": " + model.get("players") + "}";
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
	public Number[] getNumbers(int number) {
		//TODO AutoGenerated
		JsonArray numbers = model.getAsJsonObject("map").getAsJsonObject("numbers").getAsJsonArray(Integer.toString(number));
		return null;
	}

	@Override
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
	public Hex getHex(int x, int y) {

		Hex[][] hexes = getHexes();
		for (int i = 0; i < hexes.length; i++) {

			for (int j = 0; j < hexes[i].length; j++) {

				HexLocation loc = hexes[i][j].getLocation();
				if (Integer.parseInt(loc.getX()) == x && Integer.parseInt(loc.getY()) == y) return hexes[i][j];
			}
		}
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
	}

	@Override
	public void addChatEntry(int playerIndex, String content) {

		String name = getPlayerByIndex(playerIndex).getName();
		model.getAsJsonObject("chat").getAsJsonArray("lines").add(createEntry(name, content));
	}

	private JsonElement createEntry(String source, String message) {
		return gson.fromJson("{\"source\":\"" + source + "\",\"message\":\"" + message + "\"}", JsonElement.class);
	}

	@Override
	public int getSoldier() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTurnTracker(TurnTracker track) {
		JsonObject jsonTurnTracker = model.getAsJsonObject("turnTracker");
		jsonTurnTracker.addProperty("status", track.getStatus());
		jsonTurnTracker.addProperty("currentTurn", track.getCurrentTurn());
	}
}
