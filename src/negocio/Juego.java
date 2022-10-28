package negocio;

import java.util.Objects;

public class Juego {
	protected int id_game;
	protected String name;
	protected String company;
	protected Pegi pegi;
	protected CategoriaJuego genre1;
	protected CategoriaJuego genre2;
	protected double price;
	protected String description;
	protected String img_link;

	public Juego(int id_game, String name, String company, Pegi pegi, CategoriaJuego genre1, CategoriaJuego genre2,
			double price, String description, String img_link) {
		super();
		this.id_game = id_game;
		this.name = name;
		this.company = company;
		this.pegi = pegi;
		this.genre1 = genre1;
		this.genre2 = genre2;
		this.price = price;
		this.description = description;
		this.img_link = img_link;
	}

	public int getId_game() {
		return id_game;
	}

	public void setId_game(int id_game) {
		this.id_game = id_game;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Pegi getPegi() {
		return pegi;
	}

	public void setPegi(Pegi pegi) {
		this.pegi = pegi;
	}

	public CategoriaJuego getGenre1() {
		return genre1;
	}

	public void setGenre1(CategoriaJuego genre1) {
		this.genre1 = genre1;
	}

	public CategoriaJuego getGenre2() {
		return genre2;
	}

	public void setGenre2(CategoriaJuego genre2) {
		this.genre2 = genre2;
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

	public String getImg_link() {
		return img_link;
	}

	public void setImg_link(String img_link) {
		this.img_link = img_link;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_game);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Juego other = (Juego) obj;
		return id_game == other.id_game;
	}

	@Override
	public String toString() {
		return "Juego [id_game=" + id_game + ", name=" + name + ", company=" + company + ", pegi=" + pegi + ", genre1="
				+ genre1 + ", genre2=" + genre2 + ", price=" + price + ", description=" + description + ", img_link="
				+ img_link + "]";
	}

}
