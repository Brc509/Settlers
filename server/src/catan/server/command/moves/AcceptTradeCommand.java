package catan.server.command.moves;

import catan.model.GameModel;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.Command;

public class AcceptTradeCommand implements Command {

	private String type;
	private int playerIndex;
	private boolean willAccept;

	public AcceptTradeCommand() {}

	@Override
	public Object execute(GameModel game) {

		// Save the command
		Server.getPP().saveCommand(Games.get().getGameID(game), this);

		return null;
	}

}
