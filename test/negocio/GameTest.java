package negocio;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

	private Game game;
	private int id = 0;
	private String name = "Name";
	private String company = "Company";
	private Pegi pegi = Pegi.PEGI18;
	private GameGenre genre1 = GameGenre.Action;
	private GameGenre genre2 = GameGenre.Adventure;
	private double price = 0.0;
	private String description = "Description";
	private String imgLink = "ImgLink";
	
	@Before
	public void setUp() throws Exception {
		game = new Game(id, name, company, pegi, genre1, genre2, price, description, imgLink);
	}

	@After
	public void tearDown() throws Exception {
		game = null;
	}

	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testJuego() {
		assertNotNull(game);
		assertEquals(id, game.getId());
		assertEquals(name, game.getName());
		assertEquals(company, game.getCompany());
		assertEquals(pegi, game.getPegi());
		assertEquals(genre1, game.getGenre1());
		assertEquals(genre2, game.getGenre2());
		assertEquals(price, game.getPrice(), 0.0);
		assertEquals(description, game.getDescription());
		assertEquals(imgLink, game.imgLink);
	}

	@Test
	public void testGetId() {
		assertEquals(id, game.getId());
	}

	@Test
	public void testSetId() {
		int newId = 1;
		game.setId(newId);
		
		assertEquals(newId, game.getId());
	}

	@Test
	public void testGetName() {
		assertEquals(name, game.getName());
	}

	@Test
	public void testSetName() {
		String newName = "New name";
		game.setName(newName);
		
		assertEquals(newName, game.getName());
	}

	@Test
	public void testGetCompany() {
		assertEquals(company, game.getCompany());
	}

	@Test
	public void testSetCompany() {
		String newCompany = "New company";
		game.setCompany(newCompany);
		
		assertEquals(newCompany, game.getCompany());
	}

	@Test
	public void testGetPegi() {
		assertEquals(pegi, game.getPegi());
	}

	@Test
	public void testSetPegi() {
		Pegi newPegi = Pegi.PEGI3;
		game.setPegi(newPegi);
		
		assertEquals(newPegi, game.getPegi());
	}

	@Test
	public void testGetGenre1() {
		assertEquals(genre1, game.getGenre1());
	}

	@Test
	public void testSetGenre1() {
		GameGenre newGenre1 = GameGenre.RPG;
		game.setGenre1(newGenre1);
		
		assertEquals(newGenre1, game.getGenre1());
	}

	@Test
	public void testGetGenre2() {
		assertEquals(genre2, game.getGenre2());
	}

	@Test
	public void testSetGenre2() {
		GameGenre newGenre2 = GameGenre.MMORPG;
		game.setGenre2(newGenre2);
		
		assertEquals(newGenre2, game.getGenre2());
	}

	@Test
	public void testGetPrice() {
		assertEquals(price, game.getPrice(), 0.0);
	}

	@Test
	public void testSetPrice() {
		double newPrice = 1.1;
		game.setPrice(newPrice);
		
		assertEquals(newPrice, game.getPrice(), 0.0);
	}

	@Test
	public void testGetDescription() {
		assertEquals(description, game.getDescription());
	}

	@Test
	public void testSetDescription() {
		String newDescription = "New Description";
		game.setDescription(newDescription);
		
		assertEquals(newDescription, game.getDescription());
	}

	@Test
	public void testGetimgLink() {
		assertEquals(imgLink, game.getImgLink());
	}

	@Test
	public void testSetimgLink() {
		String newImgLink = "New ImgLink";
		game.setImgLink(newImgLink);
		
		assertEquals(newImgLink, game.getImgLink());
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
