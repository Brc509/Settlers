package catan.server.command.moves;

import catan.model.DevCardList;
import catan.model.Model;
import catan.model.TurnTracker;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.Command;

public class FinishTurnCommand implements Command {

	private String type;
	private int playerIndex;

	public FinishTurnCommand() {}

	@Override
	public Object execute(Object obj) {

		Server.println("Executing command: \"" + type + "\".");

		Model model = Games.get().getGames().get(obj);
		TurnTracker track = model.getTurnTracker();

		// Move the current player's new dev cards to old dev cards
		DevCardList oldDC = model.getPlayerOldDevCards(playerIndex);
		DevCardList newDC = model.getPlayerNewDevCards(playerIndex);

		oldDC.setMonopoly(oldDC.getMonopoly() + newDC.getMonopoly());
		oldDC.setMonument(oldDC.getMonument() + newDC.getMonument());
		oldDC.setRoadBuilding(oldDC.getRoadBuilding() + newDC.getRoadBuilding());
		oldDC.setSoldier(oldDC.getSoldier() + newDC.getSoldier());
		oldDC.setYearOfPlenty(oldDC.getYearOfPlenty() + newDC.getYearOfPlenty());

		newDC.setMonopoly(0);
		newDC.setMonument(0);
		newDC.setRoadBuilding(0);
		newDC.setSoldier(0);
		newDC.setYearOfPlenty(0);

		model.setPlayerOldDevCards(playerIndex, oldDC);
		model.setPlayerNewDevCards(playerIndex, newDC);

		// Go to the next player's turn
		track.setCurrentTurn((track.getCurrentTurn() + 1) % 4);
		track.setStatus("Rolling");
		model.setTurnTracker(track);

		// Add the log entry
		model.addLogEntry(playerIndex, "'s turn has ended.");

		return null;
	}
}
