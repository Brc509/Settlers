package catan.server.persistence;

import catan.model.Model;
import catan.model.Player;
import catan.server.command.Command;

public class JsonPlugin implements PersistenceProvider {
	private int frequency;

	@Override
	public void setCheckpointFrequency(int frequency) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveUser(Player registeredUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadUsers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveBaseline(int gameID, Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveCommand(int gameID, Command command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGames() {
		// TODO Auto-generated method stub
		
	}
}
