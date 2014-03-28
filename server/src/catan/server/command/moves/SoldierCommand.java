package catan.server.command.moves;

import catan.model.HexLocation;
import catan.model.Model;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.Command;

/**
 * When executed, plays a Knight development card.
 * 
 * @author Spencer Bench
 */
public class SoldierCommand implements Command {

	private String type;
	private int playerIndex;
	private int victimIndex;
	private HexLocation location;

	public SoldierCommand() {}

	@Override
	public Boolean execute(Object obj) {
		Server.println("  Attempting to execute command \"" + type + "\".");
		Model game = Games.get().getGames().get(obj);
		return game.soldier(playerIndex, victimIndex, location);
	}
}
