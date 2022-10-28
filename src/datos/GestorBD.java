package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class GestorBD {
	
	protected static final String DRIVER_NAME = "org.sqlite.JDBC";
	protected static final String DATABASE_FILE = "db/database.db";
	protected static final String CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_FILE;
	
	public GestorBD() {		
		try {
			//Cargar el diver SQLite
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException ex) {
			System.err.println(String.format("* Error al cargar el driver de BBDD: %s", ex.getMessage()));
			ex.printStackTrace();
		}
	}
	
	public void crearBBDD() {
		//Se abre la conexión y se obtiene el Statement
		//Al abrir la conexión, si no existía el fichero, se crea la base de datos
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
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
					   + " ID_USER1 INTEGER,\n"
					   + " ID_USER2 INTEGER,\n"
					   + ");";
	        	        
	        if (!stmt.execute(sql)) {
	        	System.out.println("- Games chart successfuly created");
	        }
		} catch (Exception ex) {
			System.err.println(String.format("* Error al crear la BBDD: %s", ex.getMessage()));
			ex.printStackTrace();			
		}
	}
	
}
