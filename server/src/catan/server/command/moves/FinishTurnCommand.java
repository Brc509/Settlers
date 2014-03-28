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
		// TODO Auto-generated method stub
		Model model = Games.get().getGames().get((Integer)obj);
		JsonObject updatedModel = model.finishTurn(playerIndex);
		return (Object)updatedModel;
	}

}