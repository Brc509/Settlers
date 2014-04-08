package catan.server.command.moves;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import catan.model.GameModel;
import catan.model.Player;
import catan.model.ResourceList;
import catan.server.Games;
import catan.server.command.Command;
import catan.server.command.moves.BuildSettlementCommand.VertexLocation;


public class BuildCityCommand implements Command {

	private String type;
	private int playerIndex;
	public VertexLocation vertexLocation;
	private boolean free;
	
	public BuildCityCommand() {}
	
	class VertexLocation
	{
		int x;
		int y;
		String direction;
	}
	
	@Override
	public Object execute(Object obj){
		GameModel model = Games.get().getGames().get((Integer)obj);
		Player currPlayer = model.getPlayerByIndex(playerIndex);
		
		if(!free)
		{
			ResourceList playerList = currPlayer.getResources();
			System.out.println(playerList);
			
			int playerOre = model.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().get("ore").getAsInt();
			model.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().addProperty("ore", playerOre-3);
			int playerWheat = model.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().get("wheat").getAsInt();
			model.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().addProperty("wheat", playerWheat-2);
			
			System.out.println(playerList);
			
			int ore = model.getModel().get("bank").getAsJsonObject().get("ore").getAsInt();
			model.getModel().get("bank").getAsJsonObject().addProperty("ore", ore+3);
			int wheat = model.getModel().get("bank").getAsJsonObject().get("wheat").getAsInt();
			model.getModel().get("bank").getAsJsonObject().addProperty("wheat", wheat+2);
		}
		
		JsonArray hexes = model.getModel().get("map").getAsJsonObject().get("hexGrid")
				.getAsJsonObject().get("hexes").getAsJsonArray();

		String direction = vertexLocation.direction;
		int corner = getCornerValue(direction);
		
		if(direction.equals("NE"))
		{
		// (x, y-1, "SE")(x+1, y-1, "W") too
		setHex(getHex(vertexLocation.x, vertexLocation.y,direction,hexes), corner, playerIndex);
		setHex(getHex(vertexLocation.x, vertexLocation.y-1,"SE",hexes), getCornerValue("SE"), playerIndex);
		setHex(getHex(vertexLocation.x+1, vertexLocation.y-1,"W",hexes), getCornerValue("W"), playerIndex);
		}
		if(direction.equals("NW"))
		{
		// (x, y-1, "SW")(x-1, y, "E") too
		setHex(getHex(vertexLocation.x, vertexLocation.y,direction,hexes), corner, playerIndex);
		setHex(getHex(vertexLocation.x, vertexLocation.y-1,"SW",hexes), getCornerValue("SW"), playerIndex);
		setHex(getHex(vertexLocation.x-1, vertexLocation.y,"E",hexes), getCornerValue("E"), playerIndex);
		}
		if(direction.equals("E"))
		{
		// (x+1, y-1, "SW")(x+1, y, "NW") too
		setHex(getHex(vertexLocation.x, vertexLocation.y,direction,hexes), corner, playerIndex);
		setHex(getHex(vertexLocation.x+1, vertexLocation.y-1,"SW",hexes), getCornerValue("SW"), playerIndex);
		setHex(getHex(vertexLocation.x+1, vertexLocation.y,"NW",hexes), getCornerValue("NW"), playerIndex);
		}
		if(direction.equals("SW"))
		{
		// (x, y+1, "NW")(x-1, y+1, "E") too
		setHex(getHex(vertexLocation.x, vertexLocation.y,direction,hexes), corner, playerIndex);
		setHex(getHex(vertexLocation.x, vertexLocation.y+1,"NW",hexes), getCornerValue("NW"), playerIndex);
		setHex(getHex(vertexLocation.x-1, vertexLocation.y+1,"E",hexes), getCornerValue("E"), playerIndex);
		}
		if(direction.equals("SE"))
		{
		// (x, y+1, "NE")(x+1, y, "W") too
		setHex(getHex(vertexLocation.x, vertexLocation.y,direction,hexes), corner, playerIndex);
		setHex(getHex(vertexLocation.x, vertexLocation.y+1,"NE",hexes), getCornerValue("NE"), playerIndex);
		setHex(getHex(vertexLocation.x+1, vertexLocation.y,"W",hexes), getCornerValue("W"), playerIndex);
		}
		if(direction.equals("W"))
		{
		// (x-1, y, "SE")(x-1, y+1, "NE") too
		setHex(getHex(vertexLocation.x, vertexLocation.y,direction,hexes), corner, playerIndex);
		setHex(getHex(vertexLocation.x-1, vertexLocation.y,"SE",hexes), getCornerValue("SE"), playerIndex);
		setHex(getHex(vertexLocation.x-1, vertexLocation.y+1,"NE",hexes), getCornerValue("NE"), playerIndex);
		}
		
		model.addLogEntry(playerIndex, "has built a city.");
		return null;
		}
		
		public void setHex(JsonObject hex, int corner, int player)
		{
			if(hex != null)
			{
				hex.getAsJsonObject().get("vertexes").getAsJsonArray().get(corner)
								.getAsJsonObject().get("value").getAsJsonObject()
								.addProperty("worth", 2);
				hex.getAsJsonObject().get("vertexes").getAsJsonArray().get(corner)
								.getAsJsonObject().get("value").getAsJsonObject()
								.addProperty("ownerID", player);
			}
		}
		
		public JsonObject getHex(int x, int y, String direction, JsonArray hexes)
		{
			for(int i=0;i<hexes.size();i++)
			{
				JsonArray setOfHexes = hexes.get(i).getAsJsonArray();
				for(int j=0;j<setOfHexes.size();j++)
				{
					JsonObject hex = setOfHexes.get(j).getAsJsonObject();
					if(hex.getAsJsonObject().get("location").getAsJsonObject().get("x").getAsInt()==x)
						if(hex.getAsJsonObject().get("location").getAsJsonObject().get("y").getAsInt()==y)
							return hex;
				}
			}
			
			return null;
		}
		
		public int getCornerValue(String direction)
		{
			int corner1 = -1;
			if(direction.equals("W")){corner1 = 0;}
			if(direction.equals("NW")){corner1 = 1;}
			if(direction.equals("NE")){corner1 = 2;}
			if(direction.equals("E")){corner1 = 3;}
			if(direction.equals("SE")){corner1 = 4;}
			if(direction.equals("SW")){corner1 = 5;}
			return corner1;
		}

}
