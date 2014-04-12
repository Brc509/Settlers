package catan.server.persistence;

import java.io.File;
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
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class JsonPlugin implements PersistenceProvider {

	// Static constants
	private static final Gson gson = new Gson();
	private static final JsonObject EMPTY_JSON_OBJECT = new JsonObject();
	private static final File USERS_FILE = new File("server/persistence/json/users.json");
	private static final File GAMES_FILE = new File("server/persistence/json/games.json");
	private static final File COMMANDS_FILE = new File("server/persistence/json/commands.json");

	// Static mutables
	private static boolean loading = false;

	// Create and initialize new JSON files if necessary
	static {
		try {
			if (!USERS_FILE.exists()) {
				USERS_FILE.getParentFile().mkdirs();
				USERS_FILE.createNewFile();
			}
			if (!GAMES_FILE.exists()) {
				GAMES_FILE.getParentFile().mkdirs();
				GAMES_FILE.createNewFile();
			}
			if (!COMMANDS_FILE.exists()) {
				COMMANDS_FILE.getParentFile().mkdirs();
				COMMANDS_FILE.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (USERS_FILE.length() == 0) {
			writeJSONToFile(USERS_FILE, new JsonArray());
		}
		if (GAMES_FILE.length() == 0) {
			writeJSONToFile(GAMES_FILE, new JsonArray());
		}
		if (COMMANDS_FILE.length() == 0) {
			writeJSONToFile(COMMANDS_FILE, new JsonArray());
		}
	}

	private static <T> T readJSONFromFile(File file, Class<T> classOfT) {
		T t = null;
		try {
			FileReader fr = new FileReader(file);
			t = gson.fromJson(fr, classOfT);
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	private static void writeJSONToFile(File file, Object src) {
		try {
			FileWriter fw = new FileWriter(file);
			gson.toJson(src, fw);
			fw.close();
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
		System.out.println("Checkpoint frequency set to " + checkpointFrequency + ".");
	}

	@Override
	public void saveUser(RegisteredUser user) {
		System.out.println("Saving user (\"" + user.getName() + "\", \"" + user.getPassword() + "\", " + user.getPlayerID() + ")...");
		try {
			// Read in the users array
			JsonArray usersArray = readJSONFromFile(USERS_FILE, JsonArray.class);
			// Add the user (duplicates are possible)
			JsonObject userObject = new JsonObject();
			userObject.addProperty("username", user.getName());
			userObject.addProperty("password", user.getPassword());
			userObject.addProperty("userID", user.getPlayerID());
			usersArray.add(userObject);
			// Write out the users array
			writeJSONToFile(USERS_FILE, usersArray);
		} catch (JsonSyntaxException | JsonIOException e) {
			e.printStackTrace();
		}
		System.out.println("Done.");
	}

	@Override
	public List<RegisteredUser> loadUsers() {
		System.out.println("Loading users...");
		List<RegisteredUser> users = new ArrayList<>();
		try {
			// Read in the users array
			JsonArray usersArray = readJSONFromFile(USERS_FILE, JsonArray.class);
			// Create RegisteredUser objects
			JsonObject userObject;
			for (int n = 0; n < usersArray.size(); n++) {
				userObject = usersArray.get(n).getAsJsonObject();
				RegisteredUser user = new RegisteredUser(userObject.get("username").getAsString(), userObject.get("password").getAsString(), userObject.get("userID").getAsInt());
				users.add(user);
			}
		} catch (JsonSyntaxException | JsonIOException e) {
			e.printStackTrace();
		}
		System.out.println("Done.");
		return users;
	}

	@Override
	public void saveBaseline(int gameID, GameModel model) {
		System.out.println("Saving baseline for game " + gameID + "...");
		try {
			// Read in the games array
			JsonArray gamesArray = readJSONFromFile(GAMES_FILE, JsonArray.class);
			// Get the right game
			JsonObject gameObject = getObjectForGame(gamesArray, gameID);
			// Set the baseline
			gameObject.add("baseline", gson.toJsonTree(model));
			// Initialize other fields if necessary
			if (!gameObject.has("checkpoint")) {
				gameObject.add("checkpoint", new JsonObject());
			}
			if (!gameObject.has("lastCommand")) {
				gameObject.addProperty("lastCommand", 0);
			}
			// Initialize command fields if necessary
			JsonArray commandListArray = readJSONFromFile(COMMANDS_FILE, JsonArray.class);
			JsonObject commandList = getObjectForGame(commandListArray, gameID);
			if (!commandList.has("commands")) {
				commandList.add("commands", new JsonArray());
				writeJSONToFile(COMMANDS_FILE, commandListArray);
			}
			// Write out the games array
			writeJSONToFile(GAMES_FILE, gamesArray);
		} catch (JsonSyntaxException | JsonIOException e) {
			e.printStackTrace();
		}
		System.out.println("Done.");
	}

	@Override
	public void saveCommand(int gameID, Command command) {
		if (!loading) {
			System.out.println("Saving command \"" + command.getClass().getSimpleName() + "\" for game " + gameID + "...");
			try {
				// Read in the commands array
				JsonArray commandListArray = readJSONFromFile(COMMANDS_FILE, JsonArray.class);
				// Get the right command list
				JsonObject commandListObject = getObjectForGame(commandListArray, gameID);
				// Create the internal commands array if it doesn't exist
				if (!commandListObject.has("commands")) {
					commandListObject.add("commands", new JsonArray());
				}
				// Add the command
				commandListObject.getAsJsonArray("commands").add(gson.toJsonTree(command));
				// Write out the commands array
				writeJSONToFile(COMMANDS_FILE, commandListArray);
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
			} catch (JsonSyntaxException | JsonIOException e) {
				e.printStackTrace();
			}
			System.out.println("Done.");
		}
	}

	@Override
	public Map<Integer, GameModel> loadGames() {
		System.out.println("Loading games...");
		Map<Integer, GameModel> games = new HashMap<>();
		loading = true; // Prevent self-interference while loading
		try {
			// Read in the games and commands arrays
			JsonArray gamesArray = readJSONFromFile(GAMES_FILE, JsonArray.class);
			JsonArray commandListArray = readJSONFromFile(COMMANDS_FILE, JsonArray.class);
			JsonObject gameObject;
			JsonObject modelObject;
			JsonArray commandsArray;
			JsonObject commandObject;
			Command command;
			String endpoint;
			// For each game:
			for (int gameIndex = 0; gameIndex < gamesArray.size(); gameIndex++) {
				gameObject = gamesArray.get(gameIndex).getAsJsonObject();
				int gameID = gameObject.get("gameID").getAsInt();
				// Get the checkpoint model, or the baseline model if there is no checkpoint
				modelObject = gameObject.getAsJsonObject("checkpoint");
				if (modelObject.equals(EMPTY_JSON_OBJECT)) {
					modelObject = gameObject.getAsJsonObject("baseline");
				}
				GameModel game = gson.fromJson(modelObject, GameModel.class);
				// Get the internal commands array
				commandsArray = getObjectForGame(commandListArray, gameID).getAsJsonArray("commands");
				// Deserialize and execute remaining commands on the model
				int lastCommand = gameObject.get("lastCommand").getAsInt();
				for (int commandIndex = lastCommand; commandIndex < commandsArray.size(); commandIndex++) {
					commandObject = commandsArray.get(commandIndex).getAsJsonObject();
					endpoint = "/moves/" + commandObject.get("type").getAsString();
					command = gson.fromJson(commandObject, HandlerUtils.getCommandClassForEndpoint(endpoint));
					command.execute(game);
				}
				// Add the fully restored model to the list
				games.put(gameID, game);
			}
		} catch (JsonSyntaxException | JsonIOException e) {
			e.printStackTrace();
		}
		loading = false;
		System.out.println("Done.");
		return games;
	}

	private void saveCheckpoint(int gameID, GameModel model) {
		System.out.println("Saving checkpoint for game " + gameID + "...");
		try {
			// Read in the games array
			JsonArray gamesArray = readJSONFromFile(GAMES_FILE, JsonArray.class);
			// Get the right game
			JsonObject gameObject = getObjectForGame(gamesArray, gameID);
			// Set the checkpoint
			gameObject.add("checkpoint", gson.toJsonTree(model));
			// Set the last command executed
			gameObject.addProperty("lastCommand", commandCounts.get(gameID));
			// Write out the games array
			writeJSONToFile(GAMES_FILE, gamesArray);
		} catch (JsonSyntaxException | JsonIOException e) {
			e.printStackTrace();
		}
		System.out.println("Done.");
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
