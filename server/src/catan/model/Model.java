package catan.model;

import com.google.gson.JsonObject;

public interface Model {

	public boolean setPlayer(int orderNumber, int userID, String name, String color);

	public void initializeMap(boolean randomTokens, boolean randomHexes, boolean randomPorts);

	public JsonObject getModel();

	public String getModelJSON();

	public String getModelJSONForRevision(int revision);

	public String getGamesListJSON(int id);

	public String getName();

	public Player getPlayerByIndex(int playerIndex);

	public Number[] getNumbers(int number);

	public Player[] getPlayers();

	public void addLogEntry(int playerIndex, String message);

	public void addChatEntry(int playerIndex, String content);

	public HexLocation getRobberPosition();

	public Hex getHex(HexLocation location);
	
	public void setHex(HexLocation location, Hex hex);

	public TurnTracker getTurnTracker();
	
	public void setTurnTracker(TurnTracker track);

	public void initGame(String string, boolean b, boolean c, boolean d);
	
	public DevCardList getDeck();
	
	public void updatePlayer(int playerIndex, Player player);
}
