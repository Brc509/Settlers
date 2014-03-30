package catan.model;

public class EdgeLocation {
	private int x;
	private int y;
	private String direction;
	private transient final String[] EDGE_STRINGS = new String[] {"NW","N","NE","SE","S","SW"};
	
	public EdgeLocation(int x, int y, int direction) {
		
		this.x = x;
		this.y = y;
		this.direction = EDGE_STRINGS[direction];
	}
	
	@Override
	public String toString() {
		return "EdgeLocation [x=" + x + ", y=" + y + ", direction=" + direction
				+ "]";
	}
	
	public int getDirectionIndex() {
		
		for (int i = 0; i < EDGE_STRINGS.length; i++) {
			
			if (EDGE_STRINGS[i].equals(direction))
				return i;
		}
		return -1;
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

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
}

//public class EdgeLocation {
//
//	private int x;
//	private int y;
//	private int direction;
//	private transient final String[] EDGE_STRINGS = new String[] {"NW","N","NE","SE","S","SW"};
//	
//	public EdgeLocation () {}
//	
//	public EdgeLocation(int x, int y, int direction) {
//		
//		this.x = x;
//		this.y = y;
//		this.direction = direction;
//	}
//	
//	public EdgeLocation(int x, int y, String direction) {
//		
//		this.x = x;
//		this.y = y;
//		
//		boolean found = false;
//		for (int i = 0; i < EDGE_STRINGS.length; i++) {
//			
//			if (EDGE_STRINGS[i].equals(direction)) {
//				
//				this.direction = i;
//				found = true;
//				break;
//			}
//		}
//		if (!found)
//			System.out.println("ERROR: When trying to create a new EdgeLocation, an invalid direction was created");
//	}
//
//	public int getX() {
//		return x;
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public int getY() {
//		return y;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//
//	public int getDirection() {
//		return direction;
//	}
//	
//	public String getDirectionString() {
//		
//		return EDGE_STRINGS[direction];
//	}
//
//	public void setDirection(int direction) {
//		this.direction = direction;
//	}
//	
//	public void setDirection(String direction) throws Exception {
//		
//		boolean found = false;
//		for (int i = 0; i < EDGE_STRINGS.length; i++) {
//			
//			if (EDGE_STRINGS[i].equals(direction)) {
//				
//				this.direction = i;
//				found = true;
//				break;
//			}
//		}
//		if (!found)
//			throw new Exception();
//	}
//
//	@Override
//	public String toString() {
//		return "EdgeLocation [x=" + x + ", y=" + y + ", direction=" + direction
//				+ "]";
//	}
//}
