package catan.model;

public class Edge {

	private EdgeLocation location;
	private int ownerID;
	
	public Edge(int x, int y, int direction) {

		location = new EdgeLocation(x, y, direction);
		ownerID = -1;
	}

	public EdgeLocation getLocation() {
		return location;
	}

	public void setLocation(EdgeLocation location) {
		this.location = location;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}
}