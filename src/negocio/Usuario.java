package negocio;

import java.util.ArrayList;
import java.util.Objects;

public class Usuario {
	
	protected int id;
	protected String name;
	protected String email;
	protected String passsword;
	protected String country;
	protected int gameNum;
	protected Double hoursPerGame;
	protected ArrayList<Integer> amigos;
	protected ArrayList<Juego> juegos;
	

	public Usuario(int id, String name, String email, String passsword, String country, int gameNum,
			Double hoursPerGame, ArrayList<Integer> amigos, ArrayList<Juego> juegos) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.passsword = passsword;
		this.country = country;
		this.gameNum = gameNum;
		this.hoursPerGame = hoursPerGame;
		this.amigos = amigos;
		this.juegos = juegos;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasssword() {
		return passsword;
	}

	public void setPasssword(String passsword) {
		this.passsword = passsword;
	}

	public ArrayList<Juego> getJuegos() {
		return juegos;
	}

	public void setJuegos(ArrayList<Juego> juegos) {
		this.juegos = juegos;
	}
	

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getGameNum() {
		return gameNum;
	}

	public void setGameNum(int gameNum) {
		this.gameNum = gameNum;
	}

	public Double getHoursPerGame() {
		return hoursPerGame;
	}

	public void setHoursPerGame(Double hoursPerGame) {
		this.hoursPerGame = hoursPerGame;
	}

	public ArrayList<Integer> getAmigos() {
		return amigos;
	}

	public void setAmigos(ArrayList<Integer> amigos) {
		this.amigos = amigos;
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
		Usuario other = (Usuario) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", name=" + name + ", email=" + email + ", passsword=" + passsword + ", country="
				+ country + ", gameNum=" + gameNum + ", hoursPerGame=" + hoursPerGame + ", amigos=" + amigos
				+ ", juegos=" + juegos + "]";
	}
	
}
