package catan.server.command.moves;

import catan.model.Model;
import catan.server.Games;
import catan.server.command.Command;
import catan.server.handler.HandlerUtils;

public class RollNumberCommand implements Command {
	private String type;
	private int playerIndex;
	private int number;
	
	@Override
	public Object execute(Object gameId) {
		System.out.println(type + playerIndex + number);
		Model model = Games.get().getGames().get(gameId);
		model.rollNumber(type, playerIndex, number);
		return null;
	}
}
