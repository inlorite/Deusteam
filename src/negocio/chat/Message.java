package negocio.chat;

import java.io.Serializable;

import negocio.User;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected User from;
	protected User to;
	protected String message;
	protected long date;
	
	public Message(User from, User to, String message, long date) {
		super();
		this.from = from;
		this.to = to;
		this.message = message;
		this.date = date;
	}

	public User getFrom() {
		return from;
	}

	public User getTo() {
		return to;
	}

	public String getMessage() {
		return message;
	}

	public long getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Message [from=" + from + ", to=" + to + ", message=" + message + ", date=" + date + "]";
	}
	
}
