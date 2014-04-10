package catan.server.command.moves;

import catan.model.DevCardList;
import catan.model.GameModel;
import catan.model.ResourceList;
import catan.server.Server;
import catan.server.command.Command;

public class MonopolyCommand implements Command {

	private String type;
	private String resource;
	private int playerIndex;

	@Override
	public Object execute(GameModel game) {

		Server.println("Executing command: \"" + type + "\".");

		resource = resource.toLowerCase();

		// Gather the victims' resources of the given type
		int brick = 0;
		int ore = 0;
		int sheep = 0;
		int wheat = 0;
		int wood = 0;
		for (int n = 0; n < 4; n++) {
			if (n != playerIndex) {
				ResourceList vResources = game.getPlayerResources(n);
				if (resource.equals("brick")) {
					brick += vResources.getBrick();
					vResources.setBrick(0);
				} else if (resource.equals("ore")) {
					ore += vResources.getOre();
					vResources.setOre(0);
				} else if (resource.equals("sheep")) {
					sheep += vResources.getSheep();
					vResources.setSheep(0);
				} else if (resource.equals("wheat")) {
					wheat += vResources.getWheat();
					vResources.setWheat(0);
				} else if (resource.equals("wood")) {
					wood += vResources.getWood();
					vResources.setWood(0);
				}
				game.setPlayerResources(n, vResources);
			}
		}

		// Give them to the player
		ResourceList pResources = game.getPlayerResources(playerIndex);
		pResources.setBrick(pResources.getBrick() + brick);
		pResources.setOre(pResources.getOre() + ore);
		pResources.setSheep(pResources.getSheep() + sheep);
		pResources.setWheat(pResources.getWheat() + wheat);
		pResources.setWood(pResources.getWood() + wood);
		game.setPlayerResources(playerIndex, pResources);

		// Subtract Monopoly card from player's old dev cards
		DevCardList pOldDevCards = game.getPlayerOldDevCards(playerIndex);
		pOldDevCards.setMonopoly(pOldDevCards.getMonopoly() - 1);
		game.setPlayerOldDevCards(playerIndex, pOldDevCards);

		// Add Monopoly card to deck
		DevCardList deck = game.getDeck();
		deck.setMonopoly(deck.getMonopoly() + 1);
		game.setDeck(deck);

		// Add a log entry
		game.addLogEntry(playerIndex, " played a Monopoly card and stole everyone's.");

		return null;
	}
}
