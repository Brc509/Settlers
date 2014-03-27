package catan.server.command.moves;

import catan.model.Model;
import catan.server.Games;
import catan.server.Server;
import catan.server.command.Command;

/**
 * When executed, adds a message to the chat log.
 * 
 * @author Spencer Bench
 */
public class MovesSendChatCommand implements Command {

	private String type;
	private int playerIndex;
	private String content;

	public MovesSendChatCommand() {}

	@Override
	public Boolean execute(Object obj) {
		if (Server.isDebugEnabled()) System.out.println("  Attempting to execute command \"" + type + "\".");
		Model game = Games.get().getGames().get(obj);
		return game.sendChat(playerIndex, content);
	}
}
