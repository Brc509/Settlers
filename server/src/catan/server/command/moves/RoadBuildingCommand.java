package catan.server.command.moves;

import catan.model.EdgeLocation;
import catan.model.GameModel;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.Command;

public class RoadBuildingCommand implements Command {

	private String type;
	private int playerIndex;
	private EdgeLocation spot1;
	private EdgeLocation spot2;

	@Override
	public Object execute(GameModel game) {
		// TODO Auto-generated method stub
		System.out.println(type + playerIndex + spot1.toString() + spot2.toString());

		// Save the command
		Server.getPP().saveCommand(Games.get().getGameID(game), this);

		return null;
	}
}
