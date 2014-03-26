package catan.model;

public class Player {

	private int cities;
	private String color;
	private boolean discarded;
	private boolean largestArmy;
	private boolean longestRoad;
	private int monuments;
	private String name;
	private DevCardList newDevCards;
	private DevCardList oldDevCards;
	private int orderNumber;
	private boolean playedDevCard;
	private Integer playerID;
	private ResourceList resources;
	private int roads;
	private int settlements;
	private int soldiers;
	private int victoryPoints;
	private Integer MAX_GAME_POINTS;
	
	/**
	 * Constructs a new player
	 * @param order The index number of this player (0-3)
	 */
	public Player(){
		playerID = null;
	}
	public Player(int order, int id, String color, String name) {
		
		this.color = color;
		cities = 4;
		discarded = false;
		largestArmy = false;
		longestRoad = false;
		monuments = 0;
		this.name = name;
		newDevCards = new DevCardList();
		oldDevCards = new DevCardList();
		orderNumber = order;
		playedDevCard = false;
		playerID = -2;
		resources = new ResourceList();
		roads = 15;
		settlements = 5;
		soldiers = 0;
		victoryPoints = 0;
		MAX_GAME_POINTS = 10;
	}

	public Integer getMAX_GAME_POINTS() {
		return MAX_GAME_POINTS;
	}
	public void setMAX_GAME_POINTS(Integer mAX_GAME_POINTS) {
		MAX_GAME_POINTS = mAX_GAME_POINTS;
	}
	public boolean isDiscarded() {
		return discarded;
	}
	public boolean isLargestArmy() {
		return largestArmy;
	}
	public boolean isLongestRoad() {
		return longestRoad;
	}
	public int getCities() {
		return cities;
	}

	public void setCities(int cities) {
		this.cities = cities;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean hasDiscarded() {
		return discarded;
	}

	public void setDiscarded(boolean discarded) {
		this.discarded = discarded;
	}

	public boolean hasLargestArmy() {
		return largestArmy;
	}

	public void setLargestArmy(boolean largestArmy) {
		this.largestArmy = largestArmy;
	}

	public boolean hasLongestRoad() {
		return longestRoad;
	}

	public void setLongestRoad(boolean longestRoad) {
		this.longestRoad = longestRoad;
	}

	public int getMonuments() {
		return monuments;
	}

	public void setMonuments(int monuments) {
		this.monuments = monuments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DevCardList getNewDevCards() {
		return newDevCards;
	}

	public void setNewDevCards(DevCardList newDevCards) {
		this.newDevCards = newDevCards;
	}

	public DevCardList getOldDevCards() {
		return oldDevCards;
	}

	public void setOldDevCards(DevCardList oldDevCards) {
		this.oldDevCards = oldDevCards;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public boolean isPlayedDevCard() {
		return playedDevCard;
	}

	public void setPlayedDevCard(boolean playedDevCard) {
		this.playedDevCard = playedDevCard;
	}

	public Integer getPlayerID() {
		return playerID;
	}

	public void setPlayerID(Integer playerID) {
		this.playerID = playerID;
	}

	public ResourceList getResources() {
		return resources;
	}

	public void setResources(ResourceList resources) {
		this.resources = resources;
	}

	public int getRoads() {
		return roads;
	}

	public void setRoads(int roads) {
		this.roads = roads;
	}

	public int getSettlements() {
		return settlements;
	}

	public void setSettlements(int settlements) {
		this.settlements = settlements;
	}

	public int getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(int soldiers) {
		this.soldiers = soldiers;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}
}