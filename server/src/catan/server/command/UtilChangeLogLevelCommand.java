package catan.server.command;

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
	public String execute(Object obj) {
		if (Server.isDebugEnabled()) System.out.println("  Log level changed to \"" + logLevel + "\".");
		return "Success";
	}
}
