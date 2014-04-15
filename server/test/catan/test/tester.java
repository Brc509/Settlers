package catan.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;

import catan.model.GameModel;
import catan.server.RegisteredUser;
import catan.server.persistence.JsonPlugin;
import catan.server.persistence.SQLitePlugin;


public class tester {
	
	@Test
	public void SQLiteTest1() {
		deleteFiles();
		SQLitePlugin test = new SQLitePlugin();
		
		RegisteredUser user = new RegisteredUser("Sam", "sam", 0);
		test.saveUser(user);
		RegisteredUser user2 = new RegisteredUser("Brooke", "brooke", 1);
		test.saveUser(user2);
		RegisteredUser user3 = new RegisteredUser("Pete", "pete", 2);
		test.saveUser(user3);
		RegisteredUser user4 = new RegisteredUser("Mark", "mark", 3);
		test.saveUser(user4);
		
		List<RegisteredUser> users = test.loadUsers();
		
		assertEquals("Should be Sam", "Sam", users.get(0).getName());
		assertEquals("Should be sam", "sam", users.get(0).getPassword());
		assertEquals("Should be 0", 0, users.get(0).getPlayerID());
		assertEquals("Should be Brooke", "Brooke", users.get(1).getName());
		assertEquals("Should be brooke", "brooke", users.get(1).getPassword());
		assertEquals("Should be 1", 1, users.get(1).getPlayerID());
		assertEquals("Should be Pete", "Pete", users.get(2).getName());
		assertEquals("Should be pete", "pete", users.get(2).getPassword());
		assertEquals("Should be 2", 2, users.get(2).getPlayerID());
		assertEquals("Should be Mark", "Mark", users.get(3).getName());
		assertEquals("Should be mark", "mark", users.get(3).getPassword());
		assertEquals("Should be 3", 3, users.get(3).getPlayerID());
	}
	
	@Test
	public void SQLiteTest2() {
		deleteFiles();

		SQLitePlugin test = new SQLitePlugin();
		
		GameModel testModel = new GameModel();
		Gson gson = new Gson();
		String originalModel = gson.toJson(testModel);
		System.out.println(originalModel);
		
		test.saveBaseline(1, testModel);
		
		Map<Integer, GameModel> testMap = test.loadGames();
		String retrievedModel = gson.toJson(testMap.get(1));
		System.out.println(retrievedModel);
		
		assertTrue("Testing if GameModel saved and retrieved", originalModel.equals(retrievedModel));
	}

	@Test
	public void JsonPluginTest1() {
		deleteFiles();

		JsonPlugin test = new JsonPlugin();
		
		RegisteredUser user = new RegisteredUser("Sam", "sam", 0);
		test.saveUser(user);
		RegisteredUser user2 = new RegisteredUser("Brooke", "brooke", 1);
		test.saveUser(user2);
		RegisteredUser user3 = new RegisteredUser("Pete", "pete", 2);
		test.saveUser(user3);
		RegisteredUser user4 = new RegisteredUser("Mark", "mark", 3);
		test.saveUser(user4);
		
		List<RegisteredUser> users = test.loadUsers();
		
		assertEquals("Should be Sam", "Sam", users.get(0).getName());
		assertEquals("Should be sam", "sam", users.get(0).getPassword());
		assertEquals("Should be 0", 0, users.get(0).getPlayerID());
		assertEquals("Should be Brooke", "Brooke", users.get(1).getName());
		assertEquals("Should be brooke", "brooke", users.get(1).getPassword());
		assertEquals("Should be 1", 1, users.get(1).getPlayerID());
		assertEquals("Should be Pete", "Pete", users.get(2).getName());
		assertEquals("Should be pete", "pete", users.get(2).getPassword());
		assertEquals("Should be 2", 2, users.get(2).getPlayerID());
		assertEquals("Should be Mark", "Mark", users.get(3).getName());
		assertEquals("Should be mark", "mark", users.get(3).getPassword());
		assertEquals("Should be 3", 3, users.get(3).getPlayerID());
	}
	
	@Test
	public void JsonPluginTest2() {
		deleteFiles();
		
		JsonPlugin test = new JsonPlugin();
		
		GameModel testModel = new GameModel();
		Gson gson = new Gson();
		String originalModel = gson.toJson(testModel);
		System.out.println(originalModel);
		
		test.saveBaseline(0, testModel);
		
		Map<Integer, GameModel> testMap = test.loadGames();
		String retrievedModel = gson.toJson(testMap.get(0));
		System.out.println(retrievedModel);
		
		assertTrue("Testing if GameModel saved and retrieved", originalModel.equals(retrievedModel));
	}
	
	public void deleteFiles(){
		File file = new File("server/persistence/json");
		if(file.exists()){
			String[]entries = file.list();
			for(String s: entries){
			    File currentFile = new File(file.getPath(),s);
			    currentFile.delete();
			}
			file.delete();
		}
		
		File sql = new File("server/persistence/sqlite");
		if(sql.exists()){
			String[]sqlFiles = sql.list();
			for(String s: sqlFiles){
			    File currentFile = new File(sql.getPath(),s);
			    currentFile.delete();
			}
			sql.delete();
		}

	}
}
