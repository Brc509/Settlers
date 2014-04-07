package catan.server.persistence;

import catan.model.*;
import catan.server.command.Command;

public interface PersistenceProvider {
	public void setCheckpointFrequency(int frequency);
	public void saveUser(Player registeredUser);
	public void loadUsers();
	public void saveBaseline(int gameID, Model model);
	public void saveCommand(int gameID, Command command);
	public void loadGames();
}
