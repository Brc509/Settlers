package catan.server.command.moves;

import catan.model.GameModel;
import catan.model.Player;
import catan.model.ResourceList;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.Command;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BuildCityCommand implements Command {

	private String type;
	private int playerIndex;
	private VertexLocation vertexLocation;
	private boolean free;

	public BuildCityCommand() {}

	private class VertexLocation {

		int x;
		int y;
		int direction;
	}

	@Override
	public Object execute(GameModel game) {
		Player currPlayer = game.getPlayerByIndex(playerIndex);

		if (!free) {
			ResourceList playerList = currPlayer.getResources();
			System.out.println(playerList);

			int playerOre = game.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject().get("resources").getAsJsonObject().get("ore").getAsInt();
			game.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject().get("resources").getAsJsonObject().addProperty("ore", playerOre - 3);
			int playerWheat = game.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject().get("resources").getAsJsonObject().get("wheat").getAsInt();
			game.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject().get("resources").getAsJsonObject().addProperty("wheat", playerWheat - 2);

			System.out.println(playerList);

			int ore = game.getModel().get("bank").getAsJsonObject().get("ore").getAsInt();
			game.getModel().get("bank").getAsJsonObject().addProperty("ore", ore + 3);
			int wheat = game.getModel().get("bank").getAsJsonObject().get("wheat").getAsInt();
			game.getModel().get("bank").getAsJsonObject().addProperty("wheat", wheat + 2);
		}

		JsonArray hexes = game.getModel().get("map").getAsJsonObject().get("hexGrid").getAsJsonObject().get("hexes").getAsJsonArray();

		int direction = vertexLocation.direction;
		//int corner = getCornerValue(direction);

		if (direction==2) {
			// (x, y-1, "SE")(x+1, y-1, "W") too
			setHex(getHex(vertexLocation.x, vertexLocation.y, "NE", hexes), direction, playerIndex);
			setHex(getHex(vertexLocation.x, vertexLocation.y - 1, "SE", hexes), getCornerValue("SE"), playerIndex);
			setHex(getHex(vertexLocation.x + 1, vertexLocation.y - 1, "W", hexes), getCornerValue("W"), playerIndex);
		}
		if (direction==1) {
			// (x, y-1, "SW")(x-1, y, "E") too
			setHex(getHex(vertexLocation.x, vertexLocation.y, "NW", hexes), direction, playerIndex);
			setHex(getHex(vertexLocation.x, vertexLocation.y - 1, "SW", hexes), getCornerValue("SW"), playerIndex);
			setHex(getHex(vertexLocation.x - 1, vertexLocation.y, "E", hexes), getCornerValue("E"), playerIndex);
		}
		if (direction==3) {
			// (x+1, y-1, "SW")(x+1, y, "NW") too
			setHex(getHex(vertexLocation.x, vertexLocation.y, "E", hexes), direction, playerIndex);
			setHex(getHex(vertexLocation.x + 1, vertexLocation.y - 1, "SW", hexes), getCornerValue("SW"), playerIndex);
			setHex(getHex(vertexLocation.x + 1, vertexLocation.y, "NW", hexes), getCornerValue("NW"), playerIndex);
		}
		if (direction==5) {
			// (x, y+1, "NW")(x-1, y+1, "E") too
			setHex(getHex(vertexLocation.x, vertexLocation.y, "SW", hexes), direction, playerIndex);
			setHex(getHex(vertexLocation.x, vertexLocation.y + 1, "NW", hexes), getCornerValue("NW"), playerIndex);
			setHex(getHex(vertexLocation.x - 1, vertexLocation.y + 1, "E", hexes), getCornerValue("E"), playerIndex);
		}
		if (direction==4) {
			// (x, y+1, "NE")(x+1, y, "W") too
			setHex(getHex(vertexLocation.x, vertexLocation.y, "SE", hexes), direction, playerIndex);
			setHex(getHex(vertexLocation.x, vertexLocation.y + 1, "NE", hexes), getCornerValue("NE"), playerIndex);
			setHex(getHex(vertexLocation.x + 1, vertexLocation.y, "W", hexes), getCornerValue("W"), playerIndex);
		}
		if (direction==0) {
			// (x-1, y, "SE")(x-1, y+1, "NE") too
			setHex(getHex(vertexLocation.x, vertexLocation.y, "W", hexes), direction, playerIndex);
			setHex(getHex(vertexLocation.x - 1, vertexLocation.y, "SE", hexes), getCornerValue("SE"), playerIndex);
			setHex(getHex(vertexLocation.x - 1, vertexLocation.y + 1, "NE", hexes), getCornerValue("NE"), playerIndex);
		}

		game.addLogEntry(playerIndex, " has built a city.");
		int numCities = currPlayer.getCities() - 1;

		game.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject().addProperty("cities", numCities);

		// Save the command
		Server.getPP().saveCommand(Games.get().getGameID(game), this);

		return null;
	}

	private void setHex(JsonObject hex, int corner, int player) {
		if (hex != null) {
			hex.getAsJsonObject().get("vertexes").getAsJsonArray().get(corner).getAsJsonObject().get("value").getAsJsonObject().addProperty("worth", 2);
			hex.getAsJsonObject().get("vertexes").getAsJsonArray().get(corner).getAsJsonObject().get("value").getAsJsonObject().addProperty("ownerID", player);
		}
	}

	private JsonObject getHex(int x, int y, String direction, JsonArray hexes) {
		for (int i = 0; i < hexes.size(); i++) {
			JsonArray setOfHexes = hexes.get(i).getAsJsonArray();
			for (int j = 0; j < setOfHexes.size(); j++) {
				JsonObject hex = setOfHexes.get(j).getAsJsonObject();
				if (hex.getAsJsonObject().get("location").getAsJsonObject().get("x").getAsInt() == x) if (hex.getAsJsonObject().get("location").getAsJsonObject().get("y").getAsInt() == y) return hex;
			}
		}

		return null;
	}

	private int getCornerValue(String direction) {
		int corner1 = -1;
		if (direction.equals("W")) {
			corner1 = 0;
		}
		if (direction.equals("NW")) {
			corner1 = 1;
		}
		if (direction.equals("NE")) {
			corner1 = 2;
		}
		if (direction.equals("E")) {
			corner1 = 3;
		}
		if (direction.equals("SE")) {
			corner1 = 4;
		}
		if (direction.equals("SW")) {
			corner1 = 5;
		}
		return corner1;
	}

}
