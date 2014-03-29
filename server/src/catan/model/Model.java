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
        return null;
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
        return false;
    }
		
	
	public boolean rollNumber ( String type, int playerIndex, int number) {
		return true;
	}

	public boolean sendChat(int playerIndex, String content) {
		return false;
	}

	public boolean soldier(int playerIndex, int victimIndex, HexLocation location) {
		return false;
	}

//	private boolean moveRobberAndRob(int playerIndex, int victimIndex, HexLocation location) {
//		boolean verdict = false;
//
//		JsonObject robber = model.getAsJsonObject("map").getAsJsonObject("robber");
//
//		// Make sure robber is moved from last location
//		int oldX = robber.get("x").getAsInt();
//		int oldY = robber.get("y").getAsInt();
//		if (!(oldX == Integer.parseInt(location.getX()) && oldY == Integer.parseInt(location.getY()))) {
//
//			// Make sure victim is valid for new location
//			int newX = Integer.parseInt(location.getX());
//			int newY = Integer.parseInt(location.getY());
//			JsonObject newHex = model.getAsJsonObject("map").getAsJsonObject("hexGrid").getAsJsonArray("hexes").get(newX).getAsJsonArray().get(newY).getAsJsonObject();
//			JsonArray vertexes = newHex.getAsJsonArray("vertexes");
//			boolean foundVictim = false;
//			for (JsonElement e : vertexes) {
//				JsonObject vertexValue = e.getAsJsonObject().getAsJsonObject("value");
//				int ownerID = vertexValue.get("ownerID").getAsInt();
//				if (ownerID == victimIndex) {
//					foundVictim = true;
//					break;
//				}
//			}
//			if (foundVictim) {
//				// TODO Transfer a random resource from victim to player
//			}
//		}
//		return verdict;
//	}

	public boolean yearOfPlenty(int playerIndex, String resource1, String resource2) {

        return false;
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
