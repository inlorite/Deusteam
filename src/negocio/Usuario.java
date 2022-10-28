package negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Usuario {
	
	protected int id;
	protected String username;
	protected String email;
	protected String passsword;
	protected String country;
	protected Date lastTimePlayed;
	protected int totalTimePlayed;
	protected ArrayList<Integer> amigos;
	protected ArrayList<Juego> juegos;
	


	public Usuario(int id, String username, String email, String passsword, String country, Date lastTimePlayed,
			int totalTimePlayed, ArrayList<Integer> amigos, ArrayList<Juego> juegos) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.passsword = passsword;
		this.country = country;
		this.lastTimePlayed = lastTimePlayed;
		this.totalTimePlayed = totalTimePlayed;
		this.amigos = amigos;
		this.juegos = juegos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
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

	public ArrayList<Integer> getAmigos() {
		return amigos;
	}

	public void setAmigos(ArrayList<Integer> amigos) {
		this.amigos = amigos;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLastTimePlayed() {
		return lastTimePlayed;
	}

	public void setLastTimePlayed(Date lastTimePlayed) {
		this.lastTimePlayed = lastTimePlayed;
	}

	public int getTotalTimePlayed() {
		return totalTimePlayed;
	}

	public void setTotalTimePlayed(int totalTimePlayed) {
		this.totalTimePlayed = totalTimePlayed;
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
		return "Usuario [id=" + id + ", username=" + username + ", email=" + email + ", passsword=" + passsword
				+ ", country=" + country + ", lastTimePlayed=" + lastTimePlayed + ", totalTimePlayed=" + totalTimePlayed
				+ ", amigos=" + amigos + ", juegos=" + juegos + "]";
	}

	
}
