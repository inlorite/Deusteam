package negocio;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private User user;
	private User user2;
	private int id = 0;
	private String username = "Username";
	private String email = "email";
	private String password = "Password";
	private Country country = Country.Spain;
	private Date lastTimePlayed;
	private int totalTimePlayed = 1;
	private ArrayList<Integer> friends;
	private ArrayList<Game> games;
	
	@Before
	public void setUp() throws Exception {
		user = new User(id, username, email, password, country, lastTimePlayed, totalTimePlayed, friends, games);
		user2 = new User(id, username, email, password, country, lastTimePlayed, totalTimePlayed, friends, games);
	}

	@After
	public void tearDown() throws Exception {
		user = null;
		user2 = null;
	}

	@Test
	public void testHashCode() {
		assertEquals(user.hashCode(), user2.hashCode());
	}

	@Test
	public void testUser() {
		assertNotNull(user);
		assertEquals(id, user.getId());
		assertEquals(username, user.getUsername());
		assertEquals(email, user.getEmail());
		assertEquals(password, user.getPassword());
		assertEquals(country, user.getCountry());
		assertEquals(lastTimePlayed, user.getLastTimePlayed());
		assertEquals(totalTimePlayed, user.getTotalTimePlayed());
		assertEquals(friends, user.getFriends());
		assertEquals(games, user.getGames());
	}

	@Test
	public void testGetId() {
		assertEquals(id, user.getId());
	}

	@Test
	public void testSetId() {
		int newId = 1;
		user.setId(newId);
		
		assertEquals(newId, user.getId());
	}

	@Test
	public void testGetUsername() {
		assertEquals(username, user.getUsername());
		
	}

	@Test
	public void testSetUsername() {
		String newUsername = "New username";
		user.setUsername(newUsername);
		
		assertEquals(newUsername, user.getUsername());
	}

	@Test
	public void testGetEmail() {
		assertEquals(email, user.getEmail());
	}

	@Test
	public void testSetEmail() {
		String newEmail = "New email";
		user.setEmail(newEmail);
		
		assertEquals(newEmail, user.getEmail());
	}

	@Test
	public void testGetPassword() {
		assertEquals(password, user.getPassword());
	}

	@Test
	public void testSetPassword() {
		String newPassword = "New password";
		user.setPassword(newPassword);
		
		assertEquals(newPassword, user.getPassword());
	}

	@Test
	public void testGetGames() {
		assertEquals(games, user.getGames());
	}

	@Test
	public void testSetGames() {
		ArrayList<Game> games = new ArrayList<>();
		Game g = new Game(13, "VALORANT", "RIOT", Pegi.PEGI16, GameGenre.Multiplayer, GameGenre.PvP, 24.99, "Juego multijugador pvp first person shooter", "imglink");
		games.add(g);
		user.setGames(games);
		
		assertEquals(games, user.getGames());
	}

	@Test
	public void testGetCountry() {
		assertEquals(country, user.getCountry());
	}

	@Test
	public void testSetCountry() {
		Country newCountry = Country.Albania;
		user.setCountry(newCountry);
		
		assertEquals(newCountry, user.getCountry());
	}

	@Test
	public void testGetFriends() {
		assertEquals(friends, user.getFriends());
	}

	@Test
	public void testSetFriends() {
		ArrayList<Integer> users = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			users.add((int) Math.random() * 25 + 1);
		}
		user.setFriends(users);
		
		assertEquals(users, user.getFriends());
	}

	@Test
	public void testGetLastTimePlayed() {
		assertEquals(lastTimePlayed, user.getLastTimePlayed());
	}

	@Test
	public void testSetLastTimePlayed() {
		Date date = new Date();
		
		user.setLastTimePlayed(date);
		
		assertEquals(date, user.getLastTimePlayed());
	}

	@Test
	public void testGetTotalTimePlayed() {
		assertEquals(totalTimePlayed, user.getTotalTimePlayed());
	}

	@Test
	public void testSetTotalTimePlayed() {
		int newTotalTimePlayed = 10;
		user.setTotalTimePlayed(newTotalTimePlayed);
		
		assertEquals(newTotalTimePlayed, user.getTotalTimePlayed());
	}

	@Test
	public void testEqualsObject() {
		assertEquals(user.getId(), user2.getId());
	}

	@Test
	public void testToString() {
		String toString = "Usuario [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", country=" + country + ", lastTimePlayed=" + lastTimePlayed + ", totalTimePlayed=" + totalTimePlayed
				+ ", friends=" + friends + ", games=" + games + "]";
		
		assertEquals(toString, user.toString());
	}

}
