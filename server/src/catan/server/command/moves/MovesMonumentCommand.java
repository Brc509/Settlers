package catan.server.command.moves;

import catan.server.command.Command;

public class MovesMonumentCommand implements Command {
	private String type;
	private int playerIndex;
	
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		System.out.println(type + playerIndex);
		return null;
	}

}
