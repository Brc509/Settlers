package catan.server.persistence;

import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;  
import java.sql.SQLException;
import java.sql.Statement; 

import catan.model.GameModel;
import catan.server.RegisteredUser;
import catan.server.command.Command;

public class SQLitePlugin implements PersistenceProvider {
	
	private static Connection conn;
	private final String db = "jdbc:sqlite:server/persistence/sqlite/catan.sqlite";
	private int checkpointFrequency;

	public SQLitePlugin() {

		Statement stmt1 = null;
	    Statement stmt2 = null;
	    Statement stmt3 = null;
	    Statement stmt4 = null;
	
	    try {
			
		 	Class.forName("org.sqlite.JDBC");

			File file = new File("server/persistence/sqlite/catan.sqlite");
			if(!file.exists()){
				file.getParentFile().mkdirs();
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
	        conn = getConnection();
	        if(file.length() == 0){

		        stmt1 = conn.createStatement();
		        String gameTable = "CREATE TABLE Games("
		        				+"Id INTEGER NOT NULL, "
		        				+"GameModel BLOB NOT NULL, "
		        				+"OrigGameModel BLOB NOT NULL, "
		        				+"LastSavedGame INTEGER NOT NULL, "
		        				+"PRIMARY KEY(Id)"
		        				+");"; 
		        
		        stmt1.executeUpdate(gameTable);
		        stmt1.close();
	
		        stmt2 = conn.createStatement();
		        String userTable = "CREATE TABLE Users("
	    				+"Id INTEGER NOT NULL, "
	    				+"Name varchar(255) NOT NULL, "
	    				+"Password varchar(255) NOT NULL, "
	    				+"PRIMARY KEY(Id)"
	    				+");"; 
		        
		        stmt2.executeUpdate(userTable);
		        stmt2.close();
		        
		        stmt3 = conn.createStatement();
		        String commandTable = "CREATE TABLE Commands("
	    				+"Id INTEGER NOT NULL, "
	    				+"Command BLOB NOT NULL, "
	    				+"GameID INTEGER NOT NULL, "
	    				+"PRIMARY KEY(Id), "
	    				+"FOREIGN KEY(GameId) REFERENCES Game(Id)"
	    				+");"; 
		        
		        stmt3.executeUpdate(commandTable);
		        stmt3.close();
		        
		        stmt4 = conn.createStatement();
		        String activityTable = "CREATE TABLE Activity("
	    				+"UserId INTEGER NOT NULL, "
	    				+"GameId INTEGER NOT NULL, "
	    				+"FOREIGN KEY(UserId) REFERENCES User(Id)"
	    				+"FOREIGN KEY(GameId) REFERENCES Game(Id)"
	    				+");"; 
		        
		        stmt4.executeUpdate(activityTable);
		        stmt4.close();
		        
			
	        }
		        
			 	} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e){
					e.printStackTrace();
				}
	    }
	
	@Override
	public void setCheckpointFrequency(int frequency) {
		checkpointFrequency = frequency;
		System.out.println("Checkpoint frequency set to " + checkpointFrequency + ".");
	}

	@Override
	public void saveUser(RegisteredUser user) {
        
        Connection connection = getConnection();

		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("INSERT INTO Users (Id,Name,Password) VALUES(?,?,?)");
			stmt.setInt(1,user.getPlayerID());
	        stmt.setString(2,user.getName());
	        stmt.setString(3,user.getPassword());
	        stmt.executeUpdate();
	        
	        connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<RegisteredUser> loadUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveBaseline(int gameID, GameModel model) {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveCommand(int gameID, Command command) {
		 
	}

	@Override
	public Map<Integer, GameModel> loadGames() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Connection getConnection () {
		try {
			return DriverManager.getConnection(db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
