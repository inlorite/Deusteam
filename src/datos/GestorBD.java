package datos;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

import negocio.Juego;
import negocio.Usuario;

public class GestorBD {
	
	/*
	protected static final String DRIVER_NAME = "org.sqlite.JDBC";
	protected static final String DATABASE_FILE = "db/database.db";
	protected static final String CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_FILE;
	*/
	protected static Properties properties;
	
	public GestorBD() {		
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
	
	public void crearBBDD() {
		// Connection is established and the Statement is obtained
		// If file does not exist, database is created
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			
	        String sql = "CREATE TABLE IF NOT EXISTS GAMES (\n"
	                   + " ID_GAME INTEGER PRIMARY KEY AUTOINCREMENT,\n"
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
	                   + " ID_USER INTEGER PRIMARY KEY AUTOINCREMENT,\n"
	                   + " USERNAME TEXT NOT NULL,\n"
	                   + " EMAIL TEXT NOT NULL,\n"
	                   + " PASSWORD TEXT NOT NULL,\n"
	                   + " COUNTRY TEXT NOT NULL,\n"
	                   + " LAST_TIME_PLAYED DATE NOT NULL,\n"
	                   + " TOTAL_TIME_PLAYED INT NOT NULL,\n"
	                   + ");"
	                   
					   + "CREATE TABLE IF NOT EXISTS PROPERTY (\n"
					   + " INSTALLED INTEGER NOT NULL,\n"
					   + " TOTAL_TIME_PLAYED INT NOT NULL,\n"
					   + " ID_GAME INTEGER FOREIGN KEY NOT NULL,\n"
					   + " ID_USER INTEGER FOREIGN KEY NOT NULL,\n"
					   + ");"
					   
					   + "CREATE TABLE IF NOT EXISTS FRIENDS (\n"
					   + " ID_USER1 INTEGER FOREIGN KEY NOT NULL,\n"
					   + " ID_USER2 INTEGER FOREIGN KEY NOT NULL,\n"
					   + ");";
	        
	        // Chart-creating sentence is executed
	        if (!stmt.execute(sql)) {
	        	System.out.println("- Successfuly created 4 charts (Games, Users, Property, Friends)");
	        }
		} catch (Exception ex) {
			System.err.println(String.format("* Error creating database: %s", ex.getMessage()));
			ex.printStackTrace();			
		}
	}
	
	public void borrarBBDD() {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			
	        String sql = "DROP TABLE IF EXISTS GAMES"
	        			+ "DROP TABLE IF EXISTS USERS"
	        			+ "DROP TABLE IF EXISTS PROPERTY"
	        			+ "DROP TABLE IF EXISTS FRIENDS";
			
	        // Chart-deleting sentence is executed
	        if (!stmt.execute(sql)) {
	        	System.out.println("- Successfuly deleted 4 charts (Games, Users, Property, Friends)");
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
	
	public void insertarDatosGames(Juego... juegos ) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "INSERT INTO GAMES (NAME, COMPANY, PEGI, GENRE1, GENRE2, PRICE, DESCRIPTION, IMG_LINK) "
					+ "VALUES ('%s', '%s', '%s','%s', '%s', '%d','%s', '%s');";
			
			System.out.println("- Adding games...");
			
			// Info is added to the chart
			for (Juego game : juegos) {
				if (1 == stmt.executeUpdate(String.format(sql, game.getName(),
						game.getCompany(), game.getPegi(), game.getGenre1(), 
						game.getGenre2(), game.getPrice(), game.getDescription(), 
						game.getimgLink()))) {					
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
	
	///////// USERS DATABASE /////////
	
	public void insertarDatosUsers(Usuario... usuarios ) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "INSERT INTO USERS (USERNAME, EMAIL, PASSWORD, COUNTRY, LAST_TIME_PLAYED, TOTAL_TIME_PLAYED) "
					+ "VALUES ('%s', '%s', '%s','%s', '%t', '%d');";
			
			System.out.println("- Adding users...");
			
			// Info is added to the chart
			for (Usuario user : usuarios) {
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
	
	///////// PROPERTY DATABASE /////////
	
	public void insertarDatosProperty(Juego game, Usuario user) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "INSERT INTO PROPERTY (INSTALLED, TOTAL_TIME_PLAYED, ID_GAME, ID_USER) "
					+ "VALUES ('%x', '%x', '%x','%x');";
			
			System.out.println("- Adding game to user´s library...");
			
			// Info is added to the chart
			if (1 == stmt.executeUpdate(String.format(sql, 0, 0, game.getId(), user.getId()))) {					
				System.out.println(String.format(" - Game added to library: %s", game.getName(), user.getUsername()));
			} else {
				System.out.println(String.format(" - Game could not be added to library: %s", game.getName(), user.getUsername()));
			}
		} catch (Exception ex) {
			System.err.println(String.format("* Error adding the game/user data: %s", ex.getMessage()));
			ex.printStackTrace();				
		}
	}
	
	///////// FRIENDS DATABASE /////////
	
	public void insertarDatosFriends(Usuario user1, Usuario user2) {
		// Connection is established and the Statement is obtained
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			// SQL sentence is defined
			String sql = "INSERT INTO FRIENDS (ID_USER1, ID_USER2) "
					+ "VALUES ('%x', '%x');";
			
			System.out.println("- Adding friend to user´s friendlist...");
			
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
	
}
