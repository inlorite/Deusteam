package negocio;

public class Merch {

	protected int id;
	protected String game;
	protected MerchType type;
	protected double price;
	
	
	public Merch(int id, String game, MerchType type, double price) {
		super();
		this.id = id;
		this.game = game;
		this.type = type;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
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
	public String toString() {
		return "Merch [id=" + id + ", game=" + game + ", type=" + type + ", price=" + price + "]";
	}
	
}
