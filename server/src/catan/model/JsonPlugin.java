package catan.model;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonPlugin implements Plugin {
	
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

	public JsonPlugin(String name, boolean randomTokens, boolean randomHexes, boolean randomPorts) {
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

	@Override
	public Player getPlaleryByIndex(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	@Override
	public Player getPlayerByIndex(int playerIndex) {
		Player player = model.getAsJsonArray("players").get(playerIndex).getAsJsonObject();
		return player;
	}
	*/

}
