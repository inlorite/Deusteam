package negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import datos.DBManager;
import gui.*;

public class Main {
	public static ArrayList<User> users;
	public static DBManager gestor;
	public static void main(String[] args) {
		
		
		gestor = new DBManager();
		gestor.deleteDatabase();
		
		// Create: Creacion de la BD
		gestor.createDatabase();
		
		// Insert: Insercion de usuarios y juegos en la BD
		
		// Usuarios
		List<User> users = initUsers();
		gestor.insertDataUsers(users.toArray(new User[users.size()]));
		
		// Juegos
		List<Game> games = initGames();
		gestor.insertDataGames(games.toArray(new Game[games.size()]));
		
		// Merch
		List<Merch> merch = initMerch();
		gestor.insertDataMerch(merch.toArray(new Merch[merch.size()]));
			
		// Update: Modificacion de usuarios y juegos en la BD
		gestor.updateGamePrice(games.get(0), 9.85);
		gestor.incrementUserTotalTimePlayed(users.get(0).getId(), 50);
		
		initPropertyGames(users, games);
		gestor.updatePropertyGamesInstalled(users.get(1).getId(), games.get(1).getId(), 1);
		gestor.updatePropertyGamesInstalled(users.get(2).getId(), games.get(0).getId(), 1);
		
		// Select: 
		for (User u : gestor.obtainDataUsers()) {
			System.out.println(u);
		}
		
		for (Game g : gestor.obtainDataGames()) {
			System.out.println(g);
		}
		
		for (Merch m : gestor.obtainDataMerch()) {
			System.out.println(m);
		}
		
		//VDeusteam v = new VDeusteam();
		VLogin v = new VLogin(gestor);
		
		// Delete: Borrado de las tablas y de la BD
		//gestor.deleteDatabase();
	}
	
	public static List<User> initUsers(){
		users= new ArrayList<>();
		
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
		game.setName("Elden Ring");
		game.setOwner("Yeray");
		game.setPegi(Pegi.PEGI16);
		game.setGenre1(GameGenre.Action);
		game.setGenre2(GameGenre.RPG);
		game.setPrice(10.0);
		game.setDescription("description1");
		game.setImgLink("data/game_banners/1.jpg");
		games.add(game);
		
		game = new Game();
		game.setId(2);
		game.setName("FIFA 23");
		game.setOwner("Xabi");
		game.setPegi(Pegi.PEGI7);
		game.setGenre1(GameGenre.Casual);
		game.setGenre2(GameGenre.Arcade);
		game.setPrice(12.0);
		game.setDescription("description2");
		game.setImgLink("data/game_banners/2.jpg");
		games.add(game);
		
		game = new Game();
		game.setId(3);
		game.setName("Persona 5 Royal");
		game.setOwner("Adrian");
		game.setPegi(Pegi.PEGI12);
		game.setGenre1(GameGenre.Singleplayer);
		game.setGenre2(GameGenre.Action);
		game.setPrice(132.0);
		game.setDescription("description3");
		game.setImgLink("data/game_banners/3.jpg");
		games.add(game);
		
		game = new Game();
		game.setId(4);
		game.setName("Vampire Survivors");
		game.setOwner("Inigo");
		game.setPegi(Pegi.PEGI16);
		game.setGenre1(GameGenre.Action);
		game.setGenre2(GameGenre.Fantasy);
		game.setPrice(12.0);
		game.setDescription("description4");
		game.setImgLink("data/game_banners/4.jpg");
		games.add(game);
		
		return games;
	}
	
	public static List<Merch> initMerch(){
		ArrayList<Merch> merchList = new ArrayList<>();
		
		Merch merch = new Merch();
		merch.setId(1);
		merch.setName("Elden Ring Soundtrack");
		merch.setPrice(5.99);
		merch.setType(MerchType.Soundtrack);
		merchList.add(merch);
		
		merch = new Merch();
		merch.setId(2);
		merch.setName("Vampire Survivors Artbook");
		merch.setPrice(9.99);
		merch.setType(MerchType.Artbook);
		merchList.add(merch);
		
		merch = new Merch();
		merch.setId(3);
		merch.setName("Persona 5 Soundtrack");
		merch.setPrice(14.99);
		merch.setType(MerchType.Soundtrack);
		merchList.add(merch);
		
		merch = new Merch();
		merch.setId(4);
		merch.setName("Elden Ring Artbook");
		merch.setPrice(9.99);
		merch.setType(MerchType.Artbook);
		merchList.add(merch);
		
		return merchList;
	}
	
	public static void initPropertyGames(List<User> users, List<Game> games) {
		
		gestor.insertDataPropertyGames(users.get(1), games.get(0));
		gestor.insertDataPropertyGames(users.get(1), games.get(1));
		gestor.insertDataPropertyGames(users.get(1), games.get(3));
		
		gestor.insertDataPropertyGames(users.get(2), games.get(0));
		gestor.insertDataPropertyGames(users.get(2), games.get(2));
		gestor.insertDataPropertyGames(users.get(2), games.get(3));
		
	}
	
}
