package catan.server.command.moves;

import catan.model.Model;
import catan.server.Games;
import catan.server.command.Command;

public class MovesMonopolyCommand implements Command{
	private String type;
	private String resource;
	private int playerIndex;

	@Override
	public Object execute(Object gameId) {
		System.out.println(type + resource + playerIndex);
		Model model = Games.get().getGames().get(gameId);
		model.monopoly(type, resource, playerIndex);
		return null;
	}
}
