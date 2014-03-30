package catan.server.command.moves;

import com.google.gson.JsonObject;

import catan.model.EdgeLocation;
import catan.model.Model;
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
		Model game = Games.get().getGames().get(gameID);
		JsonObject model = game.getModel();
		
		//decrement player resources
		//add bank resources
		//subtract number of roads player can build
		//change property 
				
		ResourceList playerRList = game.getPlayerResources(playerIndex);
		playerRList.setBrick(playerRList.getBrick() - 1);
		playerRList.setWood(playerRList.getWood() - 1);
		game.setPlayerResources(playerIndex, playerRList);
		
		ResourceList bankList = game.getBank();
		bankList.setBrick(bankList.getBrick() + 1);
		bankList.setWood(bankList.getWood() + 1);
		game.setBank(bankList);
			
		int roads = model.getAsJsonArray("players").get(playerIndex).getAsJsonObject().get("roads").getAsInt();
		model.getAsJsonArray("players").get(playerIndex).getAsJsonObject().addProperty("roads", roads - 1);

		//whoever has the most roads, give them longest road
		//give them two victory points
	
		Player[] players = game.getPlayers();
	
		int mostRoads12 = Math.max(players[0].getRoads(), players[1].getRoads());
		int mostRoads34 = Math.max(players[2].getRoads(), players[3].getRoads());
		int mostRoads = Math.max(mostRoads12, mostRoads34);
		
		for(int i = 0; i < players.length; i++){
			if(players[i].getRoads() == mostRoads && mostRoads > 5){
				model.addProperty("longestRoad", mostRoads);
			}
		}
		
		return null;
	}

}

//Model model = Games.get().getGames().get((Integer)obj);
//JsonObject updatedModel = model.buildSettlement(playerIndex);
//if(!free)
//{
//	int brick = updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
//				.get("resources").getAsJsonObject().get("brick").getAsInt();
//	updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
//				.get("resources").getAsJsonObject().addProperty("brick", brick-1);
//	int wood = updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
//				.get("resources").getAsJsonObject().get("wood").getAsInt();
//	updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
//				.get("resources").getAsJsonObject().addProperty("wood", wood-1);
//	int sheep = updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
//				.get("resources").getAsJsonObject().get("sheep").getAsInt();
//	updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
//				.get("resources").getAsJsonObject().addProperty("sheep", sheep-1);
//	int wheat = updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
//				.get("resources").getAsJsonObject().get("wheat").getAsInt();
//	updatedModel.getAsJsonArray("players").get(playerIndex).getAsJsonObject()
//				.get("resources").getAsJsonObject().addProperty("wheat", wheat-1);
//	
//	int brick2 = updatedModel.get("bank").getAsJsonObject().get("brick").getAsInt();
//	updatedModel.get("bank").getAsJsonObject().addProperty("brick", brick2+1);
//	
//	int wood2 = updatedModel.get("bank").getAsJsonObject().get("wood").getAsInt();
//	updatedModel.get("bank").getAsJsonObject().addProperty("wood", wood2+1);
//	
//	int sheep2 = updatedModel.get("bank").getAsJsonObject().get("sheep").getAsInt();
//	updatedModel.get("bank").getAsJsonObject().addProperty("sheep", sheep2+1);
//	
//	int wheat2 = updatedModel.get("bank").getAsJsonObject().get("wheat").getAsInt();
//	updatedModel.get("bank").getAsJsonObject().addProperty("wheat", wheat2+1);
//}
//
//int corner = -1;
//String direction = vertexLocation.direction;
//
//if(direction.equals("NE"))
//	corner = 0;
//if(direction.equals("NW"))
//	corner = 1;
//if(direction.equals("E"))
//	corner = 2;
//if(direction.equals("SW"))
//	corner = 3;
//if(direction.equals("SE"))
//	corner = 4;
//if(direction.equals("W"))
//	corner = 5;
//
//System.out.println(playerIndex+" "+vertexLocation.x+" "+vertexLocation.y+" "+corner
//		+" "+vertexLocation.direction+" "+direction+" NE");
//
//updatedModel.get("map").getAsJsonObject().get("hexGrid").getAsJsonObject().get("hexes")
//			.getAsJsonArray().get(vertexLocation.y+3).getAsJsonArray().get(vertexLocation.x+3)
//			.getAsJsonObject().get("vertexes").getAsJsonArray().get(corner).getAsJsonObject()
//			.get("value").getAsJsonObject().addProperty("worth", 1);
//
//updatedModel.get("map").getAsJsonObject().get("hexGrid").getAsJsonObject().get("hexes")
//			.getAsJsonArray().get(vertexLocation.y+3).getAsJsonArray().get(vertexLocation.x+3)
//			.getAsJsonObject().get("vertexes").getAsJsonArray().get(corner).getAsJsonObject()
//			.get("value").getAsJsonObject().addProperty("ownerID", playerIndex);
//
//return (Object)updatedModel;