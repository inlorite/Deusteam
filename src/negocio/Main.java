package negocio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import datos.DBManager;
import gui.*;

public class Main {
	public static ArrayList<User> users;
	public static DBManager gestor;
	
	protected static Properties properties;
	
	public static void main(String[] args) {
		
		try {
			properties = new Properties();
			properties.load(new FileReader("conf/config.properties"));
		} catch (Exception ex) {
			System.err.println(String.format("* Error iniciando properties: %s", ex.getMessage()));
		}
		
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
		//gestor.updateGamePrice(games.get(0), 9.85);
		gestor.incrementUserTotalTimePlayed(users.get(0).getId(), 50);
		
		initPropertyGames(users, games);
		gestor.updatePropertyGamesInstalled(users.get(1).getId(), games.get(1).getId(), 1);
		gestor.updatePropertyGamesInstalled(users.get(2).getId(), games.get(0).getId(), 1);
		
		initPropertyMerch(users, merch);
		
		initFriends(users);
		
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
		
		
		// creacion de ficheros
//		guardarFicheroUsers();
//		guardarFicheroGames();
//		guardarFicheroMerch();
		
		// comprobacion de inicializacion
//		System.out.println(initFicheroUsers());
//		System.out.println(initFicheroGames());
//		System.out.println(initFicheroMerch());
		
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
		user.setBalance(200);
		user.setFriends(new ArrayList<Integer>());
		user.setGames(new ArrayList<Game>());
		user.setMerch(new ArrayList<Merch>());
		users.add(user);
		
		user= new User();
		user.setId(2);
		user.setUsername("Adrian");
		user.setEmail("adrian@gmail.com");
		user.setPassword("adrianmartinez");
		user.setCountry(Country.Spain);
		user.setLastTimePlayed(new Date());
		user.setTotalTimePlayed(15);
		user.setBalance(500);
		user.setFriends(new ArrayList<Integer>());
		user.setGames(new ArrayList<Game>());
		user.setMerch(new ArrayList<Merch>());
		users.add(user);
		
		user= new User();
		user.setId(3);
		user.setUsername("Yeray");
		user.setEmail("yeray@gmail.com");
		user.setPassword("yeraymartinez");
		user.setCountry(Country.Spain);
		user.setLastTimePlayed(new Date());
		user.setTotalTimePlayed(23);
		user.setBalance(200);
		user.setFriends(new ArrayList<Integer>());
		user.setGames(new ArrayList<Game>());
		user.setMerch(new ArrayList<Merch>());
		users.add(user);
		
		user= new User();
		user.setId(4);
		user.setUsername("Xabi");
		user.setEmail("xabi@gmail.com");
		user.setPassword("xabilorenzo");
		user.setCountry(Country.Spain);
		user.setLastTimePlayed(new Date());
		user.setTotalTimePlayed(10);
		user.setBalance(200);
		user.setFriends(new ArrayList<Integer>());
		user.setGames(new ArrayList<Game>());
		user.setMerch(new ArrayList<Merch>());
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
		game.setPrice(45.0);
		game.setDescription("Alzate, Sinluz, y que la gracia te guie para abrazar el poder del Circulo de Elden y encumbrarte como senor del Circulo en las Tierras Intermedias.");
		game.setImgLink("data/game_banners/1.jpg");
		games.add(game);
		
		game = new Game();
		game.setId(2);
		game.setName("FIFA 23");
		game.setOwner("Xabi");
		game.setPegi(Pegi.PEGI12);
		game.setGenre1(GameGenre.Casual);
		game.setGenre2(GameGenre.Arcade);
		game.setPrice(25.0);
		game.setDescription("Vive la emocion del mayor torneo futbolistico con EA SPORTS FIFA 23 y la actualizacion de la FIFA World Cup masculina.");
		game.setImgLink("data/game_banners/2.jpg");
		games.add(game);
		
		game = new Game();
		game.setId(3);
		game.setName("Persona 5 Royal");
		game.setOwner("Adrian");
		game.setPegi(Pegi.PEGI7);
		game.setGenre1(GameGenre.Singleplayer);
		game.setGenre2(GameGenre.Action);
		game.setPrice(20.0);
		game.setDescription("Ponte la mascara, acompana a los Ladrones Fantasma de Corazones en sus asaltos e infiltrate en la mente de los corruptos para hacerles cambiar de opinion!");
		game.setImgLink("data/game_banners/3.jpg");
		games.add(game);
		
		game = new Game();
		game.setId(4);
		game.setName("Vampire Survivors");
		game.setOwner("Inigo");
		game.setPegi(Pegi.PEGI7);
		game.setGenre1(GameGenre.Action);
		game.setGenre2(GameGenre.Fantasy);
		game.setPrice(12.0);
		game.setDescription("Aniquila a miles de criaturas de la noche y sobrevive hasta el amanecer! Vampire Survivors es un juego casual de terror gotico con elementos roguelite donde tus decisiones te permitiran aumentar tu poder exponencialmente mientras luchas contra cientos de monstruos.");
		game.setImgLink("data/game_banners/4.jpg");
		games.add(game);
		
		return games;
	}
	
