package catan.server.persistence;

import java.util.List;
import java.util.Map;

import catan.model.GameModel;
import catan.server.RegisteredUser;
import catan.server.command.Command;

public interface PersistenceProvider {

	/**
	 * Sets the checkpoint frequency
	 * 
	 * @param frequency The frequency to save full game models
	 */
	public void setCheckpointFrequency(int frequency);

	/**
	 * Saves a user to disk
	 * 
	 * @param user The RegisteredUser object to save to disk
	 */
	public void saveUser(RegisteredUser user);

	/**
	 * Loads all users into memory
	 * 
	 * @return A list containing all users
	 */
	public List<RegisteredUser> loadUsers();

	/**
	 * Saves a newly created model to disk
	 * 
	 * @param gameID The ID of the new game
	 * @param model GameModel object containing the beginning state of the game
	 */
	public void saveBaseline(int gameID, GameModel model);

	/**
	 * Saves a command to disk
	 * 
	 * @param gameID The ID of the game the command belongs to
	 * @param command Command object to save to disk
	 */
	public void saveCommand(int gameID, Command command);

	/**
	 * Loads all games into memory
	 * 
	 * @return A map containing all games
	 */
	public Map<Integer, GameModel> loadGames();
}
