package catan.model;

public class VertexValue {

	private int ownerID;
	private int worth;
	public VertexValue(int ownerID, int worth) {
		
		this.ownerID = ownerID;
		this.worth = worth;
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
