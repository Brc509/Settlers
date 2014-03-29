package catan.server.command.moves;

import catan.model.Model;
import catan.model.TurnTracker;
import catan.server.Games;
import catan.server.command.Command;

public class RollNumberCommand implements Command {
	private String type;
	private int playerIndex;
	private int number;
	
	@Override
	public Object execute(Object gameId) {
		Model model = Games.get().getGames().get(gameId);
		
		//find the players 
		model.rollNumber(number);
		
		//TODO give them resources
		
		//advance the turntracker: status changes to playing
		TurnTracker tracker = model.getTurnTracker();
		tracker.setStatus("Playing");
		model.setTurnTracker(tracker);
		
		model.addLogEntry(playerIndex, " rolled a " + number);
		return false;
	}
}
