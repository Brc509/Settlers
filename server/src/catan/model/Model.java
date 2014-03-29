package catan.model;

import com.google.gson.JsonObject;

public interface Model {

	public boolean setPlayer(int orderNumber, int userID, String name, String color);

	public void initializeMap(boolean randomTokens, boolean randomHexes, boolean randomPorts);

	public JsonObject getModel();

	public String getModelJSON();

	public String getModelJSONForRevision(int revision);

	public String getGameInfo(int id);

	public String getName();

	public Player getPlayerByIndex(int playerIndex);

	public Number[] getNumbers(int number);

	public Hex[][] getHexes();

	public Player[] getPlayers();

	public void addLogEntry(int playerIndex, String message);

	public void addChatEntry(int playerIndex, String content);

	public HexLocation getRobberPosition();

	public Hex getHex(int x, int y);

	public TurnTracker getTurnTracker();

	public int getSoldier();

	public void initGame(String string, boolean b, boolean c, boolean d);
}
