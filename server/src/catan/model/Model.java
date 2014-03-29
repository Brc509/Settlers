package catan.model;

import com.google.gson.JsonObject;

public interface Model {

	public void addChatEntry(int playerIndex, String content);

	public void addLogEntry(int playerIndex, String message);

	public DevCardList getDeck();

	public String getGamesListJSON(int id);

	public Hex getHex(HexLocation location);

	public JsonObject getModel();

	public String getModelJSON();

	public String getModelJSONForRevision(int revision);

	public String getName();

	public Number[] getNumbers(int number);

	public Player getPlayerByIndex(int playerIndex);

	public Player[] getPlayers();

	public HexLocation getRobberPosition();

	public TurnTracker getTurnTracker();

	public void initGame(String string, boolean b, boolean c, boolean d);

	public void initializeMap(boolean randomTokens, boolean randomHexes, boolean randomPorts);

	public void setHex(HexLocation location, Hex hex);

	public boolean setPlayer(int orderNumber, int userID, String name, String color);

	public void setTurnTracker(TurnTracker track);

	public void updatePlayer(int playerIndex, Player player);
}
