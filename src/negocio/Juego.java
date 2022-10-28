package negocio;

import java.util.Objects;

public class Juego {
	protected int id;
	protected String name;
	protected String company;
	protected Pegi pegi;
	protected CategoriaJuego genre1;
	protected CategoriaJuego genre2;
	protected double price;
	protected String description;
	protected String imgLink;

	public Juego(int id, String name, String company, Pegi pegi, CategoriaJuego genre1, CategoriaJuego genre2,
			double price, String description, String imgLink) {
		super();
		this.id = id;
		this.name = name;
		this.company = company;
		this.pegi = pegi;
		this.genre1 = genre1;
		this.genre2 = genre2;
		this.price = price;
		this.description = description;
		this.imgLink = imgLink;
	}

	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
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

	public String getimgLink() {
		return imgLink;
	}

	public void setimgLink(String imgLink) {
		this.imgLink = imgLink;
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
		Juego other = (Juego) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Juego [id=" + id + ", name=" + name + ", company=" + company + ", pegi=" + pegi + ", genre1="
				+ genre1 + ", genre2=" + genre2 + ", price=" + price + ", description=" + description + ", imgLink="
				+ imgLink + "]";
	}

}
