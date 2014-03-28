package catan.server.command.moves;

import com.google.gson.JsonObject;

import catan.model.Model;
import catan.server.Games;
import catan.server.command.Command;
import catan.server.command.moves.MovesBuildSettlementCommand.VertexLocation;


public class MovesBuildCityCommand implements Command {

	private String type;
	private int playerIndex;
	public VertexLocation vertexLocation;
	private boolean free;
	
	public MovesBuildCityCommand() {}
	
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
			int ore = updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().get("ore").getAsInt();
			updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().addProperty("ore", ore-3);
			int wheat = updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().get("wheat").getAsInt();
			updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
						.get("resources").getAsJsonObject().addProperty("wheat", wheat-2);
			
			int ore2 = updatedModel.get("bank").getAsJsonObject().get("ore").getAsInt();
			updatedModel.get("bank").getAsJsonObject().addProperty("ore", ore2+3);
			
			int wheat2 = updatedModel.get("bank").getAsJsonObject().get("wheat").getAsInt();
			updatedModel.get("bank").getAsJsonObject().addProperty("wheat", wheat2+2);
		}
		
		int corner = -1;
		String direction = vertexLocation.direction;
		
		if(direction.equals("NE"))
			corner = 0;
		if(direction.equals("NW"))
			corner = 1;
		if(direction.equals("E"))
			corner = 2;
		if(direction.equals("SW"))
			corner = 3;
		if(direction.equals("SE"))
			corner = 4;
		if(direction.equals("W"))
			corner = 5;
		
		System.out.println(playerIndex+" "+vertexLocation.x+" "+vertexLocation.y+" "+corner
				+" "+vertexLocation.direction+" "+direction+" NE");
		
		updatedModel.get("map").getAsJsonObject().get("hexGrid").getAsJsonObject().get("hexes")
					.getAsJsonArray().get(vertexLocation.y).getAsJsonArray().get(vertexLocation.x)
					.getAsJsonObject().get("vertexes").getAsJsonArray().get(corner).getAsJsonObject()
					.get("value").getAsJsonObject().addProperty("worth", 2);
		
		updatedModel.get("map").getAsJsonObject().get("hexGrid").getAsJsonObject().get("hexes")
					.getAsJsonArray().get(vertexLocation.y).getAsJsonArray().get(vertexLocation.x)
					.getAsJsonObject().get("vertexes").getAsJsonArray().get(corner).getAsJsonObject()
					.get("value").getAsJsonObject().addProperty("ownerID", playerIndex);
		
		return (Object)updatedModel;
	}

}
