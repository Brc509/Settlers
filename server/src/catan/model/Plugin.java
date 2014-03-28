package catan.model;

public interface Plugin {
	
	public Player getPlaleryByIndex (int playerIndex);
	public Number[] getNumbers(int number);
	public Hex[] getHexes ();
	public Player[] getPlayers ();

}
