package catan.model;

public class Port {

	private String inputResource;
	private EdgeLocation location;
	private int ratio;
	private VertexLocation validVertex1;
	private VertexLocation validVertex2;
	private String orientation = "NW";

	public Port() {
		
	}
	
	public Port(String inputResource, int hexX, int hexY, String edgeDirection, int ratio, String vv0Direction, String vv1Direction) {
		
		this.inputResource = inputResource;
//		this.location = new EdgeLocation(hexX, hexY, edgeDirection);
		this.ratio = ratio;
		this.validVertex1 = new VertexLocation(hexX, hexY, vv0Direction);
		this.validVertex2 = new VertexLocation(hexX, hexY, vv1Direction);
	}

	public String getInputResource() {
		return inputResource;
	}

	public void setInputResource(String inputResource) {
		this.inputResource = inputResource;
	}

	public EdgeLocation getLocation() {
		return location;
	}

	public void setLocation(EdgeLocation location) {
		this.location = location;
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	
	public VertexLocation getValidVertex1() {
		return validVertex1;
	}

	public void setValidVertex1(VertexLocation validVertex1) {
		this.validVertex1 = validVertex1;
	}

	public VertexLocation getValidVertex2() {
		return validVertex2;
	}

	public void setValidVertex2(VertexLocation validVertex2) {
		this.validVertex2 = validVertex2;
	}
}