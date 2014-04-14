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

	public SQLitePlugin() {
		Connection conn = null;
	    Statement stmt = null;
		 	
	    try {
			
		 	Class.forName("org.sqlite.JDBC");
			
	        conn = DriverManager.getConnection("jdbc:sqlite:server/persistence/sqlite/catan.sqlite");
	        stmt = conn.createStatement();
	        
	        String sql = "CREATE TABLE Game("
	        				+"Id INTEGER NOT NULL, "
	        				+"GameModel BLOB NOT NULL, "
	        				+"OrigGameModel BLOB NOT NULL, "
	        				+"LastSavedGame INTEGER NOT NULL, "
	        				+"PRIMARY KEY(Id)"
	        				+");"; 
	        
	        stmt.executeUpdate(sql);
	        stmt.close();
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
		// TODO Auto-generated method stub
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
}
