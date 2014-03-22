package catan.server.command;

import catan.server.Server;

public class UtilChangeLogLevelCommand implements Command {

	private final String logLevel;

	public UtilChangeLogLevelCommand(String logLevel) {
		this.logLevel = logLevel;
	}

	@Override
	public String execute() {
		if (Server.isDebugEnabled()) System.out.println("  Log level changed to \"" + logLevel + "\".");
		return "Success";
	}
}
