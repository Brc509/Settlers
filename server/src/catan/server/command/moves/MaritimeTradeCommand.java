package catan.server.command.moves;

import catan.model.GameModel;
import catan.model.Player;
import catan.model.ResourceList;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.Command;

public class MaritimeTradeCommand implements Command {

	private String type;
	private int playerIndex;
	private int ratio;
	private String inputResource;
	private String outputResource;

	public MaritimeTradeCommand() {}

	@Override
	public Boolean execute(GameModel game) {

		boolean success = false;

		Player player = game.getPlayerByIndex(playerIndex);
		if (player.getResources().decrementResource(inputResource, ratio)) {

			// Add output resource to the player
			player.getResources().incrementResource(outputResource);
			game.setPlayerResources(playerIndex, player.getResources());

			ResourceList bank = game.getBank();
			bank.decrementResource(outputResource);
			bank.incrementResource(inputResource, ratio);
			game.setBank(bank);

			game.addLogEntry(playerIndex, " performed Maritime Trade.");

			success = true;
		}

		// Save the command
		Server.getPP().saveCommand(Games.get().getGameID(game), this);

		return success;
	}
}
