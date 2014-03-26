package catan.model;

public class HexGrid {

	Hex[][] hexes;
	int[] offsets;
	int radius = 4;
	int x0;
	int y0;

	public HexGrid() {
		
		// Default values for the default size HexGrid
		x0 = 3;
		y0 = 3;
		radius = 4;
		offsets = new int[] {3, 2, 1, 0, 0, 0, 0};
		int[] actualOffsets = new int[] {3, 2, 1, 0, 1, 2, 3};
		
		hexes = new Hex[7][];
		for (int i = 0; i < actualOffsets.length; i++)
			hexes[i] = new Hex[7 - actualOffsets[i]];
		
		// Initialize Hexes with locations
		for (int i = 0; i < hexes.length; i++)
			for (int j = 0; j < hexes[i].length; j++)
				hexes[i][j] = new Hex(j - x0 + offsets[i], i - y0);
	}
	
	/**
	 * Set the default types of lands for every Hex
	 */
	public void setDefaultTypes() {
		
		// row 1
		for (int i = 0; i < hexes[0].length; i++)
			hexes[0][i].setType("water");
		
		// row 2
		hexes[1][0].setType("water");
		hexes[1][1].setType("desert");
		hexes[1][2].setType("brick");
		hexes[1][3].setType("wood");
		hexes[1][4].setType("water");
		
		// row 3
		hexes[2][0].setType("water");
		hexes[2][1].setType("brick");
		hexes[2][2].setType("wood");
		hexes[2][3].setType("ore");
		hexes[2][4].setType("sheep");
		hexes[2][5].setType("water");
		
		// row 4
		hexes[3][0].setType("water");
		hexes[3][1].setType("ore");
		hexes[3][2].setType("sheep");
		hexes[3][3].setType("wheat");
		hexes[3][4].setType("brick");
		hexes[3][5].setType("wheat");
		hexes[3][6].setType("water");
		
		// row 5
		hexes[4][0].setType("water");
		hexes[4][1].setType("wheat");
		hexes[4][2].setType("sheep");
		hexes[4][3].setType("wood");
		hexes[4][4].setType("sheep");
		hexes[4][5].setType("water");
		
		// row 6
		hexes[5][0].setType("water");
		hexes[5][1].setType("wood");
		hexes[5][2].setType("ore");
		hexes[5][3].setType("wheat");
		hexes[5][4].setType("water");
		
		// row 7
		for (int i = 0; i < hexes[6].length; i++)
			hexes[6][i].setType("water");
	}
	
	public void setRandomTypes() {
		
		// TODO actually implement this
		setDefaultTypes();
	}
	
	/**
	 * Get the hex location of the only desert
	 * @return
	 */
	public HexLocation getDesertLocation() {
		
		for (int i = 0; i < hexes.length; i++)
			for (int j = 0; j < hexes[i].length; j++)
				if (hexes[i][j].getType().equals("desert"))
					return hexes[i][j].getLocation();
		System.out.println("ERROR: No desert!");
		return null;
	}
	
	public Hex[][] getHexes() {
		return hexes;
	}

	public void setHexes(Hex[][] hexes) {
		this.hexes = hexes;
	}

	public int[] getOffsets() {
		return offsets;
	}

	public void setOffsets(int[] offsets) {
		this.offsets = offsets;
	}

	public int getX0() {
		return x0;
	}

	public void setX0(int x0) {
		this.x0 = x0;
	}

	public int getY0() {
		return y0;
	}

	public void setY0(int y0) {
		this.y0 = y0;
	}
}
