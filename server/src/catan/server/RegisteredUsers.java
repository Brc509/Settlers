package catan.server;

import java.util.ArrayList;

public class RegisteredUsers {
	
  private static RegisteredUsers regUserSingleton;
  public ArrayList<RegisteredUser> registeredUsers;

  private RegisteredUsers(){
      
      registeredUsers = new ArrayList<RegisteredUser>();
      registeredUsers.add(new RegisteredUser("Sam", "sam", 0));
      registeredUsers.add(new RegisteredUser("Brooke", "brooke", 1));
      registeredUsers.add(new RegisteredUser("Pete", "pete", 2));
      registeredUsers.add(new RegisteredUser("Mark", "mark", 3));


  }

  public static RegisteredUsers get(){
      if(regUserSingleton == null){
    	  regUserSingleton = new RegisteredUsers();
    	  
      }
      return regUserSingleton;
  }

  public ArrayList<RegisteredUser> getUsers(){
      return registeredUsers;
  }

 
  public void addUser(RegisteredUser rUser){
      registeredUsers.add(rUser);
  }

}
