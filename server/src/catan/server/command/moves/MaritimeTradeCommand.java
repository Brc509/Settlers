package catan.server.command.moves;

import catan.model.GameModel;
import catan.model.Player;
import catan.model.ResourceList;
import catan.server.Games;
import catan.server.command.Command;

public class MaritimeTradeCommand implements Command {

	private String type;
	private int playerIndex;
	private int ratio;
	private String inputResource;
	private String outputResource;
		
	public MaritimeTradeCommand () {}
	
	@Override
	public Boolean execute(Object gameId) {
		
		GameModel model = Games.get().getGames().get(gameId);
		
		Player player = model.getPlayerByIndex(playerIndex);
		if (player.getResources().decrementResource(inputResource, ratio)) {
			
			// Add output resource to the player
			player.getResources().incrementResource(outputResource);
			model.setPlayerResources(playerIndex, player.getResources());
			
			ResourceList bank = model.getBank();
			bank.decrementResource(outputResource);
			bank.incrementResource(inputResource, ratio);
			model.setBank(bank);
			
			model.addLogEntry(playerIndex, " performed Maritime Trade.");
			
			return null;
		}
		
		return false;
	}
}
