package catan.server.command.moves;

import catan.server.command.Command;

public class MovesRollNumberCommand implements Command {
	private String type;
	private int playerIndex;
	private int number;
	
	@Override
	public Object execute(Object obj) {
		// TODO Auto-generated method stub
		System.out.println(type + playerIndex + number);
		return null;
	}
}
