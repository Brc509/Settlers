package catan.server.command.moves;

import com.google.gson.JsonObject;

import catan.model.EdgeLocation;
import catan.model.Hex;
import catan.model.HexLocation;
import catan.model.GameModel;
import catan.model.Player;
import catan.model.ResourceList;
import catan.server.Games;
import catan.server.command.Command;

public class BuildRoadCommand implements Command {

	private String type;
	private int playerIndex;
	private EdgeLocation roadLocation;
	private boolean free;

	public BuildRoadCommand() {}

	@Override
	public Object execute(Object obj) {
		int gameID = (Integer) obj;
		// TODO Auto-generated method stub
		GameModel game = Games.get().getGames().get(gameID);
		JsonObject model = game.getModel();
		
		
		
		//decrement player resources
		//add bank resources
		//subtract number of roads player can build
		//change property 
				
		ResourceList playerRList = game.getPlayerResources(playerIndex);
		if (!free) {
			
			playerRList.setBrick(playerRList.getBrick() - 1);
			playerRList.setWood(playerRList.getWood() - 1);
			game.setPlayerResources(playerIndex, playerRList);
		
			ResourceList bankList = game.getBank();
			bankList.setBrick(bankList.getBrick() + 1);
			bankList.setWood(bankList.getWood() + 1);
			game.setBank(bankList);
		}
			
		int roads = model.getAsJsonArray("players").get(playerIndex).getAsJsonObject().get("roads").getAsInt();
		model.getAsJsonArray("players").get(playerIndex).getAsJsonObject().addProperty("roads", roads - 1);

		// Add the roads to the map
	
		EdgeLocation roadLocation2 = game.getEquivalentEdge(roadLocation);
		
		Hex hex1 = game.getHex(new HexLocation(roadLocation.getX(), roadLocation.getY()));
		Hex hex2 = game.getHex(new HexLocation(roadLocation2.getX(), roadLocation2.getY()));
		
		hex1.getEdges()[roadLocation.getDirectionIndex()].getValue().setOwnerID(playerIndex);
		hex2.getEdges()[roadLocation2.getDirectionIndex()].getValue().setOwnerID(playerIndex);
		
		game.setHex(hex1);
		game.setHex(hex2);
		
		//whoever has the most roads, give them longest road
		//give them two victory points		
		Player[] players = game.getPlayers();
	
		int minRoads12 = Math.min(players[0].getRoads(), players[1].getRoads());
		int minRoads34 = Math.min(players[2].getRoads(), players[3].getRoads());
		int minRoads = Math.min(minRoads12, minRoads34);
		
		for(int i = 0; i < players.length; i++){
			if(players[i].getRoads() == minRoads && 17 - minRoads > 5){
				model.addProperty("longestRoad", i);
			}
		}
		
		
		if((players[0].getRoads() == 14 && players[0].getCities() == 4)&&
				(players[1].getRoads() == 14 && players[1].getCities() == 4)&&
				(players[2].getRoads() == 14 && players[2].getCities() == 4)&&
				(players[3].getRoads() == 14 && players[3].getCities() == 4)){
			game.getTurnTracker().setStatus("SecondRound");
		}else if((players[0].getRoads() == 13 && players[0].getCities() == 3)&&
				(players[1].getRoads() == 13 && players[1].getCities() == 3)&&
				(players[2].getRoads() == 13 && players[2].getCities() == 3)&&
				(players[3].getRoads() == 13 && players[3].getCities() == 3)){
			game.getTurnTracker().setStatus("Rolling");

		}
	

		
		// LOG IT
		game.addLogEntry(playerIndex, " built a road.");
		
		return null;
	}
}
