package catan.server.command.moves;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import catan.model.DevCardList;
import catan.model.GameModel;
import catan.model.ResourceList;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.Command;

public class BuyDevCardCommand implements Command {

	private String type;
	private int playerIndex;

	public BuyDevCardCommand() {}

	@Override
	public Object execute(GameModel game) {

		Server.println("Executing command: \"" + type + "\".");

		// Pick a random dev card from the deck
		DevCardList deck = game.getDeck();
		List<Integer> stack = new ArrayList<>();
		for (int n = 0; n < deck.getMonopoly(); n++) {
			stack.add(0);
		}
		for (int n = 0; n < deck.getMonument(); n++) {
			stack.add(1);
		}
		for (int n = 0; n < deck.getRoadBuilding(); n++) {
			stack.add(2);
		}
		for (int n = 0; n < deck.getSoldier(); n++) {
			stack.add(3);
		}
		for (int n = 0; n < deck.getYearOfPlenty(); n++) {
			stack.add(4);
		}
		int index = new Random().nextInt(stack.size());
		int pick = stack.get(index);

		// Move the dev card from the deck to the player
		DevCardList newDC = game.getPlayerNewDevCards(playerIndex);
		if (pick == 0) {
			newDC.setMonopoly(newDC.getMonopoly() + 1);
			deck.setMonopoly(deck.getMonopoly() - 1);
		} else if (pick == 1) {
			newDC.setMonument(newDC.getMonument() + 1);
			deck.setMonument(deck.getMonument() - 1);
		} else if (pick == 2) {
			newDC.setRoadBuilding(newDC.getRoadBuilding() + 1);
			deck.setRoadBuilding(deck.getRoadBuilding() - 1);
		} else if (pick == 3) {
			newDC.setSoldier(newDC.getSoldier() + 1);
			deck.setSoldier(deck.getSoldier() - 1);
		} else if (pick == 4) {
			newDC.setYearOfPlenty(newDC.getYearOfPlenty() + 1);
			deck.setYearOfPlenty(deck.getYearOfPlenty() - 1);
		}
		game.setPlayerNewDevCards(playerIndex, newDC);
		game.setDeck(deck);

		// Charge the player for the card
		ResourceList pR = game.getPlayerResources(playerIndex);
		ResourceList bank = game.getBank();
		pR.setOre(pR.getOre() - 1);
		pR.setSheep(pR.getSheep() - 1);
		pR.setWheat(pR.getWheat() - 1);
		bank.setOre(bank.getOre() + 1);
		bank.setSheep(bank.getSheep() + 1);
		bank.setWheat(bank.getWheat() + 1);
		game.setPlayerResources(playerIndex, pR);
		game.setBank(bank);

		// Add a log entry
		game.addLogEntry(playerIndex, " bought a development card.");

		// Save the command
		Server.getPP().saveCommand(Games.get().getGameID(game), this);

		return null;
	}

}
