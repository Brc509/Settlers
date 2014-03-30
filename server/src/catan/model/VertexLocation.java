package catan.model;

public class VertexLocation {

	private int x;
	private int y;
	private String direction;
	private transient final String[] VERTEX_STRINGS = new String[] {"W","NW","NE","E","SE","SW"};
	
	public VertexLocation(int x, int y, int direction) {
		
		this.x = x;
		this.y = y;
		this.direction = VERTEX_STRINGS[direction];
	}
	
	public VertexLocation(int x, int y, String direction) {
		
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getDirection() {
		return direction;
	}
	
	public int getDirectionIndex() {
		
		for (int i = 0; i < VERTEX_STRINGS.length; i++)
			if (VERTEX_STRINGS[i].equals(direction))
				return i;
		return -1;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
