//package catan.server.command.moves;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//
//import catan.model.DevCardList;
//import catan.model.HexLocation;
//import catan.model.Model;
//import catan.model.Vertex;
//import catan.model.VertexValue;
//import catan.server.Games;
//import catan.server.Server;
//import catan.server.command.Command;
//
///**
// * When executed, plays a Knight development card.
// * 
// * @author Spencer Bench
// */
//public class SoldierCommand implements Command {
//
//	private String type;
//	private int playerIndex;
//	private int victimIndex;
//	private HexLocation location;
//
//	public SoldierCommand() {}
//
//	@Override
//	public Boolean execute(Object obj) {
//		Server.println("  Attempting to execute command \"" + type + "\".");
//		Model game = Games.get().getGames().get(obj);
//		return game.soldier(playerIndex, victimIndex, location);
//		
//		boolean verdict = false;
//		if (playerIndex >= 0 && playerIndex < 4 && victimIndex >= 0 && victimIndex < 4) {
//
//			Player[] players = model.getPlayers();
//			Player player = players[playerIndex];
//
//			// Make sure player has a Soldier card to play
//			DevCardList oldDevCards = player.getOldDevCards();
//			int oldPlayerSoldier = oldDevCards.getSoldier();
//			if (oldPlayerSoldier > 0) {
//
//				// Move the robber and rob the victim
//				boolean movedRobberAndRobbed = moveRobberAndRob(playerIndex, victimIndex, location);
//				if (movedRobberAndRobbed) {
//
//					// Move the Soldier card back to the deck
//					oldDevCards.setSoldier(oldPlayerSoldier - 1);
////					Deck deck = model.getAsJsonObject("deck");
////					int oldDeckSoldier = deck.get("soldier").getAsInt();
//					int oldDeckSoldier = model.getSoldier();
////					deck.addProperty("soldier", oldDeckSoldier + 1);
//
//					model.setSoldier(oldDeckSoldier + 1);
//
//					// Add the log entry
//					String victimName = players.get(victimIndex).getAsJsonObject().get("name").getAsString();
//					model.addLogEntry(playerIndex, " played a Soldier card and robbed " + victimName + ".");
//
//					verdict = true;
//				}
//			}
//		}
//		return verdict;
//	}
//	
//	private boolean moveRobberAndRob(int playerIndex, int victimIndex, HexLocation location) {
//	boolean verdict = false;
//
//	HexLocation robber = model.getRobberPosition();
//
//	// Make sure robber is moved from last location
//	int oldX = robber.getX();
//	int oldY = robber.getY();
//	if (!(oldX == Integer.parseInt(location.getX()) && oldY == Integer.parseInt(location.getY()))) {
//
//		// Make sure victim is valid for new location
//		int newX = Integer.parseInt(location.getX());
//		int newY = Integer.parseInt(location.getY());
//		Hex newHex = model.getHex(newX, newY);
//		Vertex[] vertexes = newHex.getVetexes();
//		boolean foundVictim = false;
//		for (Vertex v : vertexes) {
//			VertexValue vertexValue = v.getValue();
//			int ownerID = vertexValue.getOwnerID();
//			if (ownerID == victimIndex) {
//				foundVictim = true;
//				break;
//			}
//		}
//		if (foundVictim) {
//			// TODO Transfer a random resource from victim to player
//		}
//	}
//	return verdict;
//}
//
//}
