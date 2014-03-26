package catan.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CatanMap {

	HexGrid hexGrid;
	Port[] ports;
	HexLocation robber;
	Map<Integer, ArrayList<HexLocation>> numbers;
	
	public CatanMap() {
		
		hexGrid = new HexGrid();
		
		ports = new Port[9];
		for (int i = 0; i < ports.length; i++)
			ports[i] = new Port();
		
		numbers = new HashMap<>();
		for (int i = 2; i <= 12; i++)
			if (i != 7)
				numbers.put(i, new ArrayList<HexLocation>());
	}
	
	/**
	 * Create the default map that the TA's always created
	 * @throws Exception 
	 */
	public void createMap(boolean randomNumbers, boolean randomHexes, boolean randomPorts) {
		
		if (randomNumbers)
			placeRandomNumbers();
		else
			placeDefaultnumbers();
		
		if (randomHexes) {
			
			hexGrid.setRandomTypes();
			robber = hexGrid.getDesertLocation();
		}
			
		else {
		
			hexGrid.setDefaultTypes();
			robber = new HexLocation(0, -2);
		}
		if (randomPorts)
			createRandomPorts();
		else
			createDefaultPorts();
	}
	
	/**
	 * Create the 9 default ports
	 * @throws Exception 
	 */
	public void createDefaultPorts() {
		
		// Parameters: ResourceName, Hex X-coord, Hex Y-coord, Edge-Direction, Ratio, vv0-Direction, vv1-Direction
		
		ports[0] = new Port("wood", -3, 2, "NE", 2, "NE", "E");
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
	 * Distribute the ports randomly
	 */
	public void createRandomPorts() {
		
		List<String> types = Arrays.asList(new String[] {"wood", "ore", "wheat", "sheep", "brick", null, null, null, null});
		Collections.shuffle(types);
		
		ports[0] = new Port(types.get(0), -3, 2, "NE", 2, "NE", "E");
		ports[1] = new Port(types.get(1), 3, -3, "SW", 3, "SW", "W");
		ports[2] = new Port(types.get(2), 2, 1, "NW", 3, "W", "NW");
		ports[3] = new Port(types.get(3), 0, 3, "N", 3, "NW", "NE");
		ports[4] = new Port(types.get(4), 1, -3, "S", 2, "SE", "SW");
		ports[5] = new Port(types.get(5), -1, -2, "S", 2, "SE", "SW");
		ports[6] = new Port(types.get(6), 3, -1, "NW", 2, "W", "NW");
		ports[7] = new Port(types.get(7), -2, 3, "NE", 2, "NE", "E");
		ports[8] = new Port(types.get(8), -3, 0, "SE", 3, "E", "SE");
	}
	
	/**
	 * Place the default numbers (the numbers 2-12 that are associated with hexes)
	 */
	public void placeDefaultnumbers() {
		
		numbers.get(2).add(hexGrid.getHexes()[4][1].getLocation());
		
		numbers.get(3).add(hexGrid.getHexes()[2][2].getLocation());
		numbers.get(3).add(hexGrid.getHexes()[5][2].getLocation());
		
		numbers.get(4).add(hexGrid.getHexes()[1][2].getLocation());
		numbers.get(4).add(hexGrid.getHexes()[4][3].getLocation());
		
		numbers.get(5).add(hexGrid.getHexes()[3][1].getLocation());
		numbers.get(5).add(hexGrid.getHexes()[3][4].getLocation());
		
		numbers.get(6).add(hexGrid.getHexes()[3][5].getLocation());
		numbers.get(6).add(hexGrid.getHexes()[5][1].getLocation());
		
		numbers.get(8).add(hexGrid.getHexes()[2][1].getLocation());
		numbers.get(8).add(hexGrid.getHexes()[5][3].getLocation());
		
		numbers.get(9).add(hexGrid.getHexes()[2][3].getLocation());
		numbers.get(9).add(hexGrid.getHexes()[4][2].getLocation());
		
		numbers.get(10).add(hexGrid.getHexes()[3][2].getLocation());
		numbers.get(10).add(hexGrid.getHexes()[4][4].getLocation());
		
		numbers.get(11).add(hexGrid.getHexes()[1][3].getLocation());
		numbers.get(11).add(hexGrid.getHexes()[3][3].getLocation());
		
		numbers.get(12).add(hexGrid.getHexes()[2][4].getLocation());
	}
	
	/**
	 * Place the hex numbers randomly
	 */
	public void placeRandomNumbers() {
		
		ArrayList<HexLocation> locations = getShuffledLocations();
		int[] possibleNumbers = new int[] {2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12};
		
		for (int i = 0; i < possibleNumbers.length; i++)
			numbers.get(possibleNumbers[i]).add(locations.get(i));
	}
	
	/**
	 * Get an shuffled arraylist of all the possible hex locations 
	 * @return
	 */
	public ArrayList<HexLocation> getShuffledLocations() {
		
		ArrayList<HexLocation> locations = new ArrayList<>();
		
		Hex[][] h = hexGrid.getHexes();
		locations.add(h[4][1].getLocation());
		locations.add(h[2][2].getLocation());
		locations.add(h[5][2].getLocation());
		locations.add(h[1][2].getLocation());
		locations.add(h[4][3].getLocation());
		locations.add(h[3][1].getLocation());
		locations.add(h[3][4].getLocation());
		locations.add(h[3][5].getLocation());
		locations.add(h[5][1].getLocation());
		locations.add(h[2][1].getLocation());
		locations.add(h[5][3].getLocation());
		locations.add(h[2][3].getLocation());
		locations.add(h[4][2].getLocation());
		locations.add(h[3][2].getLocation());
		locations.add(h[4][4].getLocation());
		locations.add(h[1][3].getLocation());
		locations.add(h[3][3].getLocation());
		locations.add(h[2][4].getLocation());
		
		Collections.shuffle(locations);
		return locations;
	}
}
