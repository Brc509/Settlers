package catan.server.command;

import catan.model.GameModel;
import catan.server.Server;

/**
 * When executed, changes the log level of the server.
 * 
 * @author Spencer Bench
 */
public class UtilChangeLogLevelCommand implements Command {

	private final String logLevel;

	public UtilChangeLogLevelCommand(String logLevel) {
		this.logLevel = logLevel;
	}

	@Override
	public String execute(GameModel game) {
		Server.println("  Log level changed to \"" + logLevel + "\".");
		return "Success";
	}
}
