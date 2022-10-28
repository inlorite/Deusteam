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
			//Cargar el diver SQLite
			Class.forName(properties.getProperty("DRIVER_NAME"));
		} catch (ClassNotFoundException ex) {
			System.err.println(String.format("* Error al cargar el driver de BBDD: %s", ex.getMessage()));
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void crearBBDD() {
		//Se abre la conexión y se obtiene el Statement
		//Al abrir la conexión, si no existía el fichero, se crea la base de datos
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
					   + " TOTAL_TIME_PLAYED DECIMAL(),\n"
					   + " ID_GAME INTEGER FOREIGN KEY,\n"
					   + " ID_GAME INTEGER FOREIGN KEY,\n"
					   + ");"
					   
					   + "CREATE TABLE IF NOT EXISTS FRIENDS (\n"
					   + " ID_USER1 INTEGER FOREIGN KEY,\n"
					   + " ID_USER2 INTEGER FOREIGN KEY,\n"
					   + ");";
	        	        
	        if (!stmt.execute(sql)) {
	        	System.out.println("- Successfuly created 4 charts (Games, Users, Property, Friends)");
	        }
		} catch (Exception ex) {
			System.err.println(String.format("* Error creating database: %s", ex.getMessage()));
			ex.printStackTrace();			
		}
	}
	
	public void borrarBBDD() {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			
	        String sql = "DROP TABLE IF EXISTS GAMES"
	        			+ "DROP TABLE IF EXISTS USERS"
	        			+ "DROP TABLE IF EXISTS PROPERTY"
	        			+ "DROP TABLE IF EXISTS FRIENDS";
			
	        //Se ejecuta la sentencia de creación de la tabla Estudiantes
	        if (!stmt.execute(sql)) {
	        	System.out.println("- Successfuly deleted 4 charts (Games, Users, Property, Friends)");
	        }
		} catch (Exception ex) {
			System.err.println(String.format("* Error deleting database: %s", ex.getMessage()));
			ex.printStackTrace();			
		}
		
		try {
			//Se borra el fichero de la BBDD
			Files.delete(Paths.get(properties.getProperty("DATABASE_FILE")));
			System.out.println("- Database file successfuly deleted");
		} catch (Exception ex) {
			System.err.println(String.format("* Error deleting the database file: %s", ex.getMessage()));
			ex.printStackTrace();						
		}
	}
	
	///////// GAMES DATABASE /////////
	
	public void insertarDatosGames(Juego... juegos ) {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(properties.getProperty("CONNECTION_STRING"));
		     Statement stmt = con.createStatement()) {
			//Se define la plantilla de la sentencia SQL
			String sql = "INSERT INTO GAMES (NAME, COMPANY, PEGI, GENRE1, GENRE2, PRICE, DESCRIPTION, IMG_LINK) "
					+ "VALUES ('%s', '%s', '%s','%s', '%s', '%d','%s', '%s');";
			
			System.out.println("- Adding games...");
			
			//Se recorren los clientes y se insertan uno a uno
			for (Juego game : juegos) {
				if (1 == stmt.executeUpdate(String.format(sql, game.getName(),
						game.getCompany(), game.getPegi(), game.getGenre1(), 
						game.getGenre2(), game.getPrice(), game.getDescription(), 
						game.getImg_link()))) {					
					System.out.println(String.format(" - Game added: %s", game.toString()));
				} else {
					System.out.println(String.format(" - Game was not added: %s", game.toString()));
				}
			}
		} catch (Exception ex) {
			System.err.println(String.format("* Error adding the data: %s", ex.getMessage()));
			ex.printStackTrace();						
		}				
	}
	
	///////// USERS DATABASE /////////
	
	///////// PROPERTY DATABASE /////////
	
	///////// FRIENDS DATABASE /////////
	
}
