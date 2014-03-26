package catan.server.command.games;

import java.util.List;

import catan.model.ClientModel;
import catan.server.Games;
import catan.server.RegisteredUser;
import catan.server.RegisteredUsers;
import catan.server.command.Command;

/**
 * When executed, joins a player to an active game.
 * 
 * @author Spencer Bench
 */
public class GamesJoinCommand implements Command {

	private final int userID;
	private final int gameID;
	private final String color;

	public GamesJoinCommand(int userID, int gameID, String color) {
		System.out.println("GamesJoinCommand created: userID = " + userID + ", gameID = " + gameID + ", color = " + color);
		this.userID = userID;
		this.gameID = gameID;
		this.color = color;
	}

	@Override
	public Boolean execute() {
		boolean success = false;
		ClientModel game = Games.get().getGames().get(gameID);
		if (game != null) {
			List<RegisteredUser> users = RegisteredUsers.get().getUsers();
			String name = null;
			for (RegisteredUser user : users) {
				if (user.getPlayerID() == userID) {
					name = user.getName();
					break;
				}
			}
			if (name != null) {
				game.addPlayer(userID, name, color);
				success = true;
			}
		}
		return success;
	}
}
