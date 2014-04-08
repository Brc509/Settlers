package catan.server.command.moves;

import catan.model.GameModel;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.Command;

/**
 * When executed, adds a message to the chat log.
 * 
 * @author Spencer Bench
 */
public class SendChatCommand implements Command {

	private String type;
	private int playerIndex;
	private String content;

	public SendChatCommand() {}

	@Override
	public Boolean execute(Object obj) {

		Server.println("Executing command: \"" + type + "\".");

		int gameID = (Integer) obj;
		GameModel game = Games.get().getGames().get(gameID);
		game.addChatEntry(playerIndex, content);

		return null;

//		Server.println("  Attempting to execute command \"" + type + "\".");
//		Model game = Games.get().getGames().get(obj);
////		return game.sendChat(playerIndex, content);
//		
//		boolean verdict = false;
//		if (playerIndex >= 0 && playerIndex < 4) {
//			String name = model.getPlayers()[playerIndex].getName();
////			model.getAsJsonObject("chat").getAsJsonArray("lines").add(createEntry(name, content));
//			model.createChatEntry(name, content);
//			verdict = true;
//		}
//		return verdict;
//	}
	}
}
