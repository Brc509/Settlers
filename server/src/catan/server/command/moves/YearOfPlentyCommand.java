package catan.server.command.moves;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import catan.model.DevCardList;
import catan.model.Model;
import catan.model.Player;
import catan.model.ResourceList;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.Command;

/**
 * When executed, plays a Year of Plenty development card.
 * 
 * @author Spencer Bench
 */
public class YearOfPlentyCommand implements Command {

	private String type;
	private int playerIndex;
	private String resource1;
	private String resource2;

	public YearOfPlentyCommand() {}

	@Override
	public Boolean execute(Object obj) {
//		Server.println("  Attempting to execute command \"" + type + "\".");
//		Model game = Games.get().getGames().get(obj);
//		return game.yearOfPlenty(playerIndex, resource1, resource2);
//		
//		// Guilty until proven innocent
//				boolean verdict = false;
//
//				// Check validity of playerIndex and resource names
//				if (playerIndex >= 0 && playerIndex < 4 && resourceNames.contains(resource1) && resourceNames.contains(resource2)) {
//
//					Player[] players = model.getPlayers[];
//					Player player = players[playerIndex];
//
//					// Make sure player has a Year of Plenty card to play
//					DevCardList oldDevCards = player.getOldDevCards();
//					int oldPlayerYearOfPlenty = oldDevCards.getYearOfPlenty();
//					if (oldPlayerYearOfPlenty > 0) {
//
//						// Get old bank resource values
//						DevCardList bank = model.getBank();
//						int bankNum1 = bank.get(resource1);
//						int bankNum2 = bank.get(resource2);
//
//						// Make sure the bank has resources to give
//						boolean bankSufficient;
//						int resourceDelta;
//						if (resource1.equals(resource2)) {
//							bankSufficient = bankNum1 > 1;
//							resourceDelta = 2;
//						} else {
//							bankSufficient = bankNum1 > 0 && bankNum2 > 0;
//							resourceDelta = 1;
//						}
//						if (bankSufficient) {
//
//							// Get old player resource values
//							ResourceList resources = player.getResources();
//							int playerNum1 = resources.get(resource1);
//							int playerNum2 = resources.get(resource2);
//
//							// Increment the player's resources
//							resources.addProperty(resource1, playerNum1 + resourceDelta);
//							resources.addProperty(resource2, playerNum2 + resourceDelta);
//
//							// Decrement the bank's resources
//							bank.addProperty(resource1, bankNum1 - resourceDelta);
//							bank.addProperty(resource2, bankNum2 - resourceDelta);
//
//							// Move the Year of Plenty card back to the deck
//							oldDevCards.addProperty("yearOfPlenty", oldPlayerYearOfPlenty - 1);
//							JsonObject deck = model.getAsJsonObject("deck");
//							int oldDeckYearOfPlenty = deck.get("yearOfPlenty").getAsInt();
//							deck.addProperty("yearOfPlenty", oldDeckYearOfPlenty + 1);
//
//							// Add the log entry
//							addLogEntry(playerIndex, " played a Year of Plenty card.");
//
//							// Innocent
//							verdict = true;
//						}
//					}
//				}
//
//				// Return the verdict
//				return verdict;
		return null;
	}
}
