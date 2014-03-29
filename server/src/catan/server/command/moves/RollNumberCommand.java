package catan.server.command.moves;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import catan.model.Model;
import catan.model.TurnTracker;
import catan.server.Games;
import catan.server.command.Command;
import catan.server.handler.HandlerUtils;

public class RollNumberCommand implements Command {
	private String type;
	private int playerIndex;
	private int number;
	
	@Override
	public Object execute(Object gameId) {
//		System.out.println(type + playerIndex + number);
		Model model = Games.get().getGames().get(gameId);
		
		//find the players 
		model.rollNumber(number);
		
		//TODO give them resources
		
		//advance the turntracker: status changes to playing discarding, robbing, or 
		TurnTracker tracker = model.getTurnTracker();
		tracker.setStatus("playing");
		model.setTurnTracker(tracker);
		
		model.addLogEntry(playerIndex, " rolled a " + number);
		return false;
	}
}
