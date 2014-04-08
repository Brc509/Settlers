package catan.server.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import catan.model.GameModel;
import catan.server.Games;
import catan.server.RegisteredUser;
import catan.server.command.Command;

public class JsonPlugin implements PersistenceProvider {

	private int checkpointFrequency;
	private Map<Integer, Integer> commandCounts;

	public JsonPlugin() {
		checkpointFrequency = 1; // Default to save a checkpoint every time a command is saved
		commandCounts = new HashMap<>();
	}

	@Override
	public void setCheckpointFrequency(int frequency) {
		checkpointFrequency = frequency;
	}

	@Override
	public void saveUser(RegisteredUser user) {
		// TODO Save the user ("users.json")
	}

	@Override
	public List<RegisteredUser> loadUsers() {
		// TODO Load the users ("users.json")
		return null;
	}

	@Override
	public void saveBaseline(int gameID, GameModel model) {
		// TODO Save the base game model ("games.json")
	}

	@Override
	public void saveCommand(int gameID, Command command) {

		// TODO Save the command ("commands.json")

		// Create a command count entry for this game ID if it doesn't already exist
		if (!commandCounts.containsKey(gameID)) {
			commandCounts.put(gameID, 0);
		}
		// Increment the command count for this game ID
		commandCounts.put(gameID, commandCounts.get(gameID) + 1);
		// If the command count has reached the checkpoint threshold, save a checkpoint
		if (commandCounts.get(gameID) >= checkpointFrequency) {
			saveCheckpoint(gameID, Games.get().getGames().get(gameID));
			// Reset the command count for this game ID
			commandCounts.put(gameID, 0);
		}
	}

	@Override
	public List<GameModel> loadGames() {
		// TODO Load games ("games.json" and "commands.json")
		return null;
	}

	private void saveCheckpoint(int gameID, GameModel model) {
		// TODO Save the checkpoint ("games.json")
	}
}
