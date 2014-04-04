package catan.model;

import java.util.ArrayList;

import com.google.gson.JsonObject;

public interface Model {

	/**
	 * Generate a new chat entry, using the given parameters, and add it to the game's model
	 * @param playerIndex Index (0-3) of the player sending the chat
	 * @param content String to be appended to the player's name
	 */
	public void addChatEntry(int playerIndex, String content);
	
	/**
	 * Generate a new log entry, using the given parameters, and add it to the game's model
	 * @param playerIndex Index (0-3) of the player that created the action to be logged
	 * @param message String to be appended to the player's name
	 */
	public void addLogEntry(int playerIndex, String message);
	
	/**
	 * Gathers the bank from memory and return it as a ResourceList (5 int values)
	 * @return A ResourceList containing bank values 
	 */
	public ResourceList getBank();
	
	/**
	 * Sets the game's bank value to match the parameter bank
	 * @param bank A ResourceList containing new bank values
	 */
	public void setBank(ResourceList bank);
	
	/**
	 * Gets the deck as a DevCardList (5 int values) from the model
	 * @return The deck from the model
	 */
	public DevCardList getDeck();
	
	/**
	 * Sets the deck to match the parameter DevCardList
	 * @param deck The new deck to set the model to match
	 */
	public void setDeck(DevCardList deck);
	
	/**
	 * Get a list of games to as a JSON-formatted string return to the client
	 * @param id The player ID of 
	 * @return String containing the list of games (formatted as JSON)
	 */
	public String getGamesListJSON(int id);
	
	/**
	 * Get a Hex object for the hex that matches the parameter location
	 * @param location A HexLocation object containing x and y coordinates of the Hex to get
	 * @return Hex object containing more information about the Hex
	 */
	public Hex getHex(HexLocation location);
	
	/**
	 * Get the entire model as a Gson JsonObject
	 * @return JsonObject containing the entire model
	 */
	public JsonObject getModel();
	
	/**
	 * Gets a String containing the JSON serialization of the model, with the revision number included
	 * @return String containing the JSON model
	 */
	public String getModelJSON();
	
	/**
	 * Gets a String containing the JSON serialization of the model, or 'true' 
	 * if the parameter revision matches the model's current revision number
	 * @param revision Number to compare to the model's revision number
	 * @return JSON of model if revisions do not match, 'true' if match 
	 */
	public String getModelJSONForRevision(int revision);
	
	/**
	 * Gets this model's game name
	 * @return String containing the name
	 */
	public String getName();
	
	/**
	 * Get a player object that matches the parameter index for this game
	 * @param playerIndex The index (0-3) of the player to retrieve
	 * @return A Player object which contains all necessary information about the player
	 */
	public Player getPlayerByIndex(int playerIndex);
	
	/**
	 * Get a ResourceList for the given playerIndex 
	 * @param playerIndex The index (0-3) of the player to retrieve resources from
	 * @return ResourceList containing the 5 int values representing the player's resources
	 */
	public ResourceList getPlayerResources(int playerIndex);
	
	/**
	 * Get the DevCardList of new cards for the player (cards that cannot be played yet)
	 * @param playerIndex The index (0-3) of the player to retrieve the DevCardList from
	 * @return DevCardList containing the 5 int values representing the player's new dev cards
	 */
	public DevCardList getPlayerNewDevCards(int playerIndex);	
	
	/**
	 * Get the DevCardList of old cards for the player (cards drawn in previous turns)
	 * @param playerIndex The index (0-3) of the player to retrieve the DevCardList from
	 * @return DevCardList containing the 5 int values representing the player's old dev cards
	 */
	public DevCardList getPlayerOldDevCards(int playerIndex);
	
	/**
	 * Get an array (of size 4) containing all four Player objects corresponding to this game
	 * @return Player[] for the four players
	 */
	public Player[] getPlayers();
	
	/**
	 * Get a HexLocation (x, y) containing the robber's current position in this game
	 * @return HexLocation containing the robber's location
	 */
	public HexLocation getRobberPosition();
	
	/**
	 * Get the current turn tracker as a TurnTracker object
	 * @return TurnTracker (which contains the currentTurn index and a status)
	 */
	public TurnTracker getTurnTracker();
	
	/**
	 * Set the game's name and shuffle desired areas from the default map
	 * @param string The name to assign to this game
	 * @param randomTokens Boolean to determine whether to shuffle the tokens (numbers on top on hexes)
	 * @param randomHexes Boolean to determine whether to shuffle the hexes (resource value for each hex)
	 * @param randomPorts Boolean to determine whether to shuffle the ports (9 resource/ratio pairs for 9 fixed hexes)
	 */
	public void initGame(String string, boolean randomTokens, boolean randomHexes, boolean randomPorts);
	
	/**
	 * Gets a list of hexes that contain the number parameter
	 * @param number The number rolled that we need hexes for
	 * @return ArrayList<Hex> containing all the necessary hexes
	 */
	public ArrayList <Hex> rollNumber(int number);
	
	/**
	 * Set the edges and vertexes for this parameter hex (at its location)
	 * @param hex Hex object containing location and Edge/Vertex values
	 */
	public void setHex(Hex hex);
	
	/**
	 * Set the game's player's id, name, and color to these parameters at the given orderNumber
	 * @param orderNumber Index (must be 0-3) of the position to set the player
	 * @param userID Player's ID
	 * @param name Player's name
	 * @param color Player's selected color
	 * @return
	 */
	public boolean setPlayer(int orderNumber, int userID, String name, String color);
	
	/**
	 * Set the resource values for the player at this index
	 * @param playerIndex Index (0-3) of the player to set
	 * @param resources ResourceList containing the 5 resource values for this player
	 */
	public void setPlayerResources(int playerIndex, ResourceList resources);
	
	/**
	 * Set the New Devcard values for the player at this index
	 * @param playerIndex Index (0-3) of the player to set
	 * @param newDevCards DevCardList containing the 5 new dev card values for this player
	 */
	public void setPlayerNewDevCards(int playerIndex, DevCardList newDevCards);
	
	/**
	 * Set the Old Devcard values for the player at this index
	 * @param playerIndex Index (0-3) of the player to set
	 * @param oldDevCards DevCardList containing the 5 old dev card values for this player
	 */
	public void setPlayerOldDevCards(int playerIndex, DevCardList oldDevCards);
	
	/**
	 * Set the game's turntracker values to match the parameter TurnTracker
	 * @param track TurnTracker object containing the updated values
	 */
	public void setTurnTracker(TurnTracker track);
	
	/**
	 * Get all hexes that match a given vertex (there should be 3 total)
	 * @param location VertexLocation object to get location from
	 * @return An array of 3 vertex locations (including the parameter location)
	 */
	public VertexLocation[] getAllHexesForVertex(VertexLocation location);
	
	/**
	 * Get the Equivalent reference to the edge at this same location (to update both hexes with the edge value)
	 * @param thisEdge EdgeLocation to get the other edge from
	 * @return EdgeLocation containing the reference to the other edge
	 */
	public EdgeLocation getEquivalentEdge(EdgeLocation thisEdge);
}
