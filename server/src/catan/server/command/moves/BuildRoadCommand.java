package catan.server.command.moves;

import catan.model.EdgeLocation;
import catan.server.command.Command;

public class BuildRoadCommand implements Command {

	private String type;
	private int playerIndex;
	private EdgeLocation roadLocation;
	private boolean free;

	public BuildRoadCommand() {}

	@Override
	public Object execute(Object obj) {
		int gameID = (Integer) obj;
		// TODO Auto-generated method stub
		return null;
	}

}
