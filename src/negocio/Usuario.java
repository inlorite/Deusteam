package negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Usuario {
	
	protected int id;
	protected String username;
	protected String email;
	protected String passsword;
	protected Pais country;
	protected Date lastTimePlayed;
	protected int totalTimePlayed;
	protected ArrayList<Integer> friends;
	protected ArrayList<Juego> games;
	
	
	public Usuario(int id, String username, String email, String passsword, Pais country, Date lastTimePlayed,
			int totalTimePlayed, ArrayList<Integer> friends, ArrayList<Juego> games) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.passsword = passsword;
		this.country = country;
		this.lastTimePlayed = lastTimePlayed;
		this.totalTimePlayed = totalTimePlayed;
		this.friends = friends;
		this.games = games;
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

	public ArrayList<Juego> getgames() {
		return games;
	}

	public void setgames(ArrayList<Juego> games) {
		this.games = games;
	}
	

	public Pais getCountry() {
		return country;
	}

	public void setCountry(Pais country) {
		this.country = country;
	}

	public ArrayList<Integer> getfriends() {
		return friends;
	}

	public void setfriends(ArrayList<Integer> friends) {
		this.friends = friends;
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
				+ ", friends=" + friends + ", games=" + games + "]";
	}

	
}
