package catan.server;

import java.util.HashMap;
import java.util.Map;

import catan.model.ClientModel;

public class Games {
	
	private static Games gamesSingleton;
	  public Map<Integer, ClientModel> games;

	  private Games(){
	      
		  games = new HashMap<Integer, ClientModel>();

	  }

	  public static Games get(){
	      if(gamesSingleton == null){
	    	  gamesSingleton = new Games();
	    	  
	      }
	      return gamesSingleton;
	  }

	  public Map<Integer, ClientModel> getGames(){
	      return games;
	  }

	 
	  public void addGame(ClientModel cm){
		  games.put(games.size(), cm);
	  }

}
