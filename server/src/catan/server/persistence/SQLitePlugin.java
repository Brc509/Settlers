package catan.server.persistence;

import java.util.List;
import java.util.Map;
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
	
	private final String db = "jdbc:sqlite:server/persistence/sqlite/catan.sqlite";

	public SQLitePlugin() {
		Connection conn = null;
	    Statement stmt1 = null;
	    Statement stmt2 = null;
	    Statement stmt3 = null;
	    Statement stmt4 = null;

		 	
	    try {
			
		 	Class.forName("org.sqlite.JDBC");
			
	        conn = getConnection();
	        stmt1 = conn.createStatement();
	        String gameTable = "CREATE TABLE Game("
	        				+"Id INTEGER NOT NULL, "
	        				+"GameModel BLOB NOT NULL, "
	        				+"OrigGameModel BLOB NOT NULL, "
	        				+"LastSavedGame INTEGER NOT NULL, "
	        				+"PRIMARY KEY(Id)"
	        				+");"; 
	        
	        stmt1.executeUpdate(gameTable);
	        stmt1.close();

	        stmt2 = conn.createStatement();
	        String userTable = "CREATE TABLE User("
    				+"Id INTEGER NOT NULL, "
    				+"Name varchar(255) NOT NULL, "
    				+"Password varchar(255) NOT NULL, "
    				+"PRIMARY KEY(Id)"
    				+");"; 
	        stmt2.executeUpdate(userTable);
	        stmt2.close();
	        
	        stmt3 = conn.createStatement();
	        String commandTable = "CREATE TABLE Command("
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
	        
	        conn.close();
	        
		 	} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e){
				e.printStackTrace();
			}
	    }
	
	@Override
	public void setCheckpointFrequency(int frequency) {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveUser(RegisteredUser user) {
        
        Connection connection = getConnection();

		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("INSERT INTO users (name,password,playerID) VALUES(?,?,?)");
		
			stmt.setString(1,user.getName());
	        stmt.setString(2,user.getPassword());
	        stmt.setInt(3,user.getPlayerID());
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
		// TODO Auto-generated method stub
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
