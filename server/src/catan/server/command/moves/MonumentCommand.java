package catan.server.command.moves;

import catan.server.Server;
import catan.server.command.Command;

public class MonumentCommand implements Command {

	private String type;
	private int playerIndex;

	@Override
	public Object execute(Object gameId) {

		Server.println("Executing command: \"" + type + "\".");

		// TODO

		return null;
	}
}
