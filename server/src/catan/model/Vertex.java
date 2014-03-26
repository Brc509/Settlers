package catan.model;

public class Vertex {

	private VertexValue value;
	
	public Vertex() {
		
		value = new VertexValue(-1, 0);
	}

	public VertexValue getValue() {
		return value;
	}

	public void setValue(VertexValue value) {
		this.value = value;
	}
}
