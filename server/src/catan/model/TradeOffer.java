package catan.model;

public class TradeOffer {
	
	private int receiver = -1;
	private int playerIndex = -1;
	Offer offer = new Offer();
	String type = "";

	public TradeOffer() {
		// TODO Auto-generated constructor stub
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
