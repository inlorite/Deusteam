package datos;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
	
	/** Construye una nueva ventana gráfica con fondo blanco y la visualiza en el centro de la pantalla
	 * @param anchura	Anchura en píxels (valor positivo) de la zona de pintado
	 * @param altura	Altura en píxels (valor positivo) de la zona de pintado
	 * @param titulo	Título de la ventana
	 */
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
	
	/** Creates a new Database that contains the following charts:
	 * GAMES: contains Game class objects @see Game
	 * USERS: contains User class objects @see User
	 * PROPERTY_GAMES: contains the relationship between a user and the games owned,
	 * 		as well as INSTALLED (1 if true, 0 if false) and TIME_PLAYED (time played 
	 * 		by the user to this game, in miliseconds) attributes
	 * FRIENDS: contains the relationship between two User class objects. Has the id of
	 * 		both users
	 * MERCH: contains Merch class objects @see Merch
	 * PROPERTY_MERCH: contains the relationship between a user and the merch owned
	 */
	public void createDatabase() {
		// Connection is established and the Statement is obtained
		// If file does not exist, database is created
		ArrayList<String> tables = new ArrayList<>();
		
		tables.add("CREATE TABLE IF NOT EXISTS GAMES (\n"
	                   + " ID_GAME INTEGER PRIMARY KEY AUTOINCREMENT,\n"
	                   + " NAME TEXT NOT NULL,\n"
	                   + " COMPANY TEXT NOT NULL,\n"
	                   + " PEGI TEXT NOT NULL,\n"
	                   + " GENRE1 TEXT NOT NULL,\n"
	                   + " GENRE2 TEXT NOT NULL,\n"
	                   + " PRICE DECIMAL(5, 2),\n"
	                   + " DESCRIPTION TEXT NOT NULL,\n"
	                   + " IMG_LINK TEXT NOT NULL\n"
	                   + ");");
		
		tables.add("CREATE TABLE IF NOT EXISTS USERS (\n"
	                   + " ID_USER INTEGER PRIMARY KEY AUTOINCREMENT,\n"
	                   + " USERNAME TEXT NOT NULL,\n"
	                   + " EMAIL TEXT NOT NULL,\n"
	                   + " PASSWORD TEXT NOT NULL,\n"
	                   + " COUNTRY TEXT NOT NULL,\n"
	                   + " LAST_TIME_PLAYED TEXT NOT NULL,\n"
	                   + " TOTAL_TIME_PLAYED INTEGER NOT NULL\n"
	                   + ");");
		
		tables.add("CREATE TABLE IF NOT EXISTS PROPERTY_GAMES (\n"
					   + " INSTALLED INTEGER NOT NULL,\n"
					   + " TIME_PLAYED INTEGER NOT NULL,\n"
					   + " ID_USER INTEGER NOT NULL,\n"
					   + " ID_GAME INTEGER NOT NULL,\n"
					   + " FOREIGN KEY(ID_USER) REFERENCES USERS(ID_USER) ON DELETE CASCADE,\n"
					   + " FOREIGN KEY(ID_GAME) REFERENCES GAMES(ID_GAME) ON DELETE CASCADE\n"
					   + ");");
		
		tables.add("CREATE TABLE IF NOT EXISTS FRIENDS (\n"
					   + " ID_USER1 INTEGER NOT NULL,\n"
					   + " ID_USER2 INTEGER NOT NULL,\n"
					   + " FOREIGN KEY(ID_USER1) REFERENCES USERS(ID_USER) ON DELETE CASCADE,\n"
					   + " FOREIGN KEY(ID_USER2) REFERENCES USERS(ID_USER) ON DELETE CASCADE\n"
					   + ");");
		
		tables.add("CREATE TABLE IF NOT EXISTS MERCH (\n"
					   + " ID_MERCH INTEGER PRIMARY KEY AUTOINCREMENT,\n"
					   + " NAME TEXT NOT NULL,\n"
					   + " TYPE TEXT NOT NULL,\n"
					   + " PRICE DECIMAL(5, 2)\n"
					   + ");");
		
		tables.add("CREATE TABLE IF NOT EXISTS PROPERTY_MERCH (\n"
					   + " ID_USER INTEGER NOT NULL,\n"
					   + " ID_MERCH INTEGER NOT NULL,\n"
					   + " FOREIGN KEY(ID_USER) REFERENCES USERS(ID_USER) ON DELETE CASCADE,\n"
					   + " FOREIGN KEY(ID_MERCH) REFERENCES MERCH(ID_MERCH) ON DELETE CASCADE\n"
					   + ");");
			
		for (String table : tables) {
			createTable(table);
		}
	}
	
	public void createTable(String sql) {
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
			 Statement stmt = con.createStatement()) {
		        
		        // Chart-creating sentence is executed
		        if (!stmt.execute(sql)) {
		        	System.out.println("- Table successfully created.");
		        }
			} catch (Exception ex) {
				System.err.println(String.format("* Error creating table: %s", ex.getMessage()));
				ex.printStackTrace();			
			}
	}
	
	/** Deletes all charts for this Database
	 */
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
	
	/** Inserts an array of games in the GAMES chart
	 * @param games		Array of Game class objects
	 */
	public void insertDataGames(Game... games ) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "INSERT INTO GAMES (NAME, COMPANY, PEGI, GENRE1, GENRE2, PRICE, DESCRIPTION, IMG_LINK) "
					+ "VALUES ('%s', '%s', '%s','%s', '%s', '%d','%s', '%s');";
			
			System.out.println("- Adding games...");
			
			// Info is added to the chart
			for (Game game : games) {
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
	
	/** Obtains the list of games from the GAMES chart
	 * @return	ArrayList of Game class objects
	 */
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
	
	/** Deletes games from the GAMES chart that are included in the array
	 * @param games		Array of Game class objects
	 */
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
	
	/** Updates the price for a set game in the GAMES chart
	 * @param game		Game class object
	 * @param price		Double of the price
	 */
	public void updateGamePrice(Game game, Double price) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// Statement execution
			String sql = "UPDATE GAMES SET PRICE = '%s' WHERE ID_GAME = %d;";
			
			int result = stmt.executeUpdate(String.format(sql, price, game.getId()));
			
			System.out.println(String.format("- Game price updated", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error updating game data: %s", ex.getMessage()));
			ex.printStackTrace();					
		}		
	}
	
	///////// USERS DATABASE /////////
	
	/** Inserts an array of users in the USERS chart
	 * @param users		Array of User class objects
	 */
	public void insertDataUsers(User... usuarios ) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "INSERT INTO USERS (USERNAME, EMAIL, PASSWORD, COUNTRY, LAST_TIME_PLAYED, TOTAL_TIME_PLAYED) "
					+ "VALUES ('%s', '%s', '%s','%s', '%s', '%d');";
			
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
	
	/** Obtains the list of users from the USERS chart
	 * @return	ArrayList of User class objects
	 */
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
				user.setLastTimePlayed(rs.getString("LAST_TIME_PLAYED"));
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
	
	/** Deletes users from the USERS chart that are included in the array
	 * @param users		Array of User class objects
	 */
	public void deleteDataUsers(User... users) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "DELETE FROM USERS WHERE ID_USER = '%d';";
			
			System.out.println("- Deleting users...");
			
			// Users are deleted from the table
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
	
	/** Updates the user's last time played date
	 * @param user		User class object
	 * @param date		Date class object
	 */
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
	
	/** Increments the user's total time played by a set amount of time.
	 * 	Automatically gets called by @see DBManager#incrementPropertyGamesTimePLayed(Integer, Integer)
	 * @param id_user		Integer of the user id
	 * @param time			Integer of the time  to be increased in miliseconds
	 */
	public void incrementUserTotalTimePlayed(Integer id_user, Integer time) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "UPDATE USERS SET TOTAL_TIME_PLAYED = '%x' WHERE ID_USER = %d;";
			
			String sql2 = "SELECT * FROM USERS WHERE ID_USER = " + id_user;
			
			// ResultSet to get previous time and Sentence execution
			ResultSet rs = stmt.executeQuery(sql2);
			Integer previousTime = rs.getInt("TOTAL_TIME_PLAYED");
			
			int result = stmt.executeUpdate(String.format(sql, time + previousTime, id_user));
			
			System.out.println(String.format("- User's total time played updated", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error updating user data: %s", ex.getMessage()));
			ex.printStackTrace();					
		}		
	}
	
	///////// PROPERTY_GAMES DATABASE /////////
	
	/** Adds a relation of property between a user and a game in the PROPERTY_GAME chart
	 * @param user			User class object
	 * @param game			Game class object
	 */
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
	
	/** Obtains a map from PROPERTY_GAMES chart where the keys are
	 * 	users' ids and values are a list of each user's games' ids
	 * @return userGames	HashMap<Integer, ArrayList<Integer>>
	 */
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
	
	/** Updates the installed attribute for a set user and game relationship
	 * @param id_user		Integer of the user id
	 * @param id_game		Integer of the game id
	 * @param installed		Integer, 1 if true, 0 if false
	 */
	public void updatePropertyGamesInstalled(Integer id_user, Integer id_game, Integer installed) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "UPDATE PROPERTY_GAMES SET INSTALLED = '%x' WHERE ID_USER = %d AND ID_GAME = %d;";
			
			// Installed if true = 1, if false = 0
			int result = stmt.executeUpdate(String.format(sql, installed, id_user, id_game));
			
			System.out.println(String.format("- Property game installation updated", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error updating property game installation: %s", ex.getMessage()));
			ex.printStackTrace();					
		}		
	}
	
	/** Increments the user's time played in a set game by a set amount of time.
	 * 	Automatically calls @see DBManager#incrementUserTotalTimePLayed(Integer, Integer)
	 * @param id_user		Integer of the user id
	 * @param id_game		Integer of the game id
	 * @param time			Integer of the time  to be increased in miliseconds
	 */
	public void incrementPropertyGamesTimePlayed(Integer id_user, Integer id_game, Integer timePlayed) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "UPDATE PROPERTY_GAMES SET TIME_PLAYED = '%x' WHERE ID_USER = %d AND ID_GAME = %d;";
			
			String sql2 = "SELECT * FROM PROPERTY_GAMES WHERE ID_USER = " + id_user;
			
			// ResultSet to get previous time and Sentence execution
			ResultSet rs = stmt.executeQuery(sql2);
			Integer previousTime = rs.getInt("TIME_PLAYED");
			
			int result = stmt.executeUpdate(String.format(sql, previousTime + timePlayed, id_user, id_game));
			
			// Update of the user's individual data
			incrementUserTotalTimePlayed(id_user, timePlayed);
			
			System.out.println(String.format("- Property game time played updated", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error updating property game time played: %s", ex.getMessage()));
			ex.printStackTrace();					
		}		
	}
	
	///////// FRIENDS DATABASE /////////
	
	/** Adds a relation of friendship between two users in the FRIENDS chart
	 * @param user			User class object
	 * @param game			Game class object
	 */
	public void insertDataFriends(User user1, User user2) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "INSERT INTO FRIENDS (ID_USER1, ID_USER2) "
					+ "VALUES ('%x', '%x');";
			
			System.out.println("- Adding friend to user's friendlist...");
			
			// Info is added to the chart
			if (1 == stmt.executeUpdate(String.format(sql, user1.getId(), user2.getId())) && 1 == stmt.executeUpdate(String.format(sql, user2.getId(), user1.getId()))) {					
				System.out.println(String.format(" - Friends added correctly: %s", user1.getUsername(), user2.getUsername()));
			} else {
				System.out.println(String.format(" - Friends could not be added: %s", user1.getUsername(), user2.getUsername()));
			}
		} catch (Exception ex) {
			System.err.println(String.format("* Error adding the users data: %s", ex.getMessage()));
			ex.printStackTrace();				
		}
	}
	
	/** Obtains a map from FRIENDS chart where the keys are
	 * 	users' ids and values are a list of each user's friends' ids
	 * @return friends		HashMap<Integer, ArrayList<Integer>>
	 */
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
	
	/** Deletes the relation of friendship between two users
	 * @param user1			Integer of the first user
	 * @param user2			Integer of the second user
	 */
	public void deleteDataFriends(Integer user1, Integer user2) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "DELETE FROM FRIENDS WHERE ID_USER1 = '%x' AND ID_USER2 = '%x';";
			
			System.out.println("- Deleting friend...");
			
			// Friend is deleted from the table
			if (1 == stmt.executeUpdate(String.format(sql, user1, user2)) && 1 == stmt.executeUpdate(String.format(sql, user2, user1))) {					
				System.out.println(String.format(" - Friend deleted: %s", user1, user2));
			} else {
				System.out.println(String.format(" - Friend could not be deleted: %s", user1, user2));
			}
		} catch (Exception ex) {
			System.err.println(String.format("* Error deleting the friend data: %s", ex.getMessage()));
			ex.printStackTrace();						
		}
	}
	
	///////// MERCH DATABASE /////////
	
	/** Inserts an array of merch in the MERCH chart
	 * @param merchlist		Array of Merch class objects
	 */
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
	
	/** Obtains the list of merch from the MERCH chart
	 * @return	ArrayList of Merch class objects
	 */
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
	
	/** Deletes merch from the Merch chart that are included in the array
	 * @param merchlist		Array of Merch class objects
	 */
	public void deleteDataMerch(Merch... merchlist) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "DELETE FROM MERCH WHERE ID_MERCH = '%x';";
			
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
	
	/** Updates the price for a set merch in the MERCH chart
	 * @param merch		Merch class object
	 * @param price		Double of the price
	 */
	public void updateMerchPrice(Merch merch, Double price) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "UPDATE MERCH SET PRICE = '%d' WHERE ID_MERCH = %x;";
			
			int result = stmt.executeUpdate(String.format(sql, price, merch.getId()));
			
			System.out.println(String.format("- Merch price updated", result));
		} catch (Exception ex) {
			System.err.println(String.format("* Error updating merch data: %s", ex.getMessage()));
			ex.printStackTrace();					
		}		
	}
	
	///////// PROPERTY_MERCH DATABASE /////////
	
	/** Adds a relation of property between a user and a merch in the PROPERTY_MERCH chart
	 * @param user			User class object
	 * @param merch			Merch class object
	 */
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
	
	/** Obtains a map from PROPERTY_MERCH chart where the keys are
	 * 	users' ids and values are a list of each user's merch ids
	 * @return userMerch	HashMap<Integer, ArrayList<Integer>>
	 */
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
