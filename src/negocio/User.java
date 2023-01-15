package negocio;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.StringTokenizer;

import gui.VLogin;
import gui.customComponents.DPanelTienda;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected int id;
	protected String username;
	protected String email;
	protected String password;
	protected Country country;
	protected Date lastTimePlayed;
	protected int totalTimePlayed;
	protected double balance;
	protected ArrayList<Integer> friends;
	protected ArrayList<Game> games;
	protected ArrayList<Merch> merch;
	
	/** Creates a new User class object
	 * @param id					Integer of the user id
	 * @param username				String of the username
	 * @param email					String of the email
	 * @param password				String of the password
	 * @param country				Enum of {@link #Country}
	 * @param lastTimePlayed		Date of the last time played
	 * @param totalTimePlayed		Integer of the total time played, in miliseconds
	 * @param friends				List<Integer> with ids of the user's friends
	 * @param games					List<Games> with games of the user
	 * @param merch					List<Merch> with merch of the user
	 */
	public User(int id, String username, String email, String password, Country country, Date lastTimePlayed,
			int totalTimePlayed, double balance, ArrayList<Integer> friends, ArrayList<Game> games, ArrayList<Merch> merch) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.country = country;
		this.lastTimePlayed = lastTimePlayed;
		this.totalTimePlayed = totalTimePlayed;
		this.balance = balance;
		this.friends = friends;
		this.games = games;
		this.merch = merch;
	}
	
	/** Creates a new User class object with default values
	 */
	public User() {
		super();
		this.id = 0;
		this.username = "";
		this.email = "";
		this.password = "";
		this.country = Country.Spain;
		this.lastTimePlayed = null;
		this.totalTimePlayed = 0;
		this.balance = 0.0;
		this.friends = null;
		this.games = null;
		this.merch = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Game> getGames() {
		return games;
	}

	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}
	
	public ArrayList<Merch> getMerch() {
		return merch;
	}

	public void setMerch(ArrayList<Merch> merch) {
		this.merch = merch;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public void setCountry(String country) {
		this.country = Country.valueOf(country);
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public ArrayList<Integer> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<Integer> friends) {
		this.friends = friends;
	}

	public Date getLastTimePlayed() {
		return lastTimePlayed;
	}
	
	public String getLastTimePlayedFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		return sdf.format(this.getLastTimePlayed());
	}

	public void setLastTimePlayed(Date lastTimePlayed) {
		this.lastTimePlayed = lastTimePlayed;
	}
	
	@SuppressWarnings("deprecation")
	public void setLastTimePlayed(String lastTimePlayed) {
		StringTokenizer st = new StringTokenizer(lastTimePlayed, " -:");
		ArrayList<String> values = new ArrayList<>();
		
		while (st.hasMoreTokens()) {
	         values.add(st.nextToken());
	     }
		
		this.setLastTimePlayed(new Date(Integer.parseInt(values.get(2)), Integer.parseInt(values.get(1)), Integer.parseInt(values.get(0)),
				Integer.parseInt(values.get(3)), Integer.parseInt(values.get(4))));
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
		User other = (User) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "id="+ id +", "+ username ;
				
//		"Usuario [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
//		+ ", country=" + country + ", lastTimePlayed=" + lastTimePlayed + ", totalTimePlayed=" + totalTimePlayed
//		+ ", balance=" + balance + ", friends=" + friends + ", games=" + games + ", merch=" + merch + "]";
	}
	
	public void addGame(Game game) {
		if (!this.getGames().contains(game)) {
			VLogin.loggedUser.setBalance(VLogin.loggedUser.getBalance() - game.getPrice());
			VLogin.dbManager.insertDataPropertyGames(VLogin.loggedUser, game);
			this.games.add(game);
		}
	}
	
	public void addMerch(Merch merch) {
		if (!this.getMerch().contains(merch)) {
			VLogin.loggedUser.setBalance(VLogin.loggedUser.getBalance() - merch.getPrice());
			VLogin.dbManager.insertDataPropertyMerch(VLogin.loggedUser, merch);
			this.merch.add(merch);
		}
	}
	
}
