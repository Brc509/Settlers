package catan.model;

public class Hex {

	private Edge[] edges;
	private Vertex[] vertexes;
	private HexLocation location;
	private String type;
	private boolean isLand;
	
	public Hex(int x, int y) {
		
		edges = new Edge[6];
		vertexes = new Vertex[6];
		for (int i = 0; i < 6; i++) {
			
			edges[i] = new Edge(x, y, i);
			vertexes[i] = new Vertex(x, y, i);
		}
		
		location = new HexLocation(x, y);
	}

	public Edge[] getEdges() {
		return edges;
	}

	public void setEdges(Edge[] edges) {
		this.edges = edges;
	}

	public Vertex[] getVertexes() {
		return vertexes;
	}

	public void setVertexes(Vertex[] vertexes) {
		this.vertexes = vertexes;
	}

	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation location) {
		this.location = location;
	}
	
	public void setType(String type) {
		
		this.type = type;
		isLand = true;
		if (type.equals("water"))
			isLand = false;	
	}
	
	public String getType() {
		
		return type;
	}
	
	public boolean isLand() {
		
		return isLand;
	}
}
