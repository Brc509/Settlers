package catan.server.command.moves;

import catan.model.ResourceList;
import catan.server.command.Command;

public class MovesOfferTradeCommand implements Command {

	private String type;
	private int playerIndex;
	private ResourceList offer;
	private int receiver;
	
	@Override
	public Object execute() {
		System.out.println(type + playerIndex + " " + receiver);
		offer.testString();
		return null;
	}
}
