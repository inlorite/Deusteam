package negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import datos.DBManager;

public class Main {

	public static void main(String[] args) {
		
		DBManager gestor = new DBManager();
		
		// creacion
		gestor.createDatabase();
		// insercion
			//usuarios
			List<User> users =initUser();
			gestor.insertDataUsers(users.toArray(new User[users.size()]));
			//juegos
			List<Game> games =gestor.obtainDataGames();
			gestor.insertDataGames(games.toArray(new Game[games.size()]));
			
		
		// modificacion
		
		// borrado
		gestor.deleteDatabase();
	}
	
	public static List<User> initUser(){
		ArrayList<User> users= new ArrayList<>();
		
		User user = new User();
		user.setId(1);
		user.setUsername("Inigo");
		user.setEmail("inigo@gmail.com");
		user.setPassword("inigolorite");
		user.setCountry(Country.Spain);
		user.setLastTimePlayed(new Date());
		user.setTotalTimePlayed(12);
		user.setFriends(new ArrayList());
		user.setGames(new ArrayList());
		users.add(user);
		
		user= new User();
		user.setId(2);
		user.setUsername("Adrian");
		user.setEmail("adrian@gmail.com");
		user.setPassword("adrianmartinez");
		user.setCountry(Country.Spain);
		user.setLastTimePlayed(new Date());
		user.setTotalTimePlayed(15);
		user.setFriends(new ArrayList());
		user.setGames(new ArrayList());
		users.add(user);
		
		user= new User();
		user.setId(3);
		user.setUsername("Yeray");
		user.setEmail("yeray@gmail.com");
		user.setPassword("yeraymartinez");
		user.setCountry(Country.Spain);
		user.setLastTimePlayed(new Date());
		user.setTotalTimePlayed(23);
		user.setFriends(new ArrayList());
		user.setGames(new ArrayList());
		users.add(user);
		
		user= new User();
		user.setId(4);
		user.setUsername("Xabi");
		user.setEmail("xabi@gmail.com");
		user.setPassword("xabilorenzo");
		user.setCountry(Country.Spain);
		user.setLastTimePlayed(new Date());
		user.setTotalTimePlayed(10);
		user.setFriends(new ArrayList());
		user.setGames(new ArrayList());
		users.add(user);
		
		return users;
	}
	
}
