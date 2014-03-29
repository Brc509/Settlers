package catan.model;

import com.google.gson.JsonObject;

public interface Model {
	
	public boolean setPlayer(int orderNumber, int userID, String name, String color);
	public void initializeMap(boolean b, boolean c, boolean d);
	public JsonObject getModel();
	public String getGameInfo(int id);

	public String getName();

	public Player getPlaleryByIndex (int playerIndex);
	public Number[] getNumbers(int number);
	public Hex[] getHexes ();
	public Player[] getPlayers ();
	public void addLogEntry(int playerIndex, String message);
	public HexLocation getRobberPosition();
	public Hex getHex(int x, int y);
	public TurnTracker getTurnTracker();
	public void createChatEntry(String name, String content);
	public int getSoldier();


}
