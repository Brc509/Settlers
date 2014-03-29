package catan.server.command.moves;

import catan.model.Model;
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
//		// TODO Auto-generated method stub
//		Model model = Games.get().getGames().get((Integer)obj);
//		JsonObject updatedModel = model.finishTurn(playerIndex);
//		return (Object)updatedModel;
//		
//		TurnTracker turnTracker = model.getTurnTracker();
//		int nextPlayer = turnTracker.getCurrentTurn();
//
////		System.out.println(model.get("players").getAsJsonArray().size());
//
//		if (nextPlayer < (model.getPlayers() - 1) {
//			nextPlayer++;
//		} else { 
//			nextPlayer = 0; 
//		}
//
//		System.out.println(nextPlayer);
//
//		turnTracker.addProperty("currentTurn", nextPlayer);
//		model.addLogEntry(playerIndex, "'s turn just ended");
//		return model;
//	}
		return null;
	}
}
