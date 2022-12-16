package negocio;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MerchTest {
	
	private Merch merch;
	private Merch merch2;
	private int id = 0;
	private String name = "name";
	private MerchType type = MerchType.Soundtrack;
	private double price = 0.0;

	@Before
	public void setUp() throws Exception {
		merch = new Merch(id, name, type, price);
		merch2 = new Merch(id, name, type, price);
	}

	@After
	public void tearDown() throws Exception {
		merch = null;
		merch2  = null;
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
	public void testGetName() {
		assertEquals(name, merch.getName());
	}

	@Test
	public void testSetName() {
		String newName= "name2";
		merch.setName(newName);
		
		assertEquals(newName, merch.getName());
	}

	@Test
	public void testGetType() {
		assertEquals(type, merch.getType());
	}

	@Test
	public void testSetTypeMerchType() {
		MerchType newType = MerchType.Artbook;
		merch.setType(newType);
		
		assertEquals(newType, merch.getType());
	}
	
	@Test
	public void testSetTypeString() {
		String newType = "artbook";
		merch.setType(newType);
		
		assertEquals(MerchType.valueOf(newType), merch.getType());
	}
	
	@Test
	public void testHashCode() {
		assertEquals(merch.hashCode(), merch2.hashCode());
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
		assertEquals(merch.getId(), merch2.getId());
	}
	
	@Test
	public void testToString() {
		String toString = "Merch [id=" + id + ", name=" + name + ", type=" + type + ", price=" + price + "]";
		
		assertEquals(toString, merch.toString());
	}

}
