package catan.server.command.moves;

import catan.model.GameModel;
import catan.model.ResourceList;
import catan.server.command.Command;

public class OfferTradeCommand implements Command {

	private String type;
	private int playerIndex;
	private ResourceList offer;
	private int receiver;

	@Override
	public Object execute(GameModel game) {
		System.out.println(type + playerIndex + " " + receiver);
		offer.testString();
		return null;
	}
}
