package catan.server.command.moves;

import catan.model.GameModel;
import catan.server.Server;
import catan.server.command.Command;

public class MonumentCommand implements Command {

	private String type;
	private int playerIndex;

	@Override
	public Object execute(GameModel game) {

		Server.println("Executing command: \"" + type + "\".");

		// TODO

		return null;
	}
}
