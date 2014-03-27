package catan.server.command.moves;

import catan.model.HexLocation;
import catan.model.Model;
import catan.server.Games;
import catan.server.command.Command;

public class MovesRobPlayerCommand implements Command {

	private String type;
	private int playerIndex;
	private int victimIndex;
	private HexLocation location;
	
	@Override
	public Object execute(Object gameId) {
		System.out.println(type + playerIndex + victimIndex + location.toString());
		Model model = Games.get().getGames().get(gameId);
		model.robPlayer(type, playerIndex, victimIndex, location);
		return null;
	}

}
