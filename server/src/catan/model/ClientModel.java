package catan.model;

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
	
	/**
	 * Create a new client model (for a new game)
	 */
	public ClientModel() {
		
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
	}
	
	public void initializeDefaultMap() throws Exception {
		
		map.createDefaultMap();
	}
	
	public void addPlayer(int playerIndex, int playerID, String color) {
		
		players[playerIndex].setPlayerID(playerID);
		players[playerIndex].setColor(color);
	}
}