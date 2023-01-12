package negocio;

import java.io.Serializable;
import java.util.Objects;

public class Game implements Serializable, Comparable<Game> {
	
	private static final long serialVersionUID = 1L;
	
	protected int idAux;

	protected int id;
	protected String name;
	protected String owner;
	protected Pegi pegi;
	protected GameGenre genre1;
	protected GameGenre genre2;
	protected double price;
	protected String description;
	protected String imgLink;
	
	/** Creates a new Game class object
	 * @param id				Integer of the game id
	 * @param name				String of the name
	 * @param owner				String with the owner of the game
	 * @param pegi				Enum of {@link #Pegi}
	 * @param genre1			Enum of {@link #GameGenre}
	 * @param genre2			Enum of {@link #GameGenre}
	 * @param price				Double with the price
	 * @param description		String of the game description
	 * @param imgLink			String of the game's image link
	 */
	public Game(int id, String name, String owner, Pegi pegi, GameGenre genre1, GameGenre genre2,
			double price, String description, String imgLink) {
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.pegi = pegi;
		this.genre1 = genre1;
		this.genre2 = genre2;
		this.price = price;
		this.description = description;
		this.imgLink = imgLink;
	}
	
	/** Creates a new Game class object with default values
	 */
	public Game() {
		super();
		this.id = 0;
		this.name = "";
		this.owner = "";
		this.pegi = Pegi.PEGI18;
		this.genre1 = GameGenre.Action;
		this.genre2 = GameGenre.Multiplayer;
		this.price = 0.0;
		this.description = "";
		this.imgLink = "";
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

	public Pegi getPegi() {
		return pegi;
	}

	public void setPegi(Pegi pegi) {
		this.pegi = pegi;
	}
	
	public void setPegi(String pegi) {
		this.pegi = Pegi.valueOf(pegi);
	}

	public GameGenre getGenre1() {
		return genre1;
	}

	public void setGenre1(GameGenre genre1) {
		this.genre1 = genre1;
	}
	
	public void setGenre1(String genre1) {
		this.genre1 = GameGenre.valueOf(genre1);
	}

	public GameGenre getGenre2() {
		return genre2;
	}

	public void setGenre2(GameGenre genre2) {
		this.genre2 = genre2;
	}
	
	public void setGenre2(String genre2) {
		this.genre2 = GameGenre.valueOf(genre2);
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}
	
	public int getIdAux() {
		return idAux;
	}

	public void setIdAux(int idAux) {
		this.idAux = idAux;
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
		Game other = (Game) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "id=" + id + ", " + name ;
		
	//	"Juego [id=" + id + ", name=" + name + ", owner=" + owner + ", pegi=" + pegi + ", genre1="
	//	+ genre1 + ", genre2=" + genre2 + ", price=" + price + ", description=" + description + ", imgLink="
	//	+ imgLink + "]";
	}

	@Override
	public int compareTo(Game o) {
		return this.id - o.id;
	}

}
