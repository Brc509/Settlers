package catan.server.command.moves;

import com.google.gson.JsonObject;

import catan.model.Model;
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
		Model model = Games.get().getGames().get((Integer)obj);
		Player currPlayer = model.getPlayerByIndex(playerIndex);
		
		if(!free)
		{
			ResourceList playerList = currPlayer.getResources();
			
			playerList.setOre(playerList.getOre()+3);
			playerList.setWheat(playerList.getWheat()+2);
			
			int ore = model.getModel().get("bank").getAsJsonObject().get("ore").getAsInt();
			model.getModel().get("bank").getAsJsonObject().addProperty("ore", ore+3);
			int wheat = model.getModel().get("bank").getAsJsonObject().get("wheat").getAsInt();
			model.getModel().get("bank").getAsJsonObject().addProperty("wheat", wheat+2);
		}
		
		return null;
	}

}
