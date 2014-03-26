package catan.server.command.game;

import catan.model.ClientModel;
import catan.server.command.Command;

public class GameModelCommand implements Command{
	
	public GameModelCommand() {}

	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		ClientModel cm = new ClientModel();
		return null;
	}

}
