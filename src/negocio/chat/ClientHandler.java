package negocio.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import negocio.User;

public class ClientHandler {

	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	
	public Socket socket;
	public ObjectOutputStream oos;
	public ObjectInputStream ois;
	public User user;
	
	public ClientHandler(Socket socket) {
		try {
			
			this.socket = socket;
			this.oos = new ObjectOutputStream(socket.getOutputStream());
			this.ois = new ObjectInputStream(socket.getInputStream());
			
			this.user = (User) ois.readObject();
			
			messageListener();
			
			clientHandlers.add(this);
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			closeHandler(socket, oos, ois);
		}
	}
	
	public void messageListener() {
		Thread listener = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while (socket.isConnected()) {
					try {
						
						Message messageFromClient = (Message) ois.readObject();
						
						System.out.println("[HANDLER] New message received: " + messageFromClient);
						
						sendToReceiver(messageFromClient);
						
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
						closeHandler(socket, oos, ois);
						break;
					}
				}
				
			}
		});
		
		listener.start();
	}
	
	public void sendToReceiver(Message message) {
		try {
			ClientHandler receiver = getReceiverHandler(message.to);
			
			if (receiver == null) {
				System.out.println("[HANDLER] " + message.to.getUsername() + " is offline");
				
				this.oos.writeObject(message);
			} else {
				System.out.println("[HANDLER] " + receiver.user.getUsername() + " is online. Sending message from " + message.from.getUsername());
				
				this.oos.writeObject(message);
				receiver.oos.writeObject(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
			closeHandler(socket, oos, ois);
		}
	}
	
	public void closeHandler(Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
		try {
			if (socket != null) {
				socket.close();
			}
			if (oos != null) {
				oos.close();
			}
			if (ois != null) {
				ois.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		removeHandler();
	}
	
	public void removeHandler() {
		clientHandlers.remove(this);
	}
	
	public ClientHandler getReceiverHandler(User receiver) {
		for (ClientHandler ch : clientHandlers) {
			if (ch.user.equals(receiver)) {
				return ch;
			}
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "ClientHandler [socket=" + socket + ", user=" + user + "]";
	}

}
