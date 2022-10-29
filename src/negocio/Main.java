package negocio;

import java.util.List;

import datos.DBManager;

public class Main {

	public static void main(String[] args) {
		
		DBManager gestor = new DBManager();
		
		// creacion
		gestor.createDatabase();
		// insercion
			//usuarios
			List<User> users =gestor.obtainDataUsers();
			gestor.insertDataUsers(users.toArray(new User[users.size()]));
			//juegos
			List<Game> games =gestor.obtainDataGames();
			gestor.insertDataGames(games.toArray(new Game[games.size()]));
			
		
		// modificacion
		
		// borrado
		gestor.deleteDatabase();
	}
	
}
