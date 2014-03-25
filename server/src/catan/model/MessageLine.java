package catan.model;

public class MessageLine {

	private String message;
	private String source;
	
	public MessageLine(String message, String source) {
		
		this.message = message;
		this.source = source;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}