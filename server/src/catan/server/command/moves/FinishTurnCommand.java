package catan.server.command.moves;

import catan.model.Model;
import catan.model.Player;
import catan.model.TurnTracker;
import catan.server.Games;
import catan.server.command.Command;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class FinishTurnCommand implements Command{
	
	private String type;
	private int playerIndex;

	public FinishTurnCommand(){}

	@Override
	public Object execute(Object obj) {
		// TODO Auto-generated method stub
		Model model = Games.get().getGames().get((Integer)obj);
		TurnTracker track = model.getTurnTracker();
		
		track.setCurrentTurn((track.getCurrentTurn()+1)%4);
		model.setTurnTracker(track);
		
		Player finishPlayer = model.getPlayerByIndex(playerIndex);
		String message = finishPlayer.getName()+"'s turn has ended.";
		
		model.addLogEntry(playerIndex, message);

		return null;
	}
}