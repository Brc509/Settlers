package catan.server.command.moves;

import catan.server.command.Command;

public class MovesRobPlayerCommand implements Command {

	private String type;
	private int playerIndex;
	private int victimIndex;
	private Location location;
	
	class Location {
		String x;
		String y;
	}
	
	@Override
	public Object execute() {
		System.out.println(type + playerIndex + victimIndex + location.x + location.y);
		return null;
	}

}
