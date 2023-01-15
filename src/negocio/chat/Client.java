package negocio.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.DefaultListModel;

import negocio.User;

public class Client {
	
	public static final String HOST = "localhost";
	public Socket socket;
	public ObjectOutputStream oos;
	public ObjectInputStream ois;
	public User user;
	public DefaultListModel<Message> dlmChatbox;
	
	public Client(User user, DefaultListModel<Message> dlmChatbox) {
		try {
			
			this.user = user;
			this.dlmChatbox = dlmChatbox;
			this.socket = new Socket(HOST, Server.PORT);
			this.oos = new ObjectOutputStream(socket.getOutputStream());
			this.ois = new ObjectInputStream(socket.getInputStream());
			
			oos.writeObject(user);
			
			listenForMessage();
			
		} catch (IOException e) {
			//e.printStackTrace();
			closeClient(socket, oos, ois);
		}
	}
	
	public void sendMessage(Message messageToSend) {
		try {
			oos.writeObject(messageToSend);
		} catch (IOException e) {
			//e.printStackTrace();
			closeClient(socket, oos, ois);
		}
	}
	
	public void listenForMessage() {
		Thread listener = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while (socket.isConnected()) {
					try {
						
						Message messageFromChat = (Message) ois.readObject();
						
						dlmChatbox.addElement(messageFromChat);
						
					} catch (IOException | ClassNotFoundException e) {
						//e.printStackTrace();
						closeClient(socket, oos, ois);
					}
				}
				
			}
		});
		
		listener.start();
	}
	
	public void closeClient(Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
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
			//e.printStackTrace();
		}
	}
	
}
