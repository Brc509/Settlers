package catan.server.command.moves;

import catan.model.GameModel;
import catan.model.HexLocation;
import catan.server.command.Command;

public class RobPlayerCommand implements Command {

	private String type;
	private int playerIndex;
	private int victimIndex;
	private HexLocation location;

	@Override
	public Object execute(GameModel game) {
//		System.out.println(type + playerIndex + victimIndex + location.toString());
//		game.robPlayer(type, playerIndex, victimIndex, location);
//		
//		
//		boolean verdict = false;
//		if (playerIndex >= 0 && playerIndex < 4 && victimIndex >= 0 && victimIndex < 4) {
//			// This method is also used by soldier().
//			boolean movedRobberAndRobbed = moveRobberAndRob(game, playerIndex, victimIndex, location);
//			if (movedRobberAndRobbed) {
//				// Add the log entry
//				Player[] players = game.getPlayers();
//				String victimName = players[victimIndex].getName();
//				game.addLogEntry(playerIndex, " rolled a 7 and robbed " + victimName + ".");
//				verdict = true;
//			}
//		}
//		return verdict;
//	}
//	
//	private boolean moveRobberAndRob(GameModel game, int playerIndex, int victimIndex, HexLocation location) {
//		boolean verdict = false;
//
//		HexLocation robber = game.getRobberPosition();
//
//		// Make sure robber is moved from last location
//		int oldX = robber.getX();
//		int oldY = robber.getY();
//		if (!(oldX == Integer.parseInt(location.getX()) && oldY == Integer.parseInt(location.getY()))) {
//
//			// Make sure victim is valid for new location
//			int newX = Integer.parseInt(location.getX());
//			int newY = Integer.parseInt(location.getY());
//			Hex newHex = game.getHex(newX, newY);
//			Vertex[] vertexes = newHex.getVetexes();
//			boolean foundVictim = false;
//			for (Vertex v : vertexes) {
//				VertexValue vertexValue = v.getValue();
//				int ownerID = vertexValue.getOwnerID();
//				if (ownerID == victimIndex) {
//					foundVictim = true;
//					break;
//				}
//			}
//			if (foundVictim) {
//				// TODO Transfer a random resource from victim to player
//			}
//		}
//		return verdict;
		return null;
	}

}
