package catan.server.command.moves;

import java.util.ArrayList;

import catan.model.Hex;
import catan.model.Model;
import catan.model.Player;
import catan.model.TurnTracker;
import catan.model.Vertex;
import catan.server.Games;
import catan.server.command.Command;

public class RollNumberCommand implements Command {
	private String type;
	private int playerIndex;
	private int number;
	
	private Model model;
	
	@Override
	public Object execute(Object gameId) {
		model = Games.get().getGames().get(gameId);
		
		//find the players 
		ArrayList <Hex> hexList = model.rollNumber(number);
		for (Hex hex : hexList) {
			Vertex[] vertexArray = hex.getVertexes();
			String resourceType = hex.getType();
			for (Vertex vertex : vertexArray) {
				int playerIndex = vertex.getValue().getOwnerID();
				if (playerIndex != -1) {
					int amount = vertex.getValue().getWorth();
					giveResource(playerIndex, resourceType, amount);
				}
			}
		}
		
		// Advance the turntracker: status changes to playing
		TurnTracker tracker = model.getTurnTracker();
		tracker.setStatus("Playing");
		model.setTurnTracker(tracker);
		
		model.addLogEntry(playerIndex, " rolled a " + number);
		return false;
	}
	
	private void giveResource (int playerIndex, String resourceType, int amount) {
		Player player = model.getPlayerByIndex(playerIndex);
		player.getResources().incrementResource(resourceType.toLowerCase(), amount);
		model.setPlayerResources(playerIndex, player.getResources());
	}
}
