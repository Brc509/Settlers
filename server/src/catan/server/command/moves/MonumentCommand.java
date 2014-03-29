package catan.server.command.moves;

import catan.model.Model;
import catan.server.Games;
import catan.server.command.Command;

public class MonumentCommand implements Command {
	private String type;
	private int playerIndex;
	
	@Override
	public Object execute(Object gameId) {
		// TODO Auto-generated method stub
		System.out.println(type + playerIndex);
		Model model = Games.get().getGames().get(gameId);
		return null;
	}

}
