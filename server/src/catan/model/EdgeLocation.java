package catan.model;

public class EdgeLocation {

	private int x;
	private int y;
	private int direction;
	private final String[] EDGE_STRINGS = new String[] {"NW","N","NE","SE","S","SW"};
	
	public EdgeLocation(int x, int y, int direction) {
		
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public EdgeLocation(int x, int y, String direction) throws Exception {
		
		this.x = x;
		this.y = y;
		
		boolean found = false;
		for (int i = 0; i < EDGE_STRINGS.length; i++) {
			
			if (EDGE_STRINGS[i].equals(direction)) {
				
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
		
		return EDGE_STRINGS[direction];
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void setDirection(String direction) throws Exception {
		
		boolean found = false;
		for (int i = 0; i < EDGE_STRINGS.length; i++) {
			
			if (EDGE_STRINGS[i].equals(direction)) {
				
				this.direction = i;
				found = true;
				break;
			}
		}
		if (!found)
			throw new Exception();
	}
}
