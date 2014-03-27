package catan.server.command.moves;

import catan.model.Model;
import catan.server.Games;
import catan.server.command.Command;

/**
 * When executed, plays a Year of Plenty development card.
 * 
 * @author Spencer Bench
 */
public class MovesYearOfPlentyCommand implements Command {

	private String type;
	private int playerIndex;
	private String resource1;
	private String resource2;

	public MovesYearOfPlentyCommand() {}

	@Override
	public Boolean execute(Object obj) {
		Model game = Games.get().getGames().get(obj);
		return game.yearOfPlenty(playerIndex, resource1, resource2);
	}
}
