package catan.model;

import java.util.ArrayList;

import com.google.gson.JsonObject;

public interface Model {

	/**
	 * 
	 * @param playerIndex
	 * @param content
	 */
	public void addChatEntry(int playerIndex, String content);
	/**
	 * 
	 * @param playerIndex
	 * @param message
	 */
	public void addLogEntry(int playerIndex, String message);
	/**
	 * 
	 * @return
	 */
	public ResourceList getBank();
	/**
	 * 
	 * @param bank
	 */
	public void setBank(ResourceList bank);
	/**
	 * 
	 * @return
	 */
	public DevCardList getDeck();
	/**
	 * 
	 * @param deck
	 */
	public void setDeck(DevCardList deck);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public String getGamesListJSON(int id);
	/**
	 * 
	 * @param location
	 * @return
	 */
	public Hex getHex(HexLocation location);
	/**
	 * 
	 * @return
	 */
	public JsonObject getModel();
	/**
	 * 
	 * @return
	 */
	public String getModelJSON();
	/**
	 * 
	 * @param revision
	 * @return
	 */
	public String getModelJSONForRevision(int revision);
	/**
	 * 
	 * @return
	 */
	public String getName();
	/**
	 * 
	 * @param playerIndex
	 * @return
	 */
	public Player getPlayerByIndex(int playerIndex);
	/**
	 * 
	 * @param playerIndex
	 * @return
	 */
	public ResourceList getPlayerResources(int playerIndex);
	/**
	 * 
	 * @param playerIndex
	 * @return
	 */
	public DevCardList getPlayerNewDevCards(int playerIndex);	
	/**
	 * 
	 * @param playerIndex
	 * @return
	 */
	public DevCardList getPlayerOldDevCards(int playerIndex);
	/**
	 * 
	 * @return
	 */
	public Player[] getPlayers();
	/**
	 * 
	 * @return
	 */
	public HexLocation getRobberPosition();
	/**
	 * 
	 * @return
	 */
	public TurnTracker getTurnTracker();
	/**
	 * 
	 * @param string
	 * @param b
	 * @param c
	 * @param d
	 */
	public void initGame(String string, boolean b, boolean c, boolean d);
	/**
	 * 
	 * @param randomTokens
	 * @param randomHexes
	 * @param randomPorts
	 */
	public void initializeMap(boolean randomTokens, boolean randomHexes, boolean randomPorts);
	/**
	 * 
	 * @param number
	 * @return
	 */
	public ArrayList <Hex> rollNumber(int number);
	/**
	 * 
	 * @param hex
	 */
	public void setHex(Hex hex);
	/**
	 * 
	 * @param orderNumber
	 * @param userID
	 * @param name
	 * @param color
	 * @return
	 */
	public boolean setPlayer(int orderNumber, int userID, String name, String color);
	/**
	 * 
	 * @param playerIndex
	 * @param resources
	 */
	public void setPlayerResources(int playerIndex, ResourceList resources);
	/**
	 * 
	 * @param playerIndex
	 * @param newDevCards
	 */
	public void setPlayerNewDevCards(int playerIndex, DevCardList newDevCards);
	/**
	 * 
	 * @param playerIndex
	 * @param oldDevCards
	 */
	public void setPlayerOldDevCards(int playerIndex, DevCardList oldDevCards);
	/**
	 * 
	 * @param track
	 */
	public void setTurnTracker(TurnTracker track);
	/**
	 * 
	 * @param location
	 * @return
	 */
	public VertexLocation[] getAllHexesForVertex(VertexLocation location);
	/**
	 * 
	 * @param thisEdge
	 * @return
	 */
	public EdgeLocation getEquivalentEdge(EdgeLocation thisEdge);
}
