package catan.model;

public class Vertex {

	private VertexLocation location;
	private int ownerID;
	private int worth;
	
	public Vertex(int x, int y, int direction) {
		
		location = new VertexLocation(x, y, direction);
		ownerID = -1;
		worth = 0;
	}

	public VertexLocation getLocation() {
		return location;
	}

	public void setLocation(VertexLocation location) {
		this.location = location;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public int getWorth() {
		return worth;
	}

	public void setWorth(int worth) {
		this.worth = worth;
	}
}
