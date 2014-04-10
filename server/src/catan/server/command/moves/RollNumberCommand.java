package catan.server.command.moves;

import java.util.ArrayList;

import catan.model.GameModel;
import catan.model.Hex;
import catan.model.Player;
import catan.model.TurnTracker;
import catan.model.Vertex;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.Command;

public class RollNumberCommand implements Command {

	private String type;
	private int playerIndex;
	private int number;

	@Override
	public Object execute(GameModel game) {
		//find the players 
		ArrayList<Hex> hexList = game.rollNumber(number);
		for (Hex hex : hexList) {
			Vertex[] vertexArray = hex.getVertexes();
			String resourceType = hex.getType();
			for (Vertex vertex : vertexArray) {
				int playerIndex = vertex.getValue().getOwnerID();
				if (playerIndex != -1) {
					int amount = vertex.getValue().getWorth();
					giveResource(game, playerIndex, resourceType, amount);
				}
			}
		}

		// Advance the turntracker: status changes to playing
		TurnTracker tracker = game.getTurnTracker();
		tracker.setStatus("Playing");
		game.setTurnTracker(tracker);

		game.addLogEntry(playerIndex, " rolled a " + number);

		// Save the command
		Server.getPP().saveCommand(Games.get().getGameID(game), this);

		return false;
	}

	private void giveResource(GameModel game, int playerIndex, String resourceType, int amount) {
		Player player = game.getPlayerByIndex(playerIndex);
		player.getResources().incrementResource(resourceType.toLowerCase(), amount);
		game.setPlayerResources(playerIndex, player.getResources());
	}
}
