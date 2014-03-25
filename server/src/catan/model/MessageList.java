package catan.model;

import java.util.ArrayList;

public class MessageList {

	private ArrayList<MessageLine> lines;
	
	public MessageList() {
		
		lines = new ArrayList<>();
	}
	
	public ArrayList<MessageLine> getLines() {
		
		return lines;
	}
	
	public void addLine(MessageLine newLine) {
		
		lines.add(newLine);
	}
}