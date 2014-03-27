package catan.model;

import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Model {
	
	private static final String NEWGAMEFILE = "server/newGame.json"; 
	private static final String DEFAULTGAMEFILE = "server/defaultGame.json";
	private static final Gson	gson = new Gson();
	
	private JsonObject model;
	private String name;

	public Model (String name, boolean randomTokens, boolean randomHexes, boolean randomPorts) {
		this.setName(name);
		try {
			FileReader file = new FileReader(DEFAULTGAMEFILE);
			this.model = gson.fromJson(file, JsonObject.class);
			System.out.println(model);
			file.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public char[] getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void initializeMap(boolean b, boolean c, boolean d) {
		// TODO Auto-generated method stub
		
	}

	public void buildCity(int playerIndex, int x, int y, String direction,
			boolean free) {
		// TODO Auto-generated method stub
		
	}

	public boolean addPlayer(int orderNumber, int userID, String name,
			String color) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getGameInfo (int id) {
		String s = "{ \"title\": \"" + name + "\", \"id\": " +  id + ", \"players\": " + model.get("players") + "}"; 
		return s;
	}
	
	public String toString () {
		return model.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public JsonObject finishTurn(int playerIndex)
	{
		int nextPlayer = model.get("turnTracker").getAsJsonObject().get("currentTurn").getAsInt();
		if(nextPlayer < (model.get("players").getAsJsonArray().size())-1)
		{
			nextPlayer++;
		}
		else
			nextPlayer = 0;
		Gson g = new Gson();
		String name = model.getAsJsonArray("players").get(playerIndex).getAsString();
		model.getAsJsonObject("turnTracker").addProperty("currentTurn", nextPlayer);
		JsonElement newLog = g.toJsonTree("{'source':'"+name+",'message':"+name+"'s turn just ended}");
		model.getAsJsonObject("log").getAsJsonArray("lines").add(newLog);
		return model;
	}
	
}
