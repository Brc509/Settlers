package catan.server.command.games;

import java.util.List;

import catan.model.GameModel;
import catan.model.Player;
import catan.server.Games;
import catan.server.RegisteredUser;
import catan.server.RegisteredUsers;
import catan.server.Server;
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
	private final String userName;

	public GamesJoinCommand(int userID, int gameID, String color, String userName) {
		this.userID = userID;
		this.gameID = gameID;
		this.color = color;
		this.userName = userName;
	}

	@Override
	public Boolean execute(GameModel game) {

		boolean success = false;
		// TODO This can't be hardcoded

		game = Games.get().getGames().get(gameID);
		Player[] players = game.getPlayers();

		int orderNumber = 0;
		for (int i = 0; i < players.length; i++) {
			String name = players[i].getName();
			Player p = players[i];
			if (userName.equals(p.getName()) && p.getPlayerID() >= 0) {
				orderNumber = i;
				break;
			} else if (!name.equals("") && p.getPlayerID() >= 0) {
				orderNumber++;
			}
		}

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
				success = game.setPlayer(orderNumber, userID, name, color);
				if (Server.isDebugEnabled() && success) {
					System.out.println("  \"" + name + "\" (" + userID + ") joined game " + gameID + " as \"" + color + "\".");
				}
			}
		}
		return success;
	}
}
