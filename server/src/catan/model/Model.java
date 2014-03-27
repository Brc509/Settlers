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
	private String name;

	public Model(String name, boolean randomTokens, boolean randomHexes, boolean randomPorts) {
		this.name = name;
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
		return name;
	}

	public void initializeMap(boolean b, boolean c, boolean d) {
		// TODO Auto-generated method stub
	}

	public boolean addPlayer(int orderNumber, int userID, String name, String color) {
		// TODO Auto-generated method stub
		return false;
	}

	// *** MOVES METHODS START HERE *** //

	public boolean buildCity(int playerIndex, int x, int y, String direction, boolean free) {
		// TODO Auto-generated method stub
		return false;
	}

	public JsonObject finishTurn(int playerIndex)
	{
		int nextPlayer = model.get("turnTracker").getAsJsonObject().get("currentTurn").getAsInt();
		
		System.out.println(model.get("players").getAsJsonArray().size());
		
		if(nextPlayer < (model.get("players").getAsJsonArray().size())-1)
		{
			nextPlayer++;
		}
		else
			nextPlayer = 0;
		
		System.out.println(nextPlayer);
		
		Gson g = new Gson();
		
		String name = model.getAsJsonArray("players").get(playerIndex).getAsJsonObject().get("name").getAsString();
		model.getAsJsonObject("turnTracker").addProperty("currentTurn", nextPlayer);
		JsonElement newLog = g.fromJson("{\"source\":\""+name+"\",\"message\":\""+name+"'s turn just ended\"}", JsonElement.class);
		model.getAsJsonObject("log").getAsJsonArray("lines").add(newLog);
		
		System.out.println(model.toString());
		
		return model;
	}
	
	public boolean maritimeTrade (String type, int playerIndex, int ratio, String inputResource, String outputResource) {
		return false;
	}
	
	public boolean monopoly (String type, String resource, int playerIndex) {
		return false;
	}
	
	public boolean monument (String type, int playerIndex) {
		return false;
	}
	
	public boolean offerTrade ( String type, int playerIndex, ResourceList offer, int receiver) {
		return false;
	}
	
	public boolean roadBuilding ( String type, int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		return false;
	}
	
	public boolean robPlayer ( String type, int playerIndex, int victimIndex, HexLocation location) {
		return false;
	}
	
	public boolean rollNumber ( String type,int playerIndex, int number) {
		return false;
	}
	
	public boolean yearOfPlenty(int playerIndex, String resource1, String resource2) {
		boolean success = false;
		if (playerIndex >= 0 && playerIndex < 4 && resourceNames.contains(resource1) && resourceNames.contains(resource2)) {
			JsonArray players = model.getAsJsonArray("players");
			JsonObject player = players.get(playerIndex).getAsJsonObject();
			JsonObject resources = player.getAsJsonObject("resources");
			int playerNum1 = resources.get(resource1).getAsInt();
			int playerNum2 = resources.get(resource2).getAsInt();
			JsonObject bank = model.getAsJsonObject("bank");
			int bankNum1 = bank.get(resource1).getAsInt();
			int bankNum2 = bank.get(resource2).getAsInt();
			if (bankNum1 > 0 && bankNum2 > 0) {
				resources.addProperty(resource1, playerNum1 + 1);
				resources.addProperty(resource2, playerNum2 + 1);
				bank.addProperty(resource1, bankNum1 - 1);
				bank.addProperty(resource2, bankNum2 - 1);
				success = true;
			}
		}
		return success;
	}

	// *** MOVES METHODS END HERE *** //

	public String getGameInfo(int id) {
		String s = "{ \"title\": \"" + name + "\", \"id\": " + id + ", \"players\": " + model.get("players") + "}";
		return s;
	}

	@Override
	public String toString() {
		return model.toString();
	}
}
