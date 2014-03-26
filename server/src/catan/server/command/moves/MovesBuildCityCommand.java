package catan.server.command.moves;

import catan.model.ClientModel;
import catan.server.Games;
import catan.server.command.Command;


public class MovesBuildCityCommand implements Command {

	private String type;
	private int playerIndex;
	private VertexLocation vertexLocation;
	private boolean free;
	private int gameID;
	
	public MovesBuildCityCommand() {}
	
	@Override
	public ClientModel execute() {

		ClientModel thisModel = Games.get().getGames().get(gameID);
		thisModel.buildCity(playerIndex, vertexLocation.x, vertexLocation.y, vertexLocation.direction, free);
		return thisModel;
	}
	
	public void setGameID(int gameID) {
		
		this.gameID = gameID;
	}
	
	public void print() {
		
		System.out.println(type);
		System.out.println(playerIndex);
		System.out.println(vertexLocation.x);
		System.out.println(vertexLocation.y);
		System.out.println(vertexLocation.direction);
		System.out.println(free);
		System.out.println(gameID);
	}
	
	class VertexLocation {
		
		public int x;
		public int y;
		public String direction;
	}
}
