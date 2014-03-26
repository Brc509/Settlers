package catan.server.command.moves;

import catan.server.command.Command;

public class MovesBuildCityCommand implements Command {

	String type;
	int playerIndex;
	VertexLocation vertexLocation;
	boolean free;
	
	public MovesBuildCityCommand() {}
	
	@Override
	public Object execute() {

		System.out.println("TODO, actually do things with the variables and return the game model to send back");
		return null;
	}
	
	public void print() {
		
		System.out.println(vertexLocation.toString());
	}
	
	class VertexLocation {
		
		int x;
		int y;
		String direction;
		
		public VertexLocation() {}
		
		public String toString() {
			
			String str = "";
			str += x + " ";
			str += y + " ";
			str += direction;
			return str;
		}
	}
}
