package catan.server.command.moves;

import catan.server.command.Command;

public class MovesMaritimeTradeCommand implements Command {

	private String type;
	private int playerIndex;
	private int ratio;
	private String inputResource;
	private String outputResource;
		
	public MovesMaritimeTradeCommand () {}
	
	@Override
	public Object execute(Object obj) {
		// TODO Auto-generated method stub
		System.out.println(type + playerIndex + ratio + inputResource + outputResource);
		return null;
	}
}
