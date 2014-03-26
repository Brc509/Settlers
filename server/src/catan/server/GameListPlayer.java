package catan.server;

public class GameListPlayer {

	private String color;
	private String name;
	private Integer id;


	public GameListPlayer(){
		id = null;
	}
	
	public GameListPlayer(String c, String n, int id){
		this.color = c;
		this.name = n;
		this.id = id;
	}
	
	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

}
