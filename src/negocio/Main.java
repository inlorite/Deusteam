package negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import datos.DBManager;

public class Main {

	public static void main(String[] args) {
		
		DBManager gestor = new DBManager();
		
		// Create: Creacion de la BD
		gestor.createDatabase();
		
		// Insert: Insercion de usuarios y juegos en la BD
		
		// Usuarios
		List<User> users = initUsers();
		gestor.insertDataUsers(users.toArray(new User[users.size()]));
		
		// Juegos
		List<Game> games = initGames();
		gestor.insertDataGames(games.toArray(new Game[games.size()]));
			
		// Update: Modificacion de usuarios y juegos en la BD
		gestor.updateGamePrice(games.get(0), 9.85);
		gestor.incrementUserTotalTimePlayed(users.get(0).getId(), 50);
		
		// Select: 
		for (User u : gestor.obtainDataUsers()) {
			System.out.println(u);
		}
		
		for (Game g : gestor.obtainDataGames()) {
			System.out.println(g);
		}
		
		// Delete: Borrado de las tablas y de la BD
		gestor.deleteDatabase();
	}
	
	public static List<User> initUsers(){
		ArrayList<User> users= new ArrayList<>();
		
		User user = new User();
		user.setId(1);
		user.setUsername("Inigo");
		user.setEmail("inigo@gmail.com");
		user.setPassword("inigolorite");
		user.setCountry(Country.Spain);
		user.setLastTimePlayed(new Date());
		user.setTotalTimePlayed(12);
		user.setFriends(new ArrayList<Integer>());
		user.setGames(new ArrayList<Game>());
		users.add(user);
		
		user= new User();
		user.setId(2);
		user.setUsername("Adrian");
		user.setEmail("adrian@gmail.com");
		user.setPassword("adrianmartinez");
		user.setCountry(Country.Spain);
		user.setLastTimePlayed(new Date());
		user.setTotalTimePlayed(15);
		user.setFriends(new ArrayList<Integer>());
		user.setGames(new ArrayList<Game>());
		users.add(user);
		
		user= new User();
		user.setId(3);
		user.setUsername("Yeray");
		user.setEmail("yeray@gmail.com");
		user.setPassword("yeraymartinez");
		user.setCountry(Country.Spain);
		user.setLastTimePlayed(new Date());
		user.setTotalTimePlayed(23);
		user.setFriends(new ArrayList<Integer>());
		user.setGames(new ArrayList<Game>());
		users.add(user);
		
		user= new User();
		user.setId(4);
		user.setUsername("Xabi");
		user.setEmail("xabi@gmail.com");
		user.setPassword("xabilorenzo");
		user.setCountry(Country.Spain);
		user.setLastTimePlayed(new Date());
		user.setTotalTimePlayed(10);
		user.setFriends(new ArrayList<Integer>());
		user.setGames(new ArrayList<Game>());
		users.add(user);
		
		return users;
	}
	
	public static List<Game> initGames(){
		ArrayList<Game> games= new ArrayList<>();
		
		Game game = new Game();
		game.setId(1);
		game.setName("binding of yeray");
		game.setCompany("company");
		game.setPegi(Pegi.PEGI12);
		game.setGenre1(GameGenre.Action);
		game.setGenre2(GameGenre.Adventure);
		game.setPrice(10.0);
		game.setDescription("description1");
		game.setImgLink("link");
		games.add(game);
		
		game = new Game();
		game.setId(2);
		game.setName("space adrian");
		game.setCompany("company");
		game.setPegi(Pegi.PEGI7);
		game.setGenre1(GameGenre.Action);
		game.setGenre2(GameGenre.Arcade);
		game.setPrice(12.0);
		game.setDescription("description2");
		game.setImgLink("link");
		games.add(game);
		
		return games;
	}
	
}