	public static List<Merch> initMerch(){
		ArrayList<Merch> merchList = new ArrayList<>();
		
		Merch merch = new Merch();
		merch.setId(1);
		merch.setName("Elden Ring Soundtrack");
		merch.setOwner("Yeray");
		merch.setPrice(6.0);
		merch.setType(MerchType.Soundtrack);
		merchList.add(merch);
		
		merch = new Merch();
		merch.setId(2);
		merch.setName("Vampire Survivors Artbook");
		merch.setOwner("Inigo");
		merch.setPrice(10.0);
		merch.setType(MerchType.Artbook);
		merchList.add(merch);
		
		merch = new Merch();
		merch.setId(3);
		merch.setName("Persona 5 Soundtrack");
		merch.setOwner("Adrian");
		merch.setPrice(15.0);
		merch.setType(MerchType.Soundtrack);
		merchList.add(merch);
		
		merch = new Merch();
		merch.setId(4);
		merch.setName("Elden Ring Artbook");
		merch.setOwner("Yeray");
		merch.setPrice(10.0);
		merch.setType(MerchType.Artbook);
		merchList.add(merch);
		
		return merchList;
	}
	
	public static void initPropertyGames(List<User> users, List<Game> games) {
		
		gestor.insertDataPropertyGames(users.get(0), games.get(1));
		gestor.insertDataPropertyGames(users.get(0), games.get(3));
		
		gestor.insertDataPropertyGames(users.get(1), games.get(0));
		gestor.insertDataPropertyGames(users.get(1), games.get(1));
		gestor.insertDataPropertyGames(users.get(1), games.get(2));
		
		gestor.insertDataPropertyGames(users.get(2), games.get(0));
		gestor.insertDataPropertyGames(users.get(2), games.get(2));
		gestor.insertDataPropertyGames(users.get(2), games.get(3));
		
		gestor.insertDataPropertyGames(users.get(3), games.get(1));
		gestor.insertDataPropertyGames(users.get(3), games.get(3));
		
	}
	
	public static void initFriends(List<User> users) {
		
		gestor.insertDataFriends(users.get(1), users.get(0));
		gestor.insertDataFriends(users.get(1), users.get(2));
		gestor.insertDataFriends(users.get(1), users.get(3));
		
	}
	
	public static void initPropertyMerch(List<User> users, List<Merch> merch) {
		
		gestor.insertDataPropertyMerch(users.get(0), merch.get(1));
		
		gestor.insertDataPropertyMerch(users.get(1), merch.get(0));
		gestor.insertDataPropertyMerch(users.get(1), merch.get(3));
		gestor.insertDataPropertyMerch(users.get(1), merch.get(2));
		
		gestor.insertDataPropertyMerch(users.get(2), merch.get(0));
		gestor.insertDataPropertyMerch(users.get(2), merch.get(3));
		
	}
	
	public static ArrayList<User> initFicheroUsers() {
		
		ArrayList<User> userList = new ArrayList<>();
		
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(properties.getProperty("FICHERO_USERS")));
			
			userList = (ArrayList<User>) ois.readObject();
			
			ois.close();
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Error cargando el fichero de usuarios");
		}
		
		return userList;
		
	}
	
	public static ArrayList<Game> initFicheroGames() {
		
		ArrayList<Game> gameList = new ArrayList<>();
		
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(properties.getProperty("FICHERO_GAMES")));
			
			gameList = (ArrayList<Game>) ois.readObject();
			
			ois.close();
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Error cargando el fichero de juegos");
		}
		
		return gameList;
		
	}

	public static ArrayList<Merch> initFicheroMerch() {
		
		ArrayList<Merch> merchList = new ArrayList<>();
		
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(properties.getProperty("FICHERO_MERCH")));
			
			merchList = (ArrayList<Merch>) ois.readObject();
			
			ois.close();
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Error cargando el fichero de merch");
		}
		
		return merchList;
		
	}
	
	public static void guardarFicheroUsers() {
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(properties.getProperty("FICHERO_USERS")))) {
		
			oos.writeObject(gestor.obtainDataUsers());
			
		} catch (Exception e) {
			System.err.println("Error guardando el fichero de users");
		}
		
	}
	
	public static void guardarFicheroGames() {
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(properties.getProperty("FICHERO_GAMES")))) {
		
			oos.writeObject(gestor.obtainDataGames());
			
		} catch (Exception e) {
			System.err.println("Error guardando el fichero de games");
		}
		
	}

	public static void guardarFicheroMerch() {
	
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(properties.getProperty("FICHERO_MERCH")))) {
		
			oos.writeObject(gestor.obtainDataMerch());
			
		} catch (Exception e) {
			System.err.println("Error guardando el fichero de merch");
		}
	
	}
}
