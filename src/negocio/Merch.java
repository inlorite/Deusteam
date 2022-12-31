package negocio;

import java.util.Objects;

public class Merch {

	protected int id;
	protected String name;
	protected String owner;
	protected MerchType type;
	protected double price;
	
	/** Creates a new Merch class object
	 * @param id				Integer of the merch id
	 * @param name				String of the name
	 * @param owner				String with the owner of the merch
	 * @param type				Enum of {@link #MerchType}
	 * @param price				Double with the price
	 */
	public Merch(int id, String name, String owner, MerchType type, double price) {
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.type = type;
		this.price = price;
	}
	
	/** Creates a new Merch class object with default values
	 */
	public Merch() {
		super();
		this.id = 0;
		this.name = "";
		this.owner = "";
		this.type = MerchType.Soundtrack;
		this.price = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public MerchType getType() {
		return type;
	}

	public void setType(MerchType type) {
		this.type = type;
	}
	
	public void setType(String type) {
		this.type = MerchType.valueOf(type);
	}

	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Merch other = (Merch) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Merch [id=" + id + ", name=" + name + ", owner=" + owner + ", type=" + type + ", price=" + price + "]";
	}
	
}
