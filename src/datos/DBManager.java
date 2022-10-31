package datos;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import java.sql.ResultSet;
import negocio.Game;
import negocio.Merch;
import negocio.User;

public class DBManager {
	
	/*
	protected static final String DRIVER_NAME = "org.sqlite.JDBC";
	protected static final String DATABASE_FILE = "db/database.db";
	protected static final String CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_FILE;
	*/
	protected static Properties properties;
	protected static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	
	public DBManager() {		
		try {
			properties = new Properties();
			properties.load(new FileReader("conf/config.properties"));
			// SQLite diver charging
			Class.forName(properties.getProperty("DRIVER_NAME"));
		} catch (ClassNotFoundException ex) {
			System.err.println(String.format("* Error charging database driver: %s", ex.getMessage()));
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createDatabase() {
		// Connection is established and the Statement is obtained
		// If file does not exist, database is created
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			
	        String sql = "CREATE TABLE IF NOT EXISTS GAMES (\n"
	                   + " ID_GAME INTEGER PRIMARY KEY AUTOINCREMENT ON DELETE CASCADE,\n"
	                   + " NAME TEXT NOT NULL,\n"
	                   + " COMPANY TEXT NOT NULL,\n"
	                   + " PEGI TEXT NOT NULL,\n"
	                   + " GENRE1 TEXT NOT NULL,\n"
	                   + " GENRE2 TEXT NOT NULL,\n"
	                   + " PRICE DECIMAL(5, 2),\n"
	                   + " DESCRIPTION TEXT NOT NULL,\n"
	                   + " IMG_LINK TEXT NOT NULL\n"
	                   + ");"
	                   
	                   + "CREATE TABLE IF NOT EXISTS USERS (\n"
	                   + " ID_USER INTEGER PRIMARY KEY AUTOINCREMENT ON DELETE CASCADE,\n"
	                   + " USERNAME TEXT NOT NULL,\n"
	                   + " EMAIL TEXT NOT NULL,\n"
	                   + " PASSWORD TEXT NOT NULL,\n"
	                   + " COUNTRY TEXT NOT NULL,\n"
	                   + " LAST_TIME_PLAYED DATE NOT NULL,\n"
	                   + " TOTAL_TIME_PLAYED INT NOT NULL,\n"
	                   + ");"
	                   
					   + "CREATE TABLE IF NOT EXISTS PROPERTY_GAMES (\n"
					   + " INSTALLED INTEGER NOT NULL,\n"
					   + " TIME_PLAYED INT NOT NULL,\n"
					   + " ID_USER INTEGER FOREIGN KEY NOT NULL,\n"
					   + " ID_GAME INTEGER FOREIGN KEY NOT NULL,\n"
					   + ");"
					   
					   + "CREATE TABLE IF NOT EXISTS FRIENDS (\n"
					   + " ID_USER1 INTEGER FOREIGN KEY NOT NULL,\n"
					   + " ID_USER2 INTEGER FOREIGN KEY NOT NULL,\n"
					   + ");"
					   
					   + "CREATE TABLE IF NOT EXISTS MERCH (\n"
					   + " ID_MERCH INTEGER PRIMARY KEY AUTOINCREMENT ON DELETE CASCADE,\n"
					   + " NAME TEXT NOT NULL,\n"
					   + " TYPE TEXT NOT NULL,\n"
					   + " PRICE DECIMAL(5, 2),\n"
					   + ");"
					   
					   + "CREATE TABLE IF NOT EXISTS PROPERTY_MERCH (\n"
					   + " ID_USER INTEGER FOREIGN KEY NOT NULL,\n"
					   + " ID_MERCH INTEGER FOREIGN KEY NOT NULL,\n"
					   + ");";
	        
	        // Chart-creating sentence is executed
	        if (!stmt.execute(sql)) {
	        	System.out.println("- Successfuly created 6 charts (Games, Users, Property_Games, Friends, Merch, Property_Merch)");
	        }
		} catch (Exception ex) {
			System.err.println(String.format("* Error creating database: %s", ex.getMessage()));
			ex.printStackTrace();			
		}
	}
	
	public void deleteDatabase() {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			
	        String sql = "DROP TABLE IF EXISTS GAMES"
	        			+ "DROP TABLE IF EXISTS USERS"
	        			+ "DROP TABLE IF EXISTS PROPERTY_GAMES"
	        			+ "DROP TABLE IF EXISTS FRIENDS"
	        			+ "DROP TABLE IF EXISTS MERCH"
	        			+ "DROP TABLE IF EXISTS PROPERTY_MERCH";
			
	        // Chart-deleting sentence is executed
	        if (!stmt.execute(sql)) {
	        	System.out.println("- Successfuly deleted 6 charts (Games, Users, Property_Games, Friends, Merch, Property_Merch)");
	        }
		} catch (Exception ex) {
			System.err.println(String.format("* Error deleting database: %s", ex.getMessage()));
			ex.printStackTrace();			
		}
		
		try {
			// Database file is erased
			Files.delete(Paths.get(properties.getProperty("DATABASE_FILE")));
			System.out.println("- Database file successfuly deleted");
		} catch (Exception ex) {
			System.err.println(String.format("* Error deleting the database file: %s", ex.getMessage()));
			ex.printStackTrace();						
		}
	}
	
