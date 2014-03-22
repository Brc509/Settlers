package catan.model;

public class ClientModel {

	private DevCardList deck;
	private ResourceList bank;
	private int biggestArmy;
	
	/**
	 * Create a new client model (for a new game)
	 */
	public ClientModel() {
		
		deck = new DevCardList();
		bank = new ResourceList();
		biggestArmy = -1;
	}
}