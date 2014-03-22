package catan.model;

public class DevCardList {

	private int monopoly;
	private int monument;
	private int roadBuilding;
	private int soldier;
	private int yearOfPlenty;
	
	/**
	 * Basic constructor, initialize all variables to 0
	 * Used for players' lists
	 */
	public DevCardList() {
		
		this.monopoly = 0;
		this.monument = 0;
		this.roadBuilding = 0;
		this.soldier = 0;
		this.yearOfPlenty = 0;
	}
	
	/**
	 * Constructor to initialize resources to actual values
	 * Used for the ClientModel's deck
	 * @param monopoly
	 * @param monument
	 * @param roadBuilding
	 * @param soldier
	 * @param yearOfPlenty
	 */
	public DevCardList(int monopoly, int monument, int roadBuilding, int soldier, int yearOfPlenty) {
		
		this.monopoly = monopoly;
		this.monument = monument;
		this.roadBuilding = roadBuilding;
		this.soldier = soldier;
		this.yearOfPlenty = yearOfPlenty;
	}

	public int getMonopoly() {
		return monopoly;
	}

	public void setMonopoly(int monopoly) {
		this.monopoly = monopoly;
	}

	public int getMonument() {
		return monument;
	}

	public void setMonument(int monument) {
		this.monument = monument;
	}

	public int getRoadBuilding() {
		return roadBuilding;
	}

	public void setRoadBuilding(int roadBuilding) {
		this.roadBuilding = roadBuilding;
	}

	public int getSoldier() {
		return soldier;
	}

	public void setSoldier(int soldier) {
		this.soldier = soldier;
	}

	public int getYearOfPlenty() {
		return yearOfPlenty;
	}

	public void setYearOfPlenty(int yearOfPlenty) {
		this.yearOfPlenty = yearOfPlenty;
	}
}