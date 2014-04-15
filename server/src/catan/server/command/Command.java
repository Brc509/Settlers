package catan.server.command;

import catan.model.GameModel;

public interface Command {

	public Object execute(GameModel game);
}
