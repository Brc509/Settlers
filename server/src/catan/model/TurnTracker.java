package catan.model;

public class TurnTracker {

	private int currentTurn;
	private String status;
	public TurnTracker() {
		
		currentTurn = 0;
		status = "FirstRound";
	}
	public int getCurrentTurn() {
		return currentTurn;
	}
	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
