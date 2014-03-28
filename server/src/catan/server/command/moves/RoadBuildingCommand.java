package catan.server.command.moves;

import catan.model.EdgeLocation;
import catan.model.Model;
import catan.server.Games;
import catan.server.command.Command;

public class RoadBuildingCommand implements Command {
	private String type;
	private int playerIndex;
	private EdgeLocation spot1;
	private EdgeLocation spot2;

	@Override
	public Object execute(Object gameId) {
		// TODO Auto-generated method stub
		System.out.println(type + playerIndex + spot1.toString() + spot2.toString());
		Model model = Games.get().getGames().get(gameId);
		model.roadBuilding(type, playerIndex, spot1, spot2);
		return null;
	}

}
