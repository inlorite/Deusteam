package negocio;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MerchTest {
	
	private Merch merch;
	private int id = 0;
	private String name = "name";
	private MerchType type = MerchType.soundtrack;
	private double price = 0.0;

	@Before
	public void setUp() throws Exception {
		merch = new Merch(id, name, type, price);
	}

	@After
	public void tearDown() throws Exception {
		merch=null;
	}

	@Test
	public void testMerch() {
		assertNotNull(merch);
		assertEquals(id, merch.getId());
		assertEquals(name, merch.getName());
		assertEquals(type, merch.getType());
		assertEquals(price, merch.getPrice(),0.0);
	}

	@Test
	public void testGetId() {
		assertEquals(id, merch.getId());
	}

	@Test
	public void testSetId() {
		int newId = 1;
		merch.setId(newId);
		
		assertEquals(newId, merch.getId());
	}

	@Test
	public void testGetGame() {
		assertEquals(name, merch.getName());
	}

	@Test
	public void testSetGame() {
		String newName= "name2";
		merch.setName(newName);
		
		assertEquals(newName, merch.getName());
	}

	@Test
	public void testGetType() {
		assertEquals(type, merch.getType());
	}

	@Test
	public void testSetType() {
		MerchType newType = MerchType.artbook;
		merch.setType(newType);
		
		assertEquals(newType, merch.getType());
	}

	@Test
	public void testGetPrice() {
		assertEquals(price, merch.getPrice(),0.0);
	}

	@Test
	public void testSetPrice() {
		Double newPrice = 1.0;
		merch.setPrice(newPrice);
		
		assertEquals(newPrice, merch.getPrice(),1.0);
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
