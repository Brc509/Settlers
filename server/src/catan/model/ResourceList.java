package catan.model;

public class ResourceList {

	private int brick;
	private int ore;
	private int sheep;
	private int wheat;
	private int wood;

	/**
	 * Initialize all resource values to 0
	 * Used for initializing players
	 */
	public ResourceList() {
		
		this.brick = 0;
		this.ore = 0;
		this.sheep = 0;
		this.wheat = 0;
		this.wood = 0;
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