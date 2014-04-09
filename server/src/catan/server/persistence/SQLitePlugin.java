package catan.server.persistence;

import java.util.List;

import catan.model.GameModel;
import catan.server.RegisteredUser;
import catan.server.command.Command;

public class SQLitePlugin implements PersistenceProvider {

	@Override
	public void setCheckpointFrequency(int frequency) {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveUser(RegisteredUser user) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<RegisteredUser> loadUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveBaseline(int gameID, GameModel model) {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveCommand(int gameID, Command command) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<GameModel> loadGames() {
		// TODO Auto-generated method stub
		return null;
	}
}
