package catan.model;

import com.google.gson.Gson;

public class ClientModel {

	private DevCardList deck;
	private ResourceList bank;
	private int biggestArmy;
	private int longestRoad;
	private MessageList chat;
	private MessageList log;
	private CatanMap map;
	private Player[] players;
	private TurnTracker turnTracker;
	private int winner;
	private transient int numPlayers;
	public String name;

	/**
	 * Create a new client model (for a new game)
	 */
	public ClientModel() {

		name = "";
		deck = new DevCardList(2, 5, 2, 14, 2);
		bank = new ResourceList(24, 24, 24, 24, 24);
		biggestArmy = -1;
		longestRoad = -1;
		chat = new MessageList();
		log = new MessageList();
		map = new CatanMap();

		players = new Player[4];
		for (int i = 0; i < players.length; i++)
			players[i] = new Player(i);

		turnTracker = new TurnTracker();
		winner = -1;
		numPlayers = 0;
	}

	public Player[] getPlayers() {
		return players;
	}

	/**
	 * Initialize the map for this game
	 * @param randomNumbers Whether to place random numbers (or use default numbers)
	 * @param randomHexes Whether to place random hexes (or use default hexes)
	 * @param randomPorts Whether to place random ports (or use default ports)
	 * @throws Exception
	 */
	public void initializeMap(boolean randomNumbers, boolean randomHexes, boolean randomPorts) {

		map.createMap(randomNumbers, randomHexes, randomPorts);
	}

	/**
	 * Adds a player in the next available index for this game
	 * 
	 * @param playerID
	 * @param color
	 */
	public void addPlayer(int playerID, String playerName, String color) {

		if (numPlayers >= 0 && numPlayers < 4) {

			players[numPlayers].setPlayerID(playerID);
			players[numPlayers].setColor(color);
			players[numPlayers].setName(playerName);
			numPlayers++;
		}
	}

	public void buildCity(int playerIndex, int x, int y, String direction, boolean free) {

		try {
			VertexLocation loc = new VertexLocation(x, y, direction);
			//TODO

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getModel() {

		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
