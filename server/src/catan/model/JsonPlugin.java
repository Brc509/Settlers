package catan.model;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class JsonPlugin implements Model {

	public static final String NEWGAMEFILE = "server/newGame.json";
	public static final String DEFAULTGAMEFILE = "server/defaultGame.json";
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
	private static final int[] offsets = new int[] {3, 2, 1, 0, 0, 0, 0};

	private JsonObject model;
	private String gameName;
	private int revision = 0;

	public JsonPlugin() {

		this(NEWGAMEFILE);
	}
	
	public JsonPlugin(String gameFile) {
		try {
			FileReader file = new FileReader(gameFile);
			model = gson.fromJson(file, JsonObject.class);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JsonPlugin(boolean defaultGame) {
		try {
			FileReader file = new FileReader(DEFAULTGAMEFILE);
			model = gson.fromJson(file, JsonObject.class);
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
	public ArrayList <Hex> rollNumber(int number) {
		JsonArray numbers = model.getAsJsonObject("map").getAsJsonObject("numbers").getAsJsonArray(Integer.toString(number));
		JsonArray hexes = model.getAsJsonObject("map").getAsJsonObject("hexGrid").getAsJsonArray("hexes");
		
		ArrayList <Hex> hexList = new ArrayList <>();
		
		for (JsonElement location : numbers) {
			JsonObject l = gson.fromJson(location, JsonObject.class);
			HexLocation hexLocation = new HexLocation(l.get("x").getAsString(), l.get("y").getAsString());
			Hex hex = getHex(hexLocation);
			
			hexList.add(hex);
		}
		
		return hexList;
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

		int index1 = Integer.parseInt(location.getY()) + 3;
		int index2 = Integer.parseInt(location.getX()) + 3 - offsets[index1];
		return getHexes()[index1][index2];
	}
	
	@Override
	public EdgeLocation getEquivalentEdge(EdgeLocation thisEdge) {
		
		HexLocation otherHexLocation = getNeighborLocation(thisEdge.getX(), thisEdge.getY(), thisEdge.getDirectionIndex());
		int otherDirection = getOppositeDirection(thisEdge.getDirectionIndex());
		return new EdgeLocation(otherHexLocation.getXInt(), otherHexLocation.getYInt(), otherDirection);
	}

	private int getOppositeDirection(int direction){
	
		return positiveModulo((direction + 3),6);
	}

	@Override
	public VertexLocation[] getAllHexesForVertex(VertexLocation location) {
		VertexLocation[] locations = new VertexLocation[3];
		locations[0] = location; // this location
		
		// Get one adjacent hex that shares this vertex
		HexLocation loc1 = getNeighborLocation(location.getX(), location.getY(), positiveModulo(location.getDirectionIndex() - 1, 6));
		locations[1] = new VertexLocation(loc1.getXInt(), loc1.getYInt(), positiveModulo(location.getDirectionIndex() + 2, 6));
		
		// Get the other adjacent hex that shares this vertex
		HexLocation loc2 = getNeighborLocation(location.getX(), location.getY(), location.getDirectionIndex());
		locations[2] = new VertexLocation(loc2.getXInt(), loc2.getYInt(), positiveModulo(location.getDirectionIndex() + 4, 6));
		
		return locations;
	}
	
	private HexLocation getNeighborLocation(int x, int y, int hexDirection) {
		
		int deltaX = 0;
		int deltaY = 0;
		//["NW","N","NE","SE","S","SW"]
	    switch (hexDirection) {
			case 0: // NW
				deltaX = -1; deltaY = 0;
				break;
			case 1: // N
				deltaX = 0; deltaY = -1;
				break;
			case 2: // NE
				deltaX = 1; deltaY = -1;
				break;
			case 3: // SE
				deltaX = 1; deltaY = 0;
				break;
			case 4: // S
				deltaX = 0; deltaY = 1;
				break;
			case 5: // SW
				deltaX = -1; deltaY = 1;
				break;
			default:
				System.out.println("Invalid direction!");
		}
		return new HexLocation(deltaX + x, deltaY + y);
	}
	
	private int positiveModulo(int lhs, int rhs) {
		
		return ((lhs % rhs) + rhs) % rhs;
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
		revision++;
	}

	@Override
	public void addChatEntry(int playerIndex, String content) {

		String name = getPlayerByIndex(playerIndex).getName();
		model.getAsJsonObject("chat").getAsJsonArray("lines").add(createEntry(name, content));
		revision++;
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
	public void setHex(Hex hex) {
		HexLocation location = hex.getLocation();
		JsonArray hexes = model.getAsJsonObject("map").getAsJsonObject("hexGrid").getAsJsonArray("hexes");
		int index1 = Integer.parseInt(location.getY()) + 3;
		int index2 = Integer.parseInt(location.getX()) + 3 - offsets[index1];
		JsonObject target = hexes.get(index1).getAsJsonArray().get(index2).getAsJsonObject();
		target.add("edges", gson.toJsonTree(hex.getEdges()));
		target.add("vertexes", gson.toJsonTree(hex.getVertexes()));
	}

	@Override
	public ResourceList getPlayerResources(int playerIndex) {
		JsonElement resources = model.getAsJsonArray("players").get(playerIndex).getAsJsonObject().get("resources");
		return gson.fromJson(resources, ResourceList.class);
	}

	@Override
	public void setPlayerResources(int playerIndex, ResourceList resources) {
		JsonElement resourcesElement = gson.toJsonTree(resources);
		model.getAsJsonArray("players").get(playerIndex).getAsJsonObject().add("resources", resourcesElement);
	}
	
	@Override
	public ResourceList getBank() {
		return gson.fromJson(model.get("bank"), ResourceList.class);
	}

	@Override
	public void setBank(ResourceList bank) {
		model.add("bank", gson.toJsonTree(bank));
	}

	@Override
	public void setDeck(DevCardList deck) {
		model.add("deck", gson.toJsonTree(deck));
	}

	@Override
	public DevCardList getPlayerNewDevCards(int playerIndex) {
		JsonObject player = model.getAsJsonArray("players").get(playerIndex).getAsJsonObject();
		return gson.fromJson(player.get("newDevCards"), DevCardList.class);
	}

	@Override
	public DevCardList getPlayerOldDevCards(int playerIndex) {
		JsonObject player = model.getAsJsonArray("players").get(playerIndex).getAsJsonObject();
		return gson.fromJson(player.get("oldDevCards"), DevCardList.class);
	}

	@Override
	public void setPlayerNewDevCards(int playerIndex, DevCardList newDevCards) {
		JsonObject player = model.getAsJsonArray("players").get(playerIndex).getAsJsonObject();
		player.add("newDevCards", gson.toJsonTree(newDevCards));
	}

	@Override
	public void setPlayerOldDevCards(int playerIndex, DevCardList oldDevCards) {
		JsonObject player = model.getAsJsonArray("players").get(playerIndex).getAsJsonObject();
		player.add("oldDevCards", gson.toJsonTree(oldDevCards));
	}
}