	///////// GAMES DATABASE /////////
	
	public void insertDataGames(Game... juegos ) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "INSERT INTO GAMES (NAME, COMPANY, PEGI, GENRE1, GENRE2, PRICE, DESCRIPTION, IMG_LINK) "
					+ "VALUES ('%s', '%s', '%s','%s', '%s', '%d','%s', '%s');";
			
			System.out.println("- Adding games...");
			
			// Info is added to the chart
			for (Game game : juegos) {
				if (1 == stmt.executeUpdate(String.format(sql, game.getName(),
						game.getCompany(), game.getPegi(), game.getGenre1(), 
						game.getGenre2(), game.getPrice(), game.getDescription(), 
						game.getImgLink()))) {					
					System.out.println(String.format(" - Game added: %s", game.toString()));
				} else {
					System.out.println(String.format(" - Game could not be added: %s", game.toString()));
				}
			}
		} catch (Exception ex) {
			System.err.println(String.format("* Error adding the game data: %s", ex.getMessage()));
			ex.printStackTrace();						
		}	
	}
	
	public List<Game> obtainDataGames() {
		List<Game> games = new ArrayList<>();
		
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM GAMES WHERE ID_GAME >= 0";
			
			// Sentence execution and ResultSet creation
			ResultSet rs = stmt.executeQuery(sql);
			Game game;
			
			// Game objects created
			while (rs.next()) {
				game = new Game();
				
				game.setId(rs.getInt("ID_GAME"));
				game.setName(rs.getString("NAME"));
				game.setCompany(rs.getString("COMPANY"));
				game.setPegi(rs.getString("PEGI"));
				game.setGenre1(rs.getString("GENRE1"));
				game.setGenre2(rs.getString("GENRE2"));
				game.setPrice(rs.getInt("PRICE"));
				game.setDescription(rs.getString("DESCRIPTION"));
				game.setImgLink(rs.getString("IMG_LINK"));
				
				// Game object addition
				games.add(game);
			}
			
			// ResultSet closing
			rs.close();
			
			System.out.println(String.format("- %d games retrieved...", games.size()));			
		} catch (Exception ex) {
			System.err.println(String.format("* Error obtaining data from database: %s", ex.getMessage()));
			ex.printStackTrace();						
		}		
		
		return games;
	}
	
	public void deleteDataGames(Game... games) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "DELETE FROM GAMES WHERE ID_GAME = '%d';";
			
			System.out.println("- Deleting games...");
			
			// Games are deleted from the table
			for (Game game : games) {
				if (1 == stmt.executeUpdate(String.format(sql, game.getId()))) {					
					System.out.println(String.format(" - Game deleted: %s", game.toString()));
				} else {
					System.out.println(String.format(" - Game could not be deleted: %s", game.toString()));
				}
			}
		} catch (Exception ex) {
			System.err.println(String.format("* Error deleting the game data: %s", ex.getMessage()));
			ex.printStackTrace();						
		}
	}
	
	public void updateGamePrice(Game game, Double price) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// Se ejecuta la sentencia de borrado de datos
			String sql = "UPDATE GAMES SET PRICE = '%s' WHERE ID_GAME = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, price, game.getId()));
			
			System.out.println(String.format("- Game price updated", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error updating game data: %s", ex.getMessage()));
			ex.printStackTrace();					
		}		
	}
	
	///////// USERS DATABASE /////////
	
	public void insertDataUsers(User... usuarios ) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "INSERT INTO USERS (USERNAME, EMAIL, PASSWORD, COUNTRY, LAST_TIME_PLAYED, TOTAL_TIME_PLAYED) "
					+ "VALUES ('%s', '%s', '%s','%s', '%t', '%d');";
			
			System.out.println("- Adding users...");
			
			// Info is added to the chart
			for (User user : usuarios) {
				if (1 == stmt.executeUpdate(String.format(sql, user.getUsername(),
						user.getEmail(), user.getPassword(), user.getCountry(), 
						user.getLastTimePlayed(), user.getTotalTimePlayed()))) {					
					System.out.println(String.format(" - User added: %s", user.toString()));
				} else {
					System.out.println(String.format(" - User could not be added: %s", user.toString()));
				}
			}
		} catch (Exception ex) {
			System.err.println(String.format("* Error adding the user data: %s", ex.getMessage()));
			ex.printStackTrace();						
		}	
	}
	
	public List<User> obtainDataUsers() {
		List<User> users = new ArrayList<>();
		
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM USERS WHERE ID_USER >= 0";
			
			// Sentence execution and ResultSet creation
			ResultSet rs = stmt.executeQuery(sql);
			User user;
			
			// User objects created
			while (rs.next()) {
				user = new User();
				
				user.setId(rs.getInt("ID_USER"));
				user.setUsername(rs.getString("USERNAME"));
				user.setEmail(rs.getString("EMAIL"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setCountry(rs.getString("COUNTRY"));
				user.setLastTimePlayed(rs.getDate("LAST_TIME_PLAYED"));
				user.setTotalTimePlayed(rs.getInt("TOTAL_TIME_PLAYED"));
				
				// User object addition
				users.add(user);
			}
			
			// ResultSet closing
			rs.close();
			
			System.out.println(String.format("- %d users retrieved...", users.size()));			
		} catch (Exception ex) {
			System.err.println(String.format("* Error obtaining data from database: %s", ex.getMessage()));
			ex.printStackTrace();						
		}		
		
		return users;
	}
	
	public void deleteDataUsers(User... users) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "DELETE FROM USERS WHERE ID_USER = '%d';";
			
			System.out.println("- Deleting users...");
			
			// Games are deleted from the table
			for (User user: users) {
				if (1 == stmt.executeUpdate(String.format(sql, user.getId()))) {					
					System.out.println(String.format(" - User deleted: %s", user.toString()));
				} else {
					System.out.println(String.format(" - User could not be deleted: %s", user.toString()));
				}
			}
		} catch (Exception ex) {
			System.err.println(String.format("* Error deleting the user data: %s", ex.getMessage()));
			ex.printStackTrace();						
		}
	}
	
	public void updateUserLastTimePlayed(User user, Date date) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "UPDATE USERS SET LAST_TIME_PLAYED = '%s' WHERE ID_USER = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, sdf.format(date), user.getId()));
			
			System.out.println(String.format("- User's last time played updated", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error updating user data: %s", ex.getMessage()));
			ex.printStackTrace();					
		}		
	}
	
	public void updateUserTotalTimePlayed(User user, Integer time) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "UPDATE USERS SET TOTAL_TIME_PLAYED = '%x' WHERE ID_USER = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, time, user.getId()));
			
			System.out.println(String.format("- User's total time played updated", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error updating user data: %s", ex.getMessage()));
			ex.printStackTrace();					
		}		
	}
	
	///////// PROPERTY_GAMES DATABASE /////////
	
	public void insertDataPropertyGames(User user, Game game) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "INSERT INTO PROPERTY_GAMES (INSTALLED, TIME_PLAYED, ID_USER, ID_GAME) "
					+ "VALUES ('%x', '%x', '%x','%x');";
			
			System.out.println("- Adding game to user's library...");
			
			// Info is added to the chart
			if (1 == stmt.executeUpdate(String.format(sql, 0, 0, user.getId(), game.getId()))) {					
				System.out.println(String.format(" - Game added to library: %s", user.getUsername(), game.getName()));
			} else {
				System.out.println(String.format(" - Game could not be added to library: %s", user.getUsername(), game.getName()));
			}
		} catch (Exception ex) {
			System.err.println(String.format("* Error adding the game/user data: %s", ex.getMessage()));
			ex.printStackTrace();				
		}
	}
	
	public Map<Integer, List<Integer>> obtainDataPropertyGames() {
		Map<Integer, List<Integer>> userGames = new HashMap<Integer, List<Integer>>();
		
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM PROPERTY_GAMES WHERE ID_USER >= 0";
			
			// Sentence execution and ResultSet creation
			ResultSet rs = stmt.executeQuery(sql);
			
			// Adding IDs to map
			while (rs.next()) {
				
				if (userGames.containsKey(rs.getInt("ID_USER"))) {
					userGames.get(rs.getInt("ID_USER")).add(rs.getInt("ID_GAME"));
				} else {
					userGames.put(rs.getInt("ID_USER"), new ArrayList<Integer>(rs.getInt("ID_GAME")));
				}
				
			}
			
			// ResultSet closing
			rs.close();
			
			System.out.println(String.format("- %d user's games retrieved...", userGames.size()));			
		} catch (Exception ex) {
			System.err.println(String.format("* Error obtaining data from database: %s", ex.getMessage()));
			ex.printStackTrace();						
		}		
		
		return userGames;
	}
	
	///////// FRIENDS DATABASE /////////
	
	public void insertDataFriends(User user1, User user2) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "INSERT INTO FRIENDS (ID_USER1, ID_USER2) "
					+ "VALUES ('%x', '%x');";
			
			System.out.println("- Adding friend to user's friendlist...");
			
			// Info is added to the chart
			if (1 == stmt.executeUpdate(String.format(sql, user1.getId(), user2.getId()))) {					
				System.out.println(String.format(" - Friend added correctly: %s", user1.getUsername(), user2.getUsername()));
			} else {
				System.out.println(String.format(" - Friend could not be added: %s", user1.getUsername(), user2.getUsername()));
			}
		} catch (Exception ex) {
			System.err.println(String.format("* Error adding the users data: %s", ex.getMessage()));
			ex.printStackTrace();				
		}
	}
	
	public Map<Integer, List<Integer>> obtainDataFriends() {
		Map<Integer, List<Integer>> friends = new HashMap<Integer, List<Integer>>();
		
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM FRIENDS WHERE ID_USER1 >= 0";
			
			// Sentence execution and ResultSet creation
			ResultSet rs = stmt.executeQuery(sql);
			
			// Adding IDs to map
			while (rs.next()) {
				
				if (friends.containsKey(rs.getInt("ID_USER1"))) {
					friends.get(rs.getInt("ID_USER1")).add(rs.getInt("ID_USER2"));
				} else {
					friends.put(rs.getInt("ID_USER1"), new ArrayList<Integer>(rs.getInt("ID_USER2")));
				}
				
			}
			
			// ResultSet closing
			rs.close();
			
			System.out.println(String.format("- %d user's friends retrieved...", friends.size()));	
		} catch (Exception ex) {
			System.err.println(String.format("* Error obtaining data from database: %s", ex.getMessage()));
			ex.printStackTrace();						
		}		
		
		return friends;
	}
	
	///////// MERCH DATABASE /////////
	
	public void insertDataMerch(Merch... merchlist) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "INSERT INTO MERCH (NAME, TYPE, PRICE) "
					+ "VALUES ('%s', '%s', '%d');";
			
			System.out.println("- Adding merch...");
			
			// Info is added to the chart
			for (Merch merch : merchlist) {
				if (1 == stmt.executeUpdate(String.format(sql, merch.getName(),
						merch.getType(), merch.getPrice()))) {					
					System.out.println(String.format(" - Merch added: %s", merch.toString()));
				} else {
					System.out.println(String.format(" - Merch could not be added: %s", merch.toString()));
				}
			}
		} catch (Exception ex) {
			System.err.println(String.format("* Error adding the merch data: %s", ex.getMessage()));
			ex.printStackTrace();						
		}	
	}
	
	public List<Merch> obtainDataMerch() {
		List<Merch> merchlist = new ArrayList<>();
		
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM MERCH WHERE ID_MERCH >= 0";
			
			// Sentence execution and ResultSet creation
			ResultSet rs = stmt.executeQuery(sql);
			Merch merch;
			
			// User objects created
			while (rs.next()) {
				merch = new Merch();
				
				merch.setId(rs.getInt("ID_MERCH"));
				merch.setName(rs.getString("NAME"));
				merch.setType(rs.getString("TYPE"));
				merch.setPrice(rs.getInt("PRICE"));
				
				// User object addition
				merchlist.add(merch);
			}
			
			// ResultSet closing
			rs.close();
			
			System.out.println(String.format("- %d merch products retrieved...", merchlist.size()));			
		} catch (Exception ex) {
			System.err.println(String.format("* Error obtaining data from database: %s", ex.getMessage()));
			ex.printStackTrace();						
		}		
		
		return merchlist;
	}
	
	public void deleteDataMerch(Merch... merchlist) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "DELETE FROM MERCH WHERE ID_MERCH = '%d';";
			
			System.out.println("- Deleting merch...");
			
			// Merch products are deleted from the table
			for (Merch merch: merchlist) {
				if (1 == stmt.executeUpdate(String.format(sql, merch.getId()))) {					
					System.out.println(String.format(" - Merch product deleted: %s", merch.toString()));
				} else {
					System.out.println(String.format(" - Merch product could not be deleted: %s", merch.toString()));
				}
			}
		} catch (Exception ex) {
			System.err.println(String.format("* Error deleting the merch data: %s", ex.getMessage()));
			ex.printStackTrace();						
		}
	}
	
	public void updateMerchPrice(Merch merch, Double price) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "UPDATE MERCH SET PRICE = '%d' WHERE ID_MERCH = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, price, merch.getId()));
			
			System.out.println(String.format("- Merch price updated", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error updating merch data: %s", ex.getMessage()));
			ex.printStackTrace();					
		}		
	}
	
	///////// PROPERTY_MERCH DATABASE /////////
	
	public void insertDataPropertyMerch(User user, Merch merch) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "INSERT INTO PROPERTY_MERCH (ID_USER, ID_MERCH) "
					+ "VALUES ('%x', '%x');";
			
			System.out.println("- Adding merch to user's library...");
			
			// Info is added to the chart
			if (1 == stmt.executeUpdate(String.format(sql, 0, 0, user.getId(), merch.getId()))) {					
				System.out.println(String.format(" - Merch added to library: %s", user.getUsername(), merch.getName()));
			} else {
				System.out.println(String.format(" - Merch could not be added to library: %s", user.getUsername(), merch.getName()));
			}
		} catch (Exception ex) {
			System.err.println(String.format("* Error adding the merch/user data: %s", ex.getMessage()));
			ex.printStackTrace();				
		}
	}
	
	public Map<Integer, List<Integer>> obtainDataPropertyMerch() {
		Map<Integer, List<Integer>> userMerch = new HashMap<Integer, List<Integer>>();
		
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM PROPERTY_MERCH WHERE ID_USER >= 0";
			
			// Sentence execution and ResultSet creation
			ResultSet rs = stmt.executeQuery(sql);
			
			// Adding IDs to map
			while (rs.next()) {
				
				if (userMerch.containsKey(rs.getInt("ID_USER"))) {
					userMerch.get(rs.getInt("ID_USER")).add(rs.getInt("ID_MERCH"));
				} else {
					userMerch.put(rs.getInt("ID_USER"), new ArrayList<Integer>(rs.getInt("ID_MERCH")));
				}
				
			}
			
			// ResultSet closing
			rs.close();
			
			System.out.println(String.format("- %d user's merch retrieved...", userMerch.size()));			
		} catch (Exception ex) {
			System.err.println(String.format("* Error obtaining data from database: %s", ex.getMessage()));
			ex.printStackTrace();						
		}		
		
		return userMerch;
	}
	
}
