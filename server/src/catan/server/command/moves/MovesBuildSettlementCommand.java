package catan.server.command.moves;

import java.io.IOException;
import java.util.Map;

import catan.model.ClientModel;
import catan.model.Model;
import catan.server.Cookie;
import catan.server.Games;
import catan.server.command.Command;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class MovesBuildSettlementCommand implements Command{
	
	private String type;
	private int playerIndex;
	public VertexLocation vertexLocation;
	private boolean free;

	public MovesBuildSettlementCommand(){
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
		JsonObject updatedModel = model.buildSettlement(playerIndex);
		if(!free)
		{
			int brick = updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().get("brick").getAsInt();
			updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().addProperty("brick", brick-1);
			int wood = updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().get("wood").getAsInt();
			updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().addProperty("wood", wood-1);
			int sheep = updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().get("sheep").getAsInt();
			updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().addProperty("sheep", sheep-1);
			int wheat = updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().get("wheat").getAsInt();
			updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().addProperty("wheat", wheat-1);
			
			int brick2 = updatedModel.get("bank").getAsJsonObject().get("brick").getAsInt();
			updatedModel.get("bank").getAsJsonObject().addProperty("brick", brick2+1);
			
			int wood2 = updatedModel.get("bank").getAsJsonObject().get("wood").getAsInt();
			updatedModel.get("bank").getAsJsonObject().addProperty("wood", wood2+1);
			
			int sheep2 = updatedModel.get("bank").getAsJsonObject().get("sheep").getAsInt();
			updatedModel.get("bank").getAsJsonObject().addProperty("sheep", sheep2+1);
			
			int wheat2 = updatedModel.get("bank").getAsJsonObject().get("wheat").getAsInt();
			updatedModel.get("bank").getAsJsonObject().addProperty("wheat", wheat2+1);
		}
		
		JsonElement[] hexes = {updatedModel.get("map").getAsJsonObject().get("hexGrid").getAsJsonObject().get("hexes")
					.getAsJsonArray()};
		
		for(int i=0;i<hexes.length;i++)
		{
			if(hexes[i].getAsJsonObject().get("x").getAsInt()==vertexLocation.x)
			{
				if(hexes[i].getAsJsonObject().get("y").getAsInt()==vertexLocation.y)
				{
					int corner = -1;
					if(vertexLocation.direction=="NE")
						corner = 0;
					else if(vertexLocation.direction=="NW")
						corner = 1;
					else if(vertexLocation.direction=="E")
						corner = 2;
					else if(vertexLocation.direction=="SW")
						corner = 3;
					else if(vertexLocation.direction=="SE")
						corner = 4;
					else if(vertexLocation.direction=="W")
						corner = 5;
					
					hexes[i].getAsJsonObject().get("vertexes").getAsJsonArray().get(corner)
							.getAsJsonObject().get("value").getAsJsonObject()
							.addProperty("worth", 1);
					hexes[i].getAsJsonObject().get("vertexes").getAsJsonArray().get(corner)
							.getAsJsonObject().get("value").getAsJsonObject()
							.addProperty("ownerID", playerIndex);
				}
			}
		}
		
		return (Object)updatedModel;
	}

}
