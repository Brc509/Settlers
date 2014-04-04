package catan.model;

import java.util.List;

import catan.server.Games;
import catan.server.RegisteredUser;
import catan.server.command.Command;

public interface PersistenceProvider {

	/**
	 * Save this user into memory
	 * @param user The RegisteredUser object to save into memory
	 */
	public void saveUser(RegisteredUser user);
	
	/**
	 * Load all needed users into memory (for active games)
	 * @return A list containing all relevant users
	 */
	public List<RegisteredUser> loadUsers();
	
	/**
	 * Save a command into memory
	 * @param command Command object to save
	 */
	public void saveCommand(Command command);
	
	/**
	 * Save the current model's state into memory
	 * @param model Model object containing the current state of the game
	 */
	public void saveCheckpoint(Model model);
	
	/**
	 * Load all unfinished games into memory
	 * @return Games object containing all relevant games
	 */
	public Games loadGames();
}
