package catan.server.command.moves;

import catan.model.GameModel;
import catan.model.ResourceList;
import catan.server.command.Command;

public class DiscardCardsCommand implements Command {

	private String type;
	private int playerIndex;
	private ResourceList discardedCards;

	public DiscardCardsCommand() {}

	@Override
	public Object execute(GameModel game) {
		// TODO Auto-generated method stub
		return null;
	}

}
