package catan.server.command.moves;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import catan.model.Model;
import catan.server.Games;
import catan.server.command.Command;
import catan.server.handler.HandlerUtils;

public class RollNumberCommand implements Command {
	private String type;
	private int playerIndex;
	private int number;
	
	@Override
	public Object execute(Object gameId) {
//		System.out.println(type + playerIndex + number);
//		Model model = Games.get().getGames().get(gameId);
//		
//		JsonArray numbers = model.getNumbers(number);
//		JsonArray hexes = model.getHexes();
//		
//		for (HexLocation hexLocation : numbers) {
//			JsonObject location = gson.fromJson(hexLocation, JsonObject.class);
//			int x =  location.get("x").getAsInt();
//			int y =  location.get("y").getAsInt();
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
//		}
		
		//TODO advance the turntracker: status changes to discarding, robbing, or playing
		//TODO add this command to the log
		return false;
	}
}
