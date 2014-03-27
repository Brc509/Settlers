package catan.server.command.moves;

import catan.model.Model;
import catan.server.Games;
import catan.server.command.Command;

public class MovesMaritimeTradeCommand implements Command {

	private String type;
	private int playerIndex;
	private int ratio;
	private String inputResource;
	private String outputResource;
		
	public MovesMaritimeTradeCommand () {}
	
	@Override
	public Object execute(Object gameId) {
		// TODO Auto-generated method stub
		System.out.println(type + playerIndex + ratio + inputResource + outputResource);
		Model model = Games.get().getGames().get(gameId);
		model.maritimeTrade(type, playerIndex, ratio, inputResource, outputResource);
		return null;
	}
}
