package catan.server.command.moves;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import catan.model.ClientModel;
import catan.model.Hex;
import catan.model.Model;
import catan.model.Player;
import catan.model.ResourceList;
import catan.server.Cookie;
import catan.server.Games;
import catan.server.command.Command;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class BuildSettlementCommand implements Command{
	
	private String type;
	private int playerIndex;
	public VertexLocation vertexLocation;
	private boolean free;

	public BuildSettlementCommand(){
	}
	
	class VertexLocation
	{
		int x;
		int y;
		String direction;
	}

	@Override
	public Object execute(Object obj){
		Model model = Games.get().getGames().get((Integer)obj);
		Player currPlayer = model.getPlayerByIndex(playerIndex);
		
		if(!free)
		{
			ResourceList playerList = currPlayer.getResources();
			
			int playerBrick = model.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject()
					.get("resources").getAsJsonObject().get("brick").getAsInt();
			model.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject()
					.get("resources").getAsJsonObject().addProperty("brick", playerBrick-1);
			int playerWood = model.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject()
					.get("resources").getAsJsonObject().get("wood").getAsInt();
			model.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject()
					.get("resources").getAsJsonObject().addProperty("wood", playerWood-1);
			int playerSheep = model.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject()
					.get("resources").getAsJsonObject().get("sheep").getAsInt();
			model.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject()
					.get("resources").getAsJsonObject().addProperty("sheep", playerSheep-1);
			int playerWheat = model.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject()
					.get("resources").getAsJsonObject().get("wheat").getAsInt();
			model.getModel().get("players").getAsJsonArray().get(playerIndex).getAsJsonObject()
					.get("resources").getAsJsonObject().addProperty("wheat", playerWheat-1);
			
			int brick = model.getModel().get("bank").getAsJsonObject().get("brick").getAsInt();
			model.getModel().get("bank").getAsJsonObject().addProperty("brick", brick+1);
			int wood = model.getModel().get("bank").getAsJsonObject().get("wood").getAsInt();
			model.getModel().get("bank").getAsJsonObject().addProperty("wood", wood+1);
			int sheep = model.getModel().get("bank").getAsJsonObject().get("sheep").getAsInt();
			model.getModel().get("bank").getAsJsonObject().addProperty("sheep", sheep+1);
			int wheat = model.getModel().get("bank").getAsJsonObject().get("wheat").getAsInt();
			model.getModel().get("bank").getAsJsonObject().addProperty("wheat", wheat+1);
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
		
		model.addLogEntry(playerIndex, "has built a settlement.");
		
		return null;
	}
	
	public void setHex(JsonObject hex, int corner, int player)
	{
		if(hex != null)
		{
			hex.getAsJsonObject().get("vertexes").getAsJsonArray().get(corner)
								.getAsJsonObject().get("value").getAsJsonObject()
								.addProperty("worth", 1);
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

