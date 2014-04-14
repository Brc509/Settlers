package catan.server.command;

import java.io.Serializable;

import catan.model.GameModel;

public interface Command extends Serializable{

	public Object execute(GameModel game);
}
