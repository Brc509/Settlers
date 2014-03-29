package catan.model;

public interface Plugin {
	
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
