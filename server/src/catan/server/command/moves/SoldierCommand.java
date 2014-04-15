package catan.server.command.moves;

import catan.model.GameModel;
import catan.model.Hex;
import catan.model.HexLocation;
import catan.model.Player;
import catan.model.Vertex;
import catan.model.VertexValue;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.Command;

/**
 * When executed, plays a Knight development card.
 * 
 * @author Spencer Bench
 */
public class SoldierCommand implements Command {

	private String type;
	private int playerIndex;
	private int victimIndex;
	private HexLocation location;
	private String robResource = null;

	public SoldierCommand() {}

	@Override
	public Boolean execute(GameModel game) {
		Server.println("  Attempting to execute command \"" + type + "\".");

		boolean verdict = false;
		if (playerIndex >= 0 && playerIndex < 4 && victimIndex >= 0 && victimIndex < 4) {

			Player[] players = game.getPlayers();
			Player player = players[playerIndex];

			// Make sure player has a Soldier card to play
			int oldPlayerSoldier = player.getOldDevCards().getSoldier();
			if (oldPlayerSoldier > 0) {

				// Move the robber and rob the victim
				boolean movedRobberAndRobbed = moveRobberAndRob(game, playerIndex, victimIndex, location);
				if (movedRobberAndRobbed) {

					/* I don't think the card is supposed to be returned to the deck
						// Move the Soldier card back to the deck
						oldDevCards.setSoldier(oldPlayerSoldier - 1);
						DevCardList deck = game.getDeck();
						deck.setSoldier(deck.getSoldier() + 1);
					*/

					// Add the log entry
					String victimName = players[victimIndex].getName();
					game.addLogEntry(playerIndex, " played a Soldier card and robbed " + victimName + ".");

					verdict = true;
				}
			}
		}
		// TODO update the game with the new values

		// Save the command
		Server.getPP().saveCommand(Games.get().getGameID(game), this);

		return verdict;
	}

	private boolean moveRobberAndRob(GameModel game, int playerIndex, int victimIndex, HexLocation location) {
		boolean verdict = false;

		HexLocation robber = game.getRobberPosition();

		// Make sure robber is moved from last location
		String oldX = robber.getX();
		String oldY = robber.getY();

		// If the new robber location doesn't exactly match the old robber location (it shouldn't)
		if (!(oldX.equals(location.getX()) && oldY == location.getY())) {

			// Make sure victim is valid for new location
			String newX = location.getX();
			String newY = location.getY();
			Hex newHex = game.getHex(new HexLocation(newX, newY));
			Vertex[] vertexes = newHex.getVertexes();
			boolean foundVictim = false;
			for (Vertex v : vertexes) {
				VertexValue vertexValue = v.getValue();
				int ownerID = vertexValue.getOwnerID();
				if (ownerID == victimIndex) {
					foundVictim = true;
					break;
				}
			}
			if (foundVictim) {
				// Take a random resource from the victim and give it to the player
				Player[] players = game.getPlayers();
				Player player = players[playerIndex];
				Player victim = players[victimIndex];

				if (robResource == null) {
					robResource = victim.getResources().getRandomResource();
				}
				player.getResources().incrementResource(robResource);
				victim.getResources().decrementResource(robResource);

				game.setPlayerResources(playerIndex, player.getResources());
				game.setPlayerResources(victimIndex, player.getResources());
			}
		}
		return verdict;
	}
}
