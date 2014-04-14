package catan.server.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
	private boolean loading;

	public SQLitePlugin() {

		loading = false;
		
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
	    				+"Type varchar(255) NOT NULL, "
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
		Connection connection = getConnection();
        List<RegisteredUser> users = new ArrayList<RegisteredUser>();

        Statement stmt = null;
		try {	        
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS;" );

	        while(rs.next())
	        {
	        	String name = rs.getString("Name");
	        	String password = rs.getString("Password");
	        	int id = rs.getInt("Id");
	        	
	        	RegisteredUser nextUser = new RegisteredUser(name, password, id);
	        	users.add(nextUser);
	        }
	        
	        connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public void saveBaseline(int gameID, GameModel model) {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveCommand(int gameID, Command command) {
		Connection connection = getConnection();
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("INSERT INTO Commands(Command,GameId,Type) VALUES(?,?,?)");
	        stmt.setBytes(2, createBlob(command));
	        stmt.setInt(3,gameID);
	        stmt.setString(4, Command.class.getSimpleName());
	        stmt.executeUpdate();
	        
	        connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	private byte[] createBlob(Object object)  {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o;
		try {
			o = new ObjectOutputStream(b);
	        o.writeObject(object);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return b.toByteArray();
		
	}

	@Override
	public Map<Integer, GameModel> loadGames() {

		System.out.println("Loading games...");
		Map<Integer, GameModel> games = new HashMap<>();
		loading = true;		
		
		Connection connection = getConnection();
		Statement stmt = null;
		try {
			
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Games");
	
			// For every game
			while (rs.next()) {
				
				int gameID = rs.getInt("Id");
				rs.getString("");
				
				// Get all the commands for this game
				Statement commandSTMT = connection.createStatement();
				ResultSet commandRS = commandSTMT.executeQuery("SELECT * FROM COMMANDS WHERE GameID = " + gameID);
						
				while (commandRS.next()) {
					
					// TODO get the updated game model (using the last saved model + the needed commands to be executed)
				}
			}
		} 
		catch (SQLException e) {
			
			System.out.println("SQL Exception in loadGames!");
			e.printStackTrace();
		}
		
		loading = false;
		System.out.println("Done loading games.");
		return games;
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
