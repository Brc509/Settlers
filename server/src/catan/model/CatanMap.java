package catan.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CatanMap {

	HexGrid hexGrid;
	Port[] ports;
	HexLocation robber;
	Map<Integer, ArrayList<Hex>> tokens;
	
	public CatanMap() {
		
		hexGrid = new HexGrid();
		
		ports = new Port[9];
		for (int i = 0; i < ports.length; i++)
			ports[i] = new Port();
		
		tokens = new HashMap<>();
		for (int i = 2; i <= 12; i++)
			if (i != 7)
				tokens.put(i, new ArrayList<Hex>());
	}
	
	/**
	 * Create the default map that the TA's always created
	 * @throws Exception 
	 */
	public void createDefaultMap() throws Exception {
		
		createDefaultPorts();
		placeDefaultTokens();
		hexGrid.setDefaultTypes();
		robber = new HexLocation(0, -2);
	}
	
	/**
	 * Create the 9 default ports
	 * @throws Exception 
	 */
	public void createDefaultPorts() throws Exception {
		
		// Parameters: ResourceName, Hex X-coord, Hex Y-coord, Edge-Direction, Ratio, vv0-Direction, vv1-Direction
		
		ports[0] = new Port("wood", -3, -2, "NE", 2, "NE", "E");
		ports[1] = new Port(null, 3, -3, "SW", 3, "SW", "W");
		ports[2] = new Port(null, 2, 1, "NW", 3, "W", "NW");
		ports[3] = new Port(null, 0, 3, "N", 3, "NW", "NE");
		ports[4] = new Port("ore", 1, -3, "S", 2, "SE", "SW");
		ports[5] = new Port("wheat", -1, -2, "S", 2, "SE", "SW");
		ports[6] = new Port("sheep", 3, -1, "NW", 2, "W", "NW");
		ports[7] = new Port("brick", -2, 3, "NE", 2, "NE", "E");
		ports[8] = new Port(null, -3, 0, "SE", 3, "E", "SE");
	}
	
	/**
	 * Place the default tokens (the numbers 2-12 that are associated with hexes)
	 */
	public void placeDefaultTokens() {
		
		tokens.get(2).add(hexGrid.getHexes()[4][1]);
		
		tokens.get(3).add(hexGrid.getHexes()[2][2]);
		tokens.get(3).add(hexGrid.getHexes()[5][2]);
		
		tokens.get(4).add(hexGrid.getHexes()[1][2]);
		tokens.get(4).add(hexGrid.getHexes()[4][3]);
		
		tokens.get(5).add(hexGrid.getHexes()[3][1]);
		tokens.get(5).add(hexGrid.getHexes()[3][4]);
		
		tokens.get(6).add(hexGrid.getHexes()[3][5]);
		tokens.get(6).add(hexGrid.getHexes()[5][1]);
		
		tokens.get(8).add(hexGrid.getHexes()[2][1]);
		tokens.get(8).add(hexGrid.getHexes()[5][3]);
		
		tokens.get(9).add(hexGrid.getHexes()[2][3]);
		tokens.get(9).add(hexGrid.getHexes()[4][2]);
		
		tokens.get(10).add(hexGrid.getHexes()[3][2]);
		tokens.get(10).add(hexGrid.getHexes()[4][4]);
		
		tokens.get(11).add(hexGrid.getHexes()[1][3]);
		tokens.get(11).add(hexGrid.getHexes()[3][3]);
		
		tokens.get(12).add(hexGrid.getHexes()[2][4]);
	}
}
