package catan.server.command.moves;

import catan.server.command.Command;

public class MovesMonopolyCommand implements Command{
	private String type;
	private String resource;
	private int playerIndex;

	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		System.out.println(type + resource + playerIndex);
		return null;
	}
}
