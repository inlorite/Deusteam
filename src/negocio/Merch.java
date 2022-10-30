package negocio;

import java.util.Objects;

public class Merch {

	protected int id;
	protected String name;
	protected MerchType type;
	protected double price;
	
	
	public Merch(int id, String name, MerchType type, double price) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
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

	public MerchType getType() {
		return type;
	}

	public void setType(MerchType type) {
		this.type = type;
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
		return "Merch [id=" + id + ", name=" + name + ", type=" + type + ", price=" + price + "]";
	}
	
}
