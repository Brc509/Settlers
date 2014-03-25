package catan.model;

public class Port {

	private String inputResource;
	private EdgeLocation location;
	private int ratio;
	private VertexLocation[] validVertexes;
	
	public Port() {
		
		validVertexes = new VertexLocation[2];
	}
	
	public Port(String inputResource, int hexX, int hexY, String edgeDirection, int ratio, String vv0Direction, String vv1Direction) throws Exception {
		
		this.inputResource = inputResource;
		this.location = new EdgeLocation(hexX, hexY, edgeDirection);
		this.ratio = ratio;
		
		this.validVertexes = new VertexLocation[2];
		this.validVertexes[0] = new VertexLocation(hexX, hexY, vv0Direction);
		this.validVertexes[1] = new VertexLocation(hexX, hexY, vv1Direction);
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

	public VertexLocation[] getValidVertexes() {
		return validVertexes;
	}

	public void setValidVertexes(VertexLocation[] validVertexes) {
		this.validVertexes = validVertexes;
	}
}