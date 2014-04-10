package catan.server.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import catan.model.GameModel;
import catan.server.Games;
import catan.server.RegisteredUser;
import catan.server.command.Command;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class JsonPlugin implements PersistenceProvider {

	// Static constants
	private static final Gson gson = new Gson();
	private static final File USERS_FILE = new File("users.json");
	private static final File GAMES_FILE = new File("games.json");
	private static final File COMMANDS_FILE = new File("commands.json");

	// Static mutables
	private static boolean loading = false;

	// Create new JSON files if they don't exist
	static {
		try {
			if (!USERS_FILE.exists()) {
				USERS_FILE.createNewFile();
				new FileWriter(USERS_FILE).write("[]");
			}
			if (!GAMES_FILE.exists()) {
				GAMES_FILE.createNewFile();
				new FileWriter(GAMES_FILE).write("[]");
			}
			if (!COMMANDS_FILE.exists()) {
				COMMANDS_FILE.createNewFile();
				new FileWriter(COMMANDS_FILE).write("[]");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Instance fields
	private int checkpointFrequency;
	private final Map<Integer, Integer> commandCounts;
	private final Map<Integer, Integer> checkpointCounts;

	public JsonPlugin() {
		checkpointFrequency = 1; // Default to save a checkpoint every time a command is saved
		commandCounts = new HashMap<>();
		checkpointCounts = new HashMap<>();
	}

	@Override
	public void setCheckpointFrequency(int frequency) {
		checkpointFrequency = frequency;
	}

	@Override
	public void saveUser(RegisteredUser user) {
		try {
			// Read in the users array
			JsonArray usersArray = gson.fromJson(new FileReader(USERS_FILE), JsonArray.class);
			// Add the user (duplicates are possible)
			JsonObject userObject = new JsonObject();
			userObject.addProperty("username", user.getName());
			userObject.addProperty("password", user.getPassword());
			userObject.addProperty("userID", user.getPlayerID());
			usersArray.add(userObject);
			// Write out the users array
			gson.toJson(usersArray, new FileWriter(USERS_FILE));
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<RegisteredUser> loadUsers() {
		List<RegisteredUser> users = new ArrayList<>();
		try {
			// Read in the users array
			JsonArray usersArray = gson.fromJson(new FileReader(USERS_FILE), JsonArray.class);
			// Create RegisteredUser objects
			JsonObject userObject;
			for (int n = 0; n < usersArray.size(); n++) {
				userObject = usersArray.get(n).getAsJsonObject();
				RegisteredUser user = new RegisteredUser(userObject.get("username").getAsString(), userObject.get("password").getAsString(), userObject.get("userID").getAsInt());
				users.add(user);
			}
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public void saveBaseline(int gameID, GameModel model) {
		try {
			// Read in the games array
			JsonArray gamesArray = gson.fromJson(new FileReader(GAMES_FILE), JsonArray.class);
			// Get the right game
			JsonObject gameObject = getObjectForGame(gamesArray, gameID);
			// Set the baseline
			gameObject.add("baseline", gson.toJsonTree(model));
			// Write out the games array
			gson.toJson(gamesArray, new FileWriter(GAMES_FILE));
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveCommand(int gameID, Command command) {
		if (!loading) {
			try {
				// Read in the commands array
				JsonArray commandListArray = gson.fromJson(new FileReader(COMMANDS_FILE), JsonArray.class);
				// Get the right command list
				JsonObject commandListObject = getObjectForGame(commandListArray, gameID);
				// Create the internal commands array if it doesn't exist
				if (!commandListObject.has("commands")) {
					commandListObject.add("commands", new JsonArray());
				}
				// Add the command
				commandListObject.getAsJsonArray("commands").add(gson.toJsonTree(command));
				// Write out the commands array
				gson.toJson(commandListArray, new FileWriter(COMMANDS_FILE));
				// Create map entries for this game ID if they doesn't already exist
				if (!commandCounts.containsKey(gameID)) {
					commandCounts.put(gameID, 0);
				}
				if (!checkpointCounts.containsKey(gameID)) {
					checkpointCounts.put(gameID, 0);
				}
				// Increment the command count for this game ID
				commandCounts.put(gameID, commandCounts.get(gameID) + 1);
				// Increment the checkpoint count for this game ID
				checkpointCounts.put(gameID, checkpointCounts.get(gameID) + 1);
				// If the checkpoint count has reached the checkpoint threshold, save a checkpoint
				if (checkpointCounts.get(gameID) >= checkpointFrequency) {
					saveCheckpoint(gameID, Games.get().getGames().get(gameID));
					// Reset the checkpoint count for this game ID
					checkpointCounts.put(gameID, 0);
				}
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<GameModel> loadGames() {
		List<GameModel> games = new ArrayList<>();
		loading = true; // Prevent self-interference while loading
		try {
			// Read in the games and commands arrays
			JsonArray gamesArray = gson.fromJson(new FileReader(GAMES_FILE), JsonArray.class);
			JsonArray commandListArray = gson.fromJson(new FileReader(COMMANDS_FILE), JsonArray.class);
			JsonObject gameObject;
			JsonObject modelObject;
			JsonArray commandsArray;
			// For each game:
			for (int gameIndex = 0; gameIndex < gamesArray.size(); gameIndex++) {
				gameObject = gamesArray.get(gameIndex).getAsJsonObject();
				// Get the game ID and last command executed
				int gameID = gameObject.get("gameID").getAsInt();
				int lastCommand = gameObject.get("lastCommand").getAsInt();
				// Get the checkpoint model, or the baseline model if there is no checkpoint
				if (gameObject.has("checkpoint")) {
					modelObject = gameObject.getAsJsonObject("checkpoint");
				} else {
					modelObject = gameObject.getAsJsonObject("baseline");
				}
				GameModel game = gson.fromJson(modelObject, GameModel.class);
				// Get the internal commands array
				commandsArray = getObjectForGame(commandListArray, gameID).getAsJsonArray("commands");
				// Deserialize and execute remaining commands on the model
				for (int commandIndex = lastCommand; commandIndex < commandsArray.size(); commandIndex++) {
					// TODO: We may have to check the "type" and use a big switch statement...
					Command command = gson.fromJson(commandsArray.get(commandIndex), Command.class);
					command.execute(game);
				}
				// Add the fully restored model to the list
				games.add(game);
			}
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
		loading = false;
		return games;
	}

	private void saveCheckpoint(int gameID, GameModel model) {
		try {
			// Read in the games array
			JsonArray gamesArray = gson.fromJson(new FileReader(GAMES_FILE), JsonArray.class);
			// Get the right game
			JsonObject gameObject = getObjectForGame(gamesArray, gameID);
			// Set the checkpoint
			gameObject.add("checkpoint", gson.toJsonTree(model));
			// Set the last command executed
			gameObject.addProperty("lastCommand", commandCounts.get(gameID));
			// Write out the games array
			gson.toJson(gamesArray, new FileWriter(GAMES_FILE));
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			e.printStackTrace();
		}
	}

	private JsonObject getObjectForGame(JsonArray array, int gameID) {
		JsonObject object;
		for (int n = 0; n < array.size(); n++) {
			object = array.get(n).getAsJsonObject();
			if (object.get("gameID").getAsInt() == gameID) {
				return object;
			}
		}
		object = new JsonObject();
		object.addProperty("gameID", gameID);
		array.add(object);
		return object;
	}
}
