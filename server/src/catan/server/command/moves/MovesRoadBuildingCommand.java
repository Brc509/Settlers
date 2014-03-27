package catan.server.command.moves;

import catan.server.command.Command;

public class MovesRoadBuildingCommand implements Command {
	private String type;
	private int playerIndex;
	private Spot spot1;
	private Spot spot2;
	
	class Spot {
		int x;
		int y;
		String direction;
	}

	@Override
	public Object execute(Object obj) {
		// TODO Auto-generated method stub
		System.out.println(type + playerIndex + spot1.x + spot1.y + spot1.direction + spot2.x + spot2.y + spot2.direction);
		return null;
	}

}
