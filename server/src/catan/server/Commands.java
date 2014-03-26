package catan.server;

import java.util.ArrayList;

public class Commands {
	
	private static Commands commandsSingleton;
	  public ArrayList<String> commands;

	  private Commands(){
	      
		  commands = new ArrayList<String>();

	  }

	  public static Commands get(){
	      if(commandsSingleton == null){
	    	  commandsSingleton = new Commands();
	    	  
	      }
	      return commandsSingleton;
	  }

	  public ArrayList<String> getCommands(){
	      return commands;
	  }

	 
	  public void addCommand(String c){
		  commands.add(c);
	  }

}
