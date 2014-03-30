package catan.model;

public class HexLocation {

	private String x;
	private String y;
	
	public HexLocation () {}
	
	public HexLocation(String x, String y) {
		
		this.x = x;
		this.y = y;
	}
	
	public HexLocation(int x, int y) {
		
		this.x = "" + x;
		this.y = "" + y;
	}
	
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "HexLocation [x=" + x + ", y=" + y + "]";
	}

}
