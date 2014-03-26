package catan.model;

public class Edge {

	private EdgeValue value;
	
	public Edge() {

		value = new EdgeValue(-1);
	}

	public EdgeValue getValue() {
		return value;
	}

	public void setValue(EdgeValue value) {
		this.value = value;
	}
}