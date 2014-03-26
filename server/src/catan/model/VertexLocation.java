package catan.model;

public class VertexLocation {

	private int x;
	private int y;
	private int direction;
	private transient final String[] VERTEX_STRINGS = new String[] {"W","NW","NE","E","SE","SW"};
	
	public VertexLocation(int x, int y, int direction) {
		
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public VertexLocation(int x, int y, String direction) throws Exception {
		
		this.x = x;
		this.y = y;
		
		boolean found = false;
		for (int i = 0; i < VERTEX_STRINGS.length; i++) {
			
			if (VERTEX_STRINGS[i].equals(direction)) {
				
				this.direction = i;
				found = true;
				break;
			}
		}	
		if (!found) 
			throw new Exception();
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

	public int getDirection() {
		
		return direction;
	}
	public String getDirectionString() {
		
		return VERTEX_STRINGS[direction];
	}

	public void setDirection(int direction) {
		
		this.direction = direction;
	}
	
	/**
	 * Set the direction as a string (e.g. "NW" or "N")
	 * It will be stored as an integer
	 * @param direction
	 * @throws Exception
	 */
	public void setDirection(String direction) throws Exception {
		
		boolean found = false;
		for (int i = 0; i < VERTEX_STRINGS.length; i++) {
			
			if (VERTEX_STRINGS[i].equals(direction)) {
				
				this.direction = i;
				found = true;
			}
		}	
		if (!found) 
			throw new Exception();
	}
}
