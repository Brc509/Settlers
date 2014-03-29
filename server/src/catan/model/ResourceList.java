package catan.model;

public class ResourceList {

	private int brick = 0;
	private int ore = 0;
	private int sheep = 0;
	private int wheat = 0;
	private int wood = 0;
	
	/**
	 * Initialize all resource values to 0
	 * Used for initializing players
	 */
	public ResourceList() {}

	public String  toString() {
		
		String returnString = "{\n";
		returnString += "\tbrick: " + brick + "\n";
		returnString += "\tore: " + ore + "\n";
		returnString += "\tsheep: " + sheep + "\n";
		returnString += "\twheat: " + wheat + "\n";
		returnString += "\twood: " + wood + "\n";
		returnString += "}";
		return returnString;
	}
	
	/**
	 * Initialize the resource list with pre-defined resource values
	 * Useful for creating the ClientModel's bank
	 * @param brick
	 * @param ore
	 * @param sheep
	 * @param wheat
	 * @param wood
	 */
	public ResourceList(int brick, int ore, int sheep, int wheat, int wood) {
		
		this.brick = brick;
		this.ore = ore;
		this.sheep = sheep;
		this.wheat = wheat;
		this.wood = wood;
	}
	
	public void testString () {
		System.out.println( brick + " " + ore + " " + sheep + " " + wheat + " " + wood + " ");
	}

	public int getBrick() {
		return brick;
	}

	public void setBrick(int brick) {
		this.brick = brick;
	}

	public int getOre() {
		return ore;
	}

	public void setOre(int ore) {
		this.ore = ore;
	}

	public int getSheep() {
		return sheep;
	}

	public void setSheep(int sheep) {
		this.sheep = sheep;
	}

	public int getWheat() {
		return wheat;
	}

	public void setWheat(int wheat) {
		this.wheat = wheat;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}
}