package negocio.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static final int PORT = 4321;
	public ServerSocket serverSocket;
	
	public Server() {
		try {
			
			this.serverSocket = new ServerSocket(PORT);
			
			startServer();
			
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	public void startServer() {
		
		System.out.println("[SERVER] Server running on port: " + PORT);
		
		while (!serverSocket.isClosed()) {
			try {
				
				Socket socket = serverSocket.accept(); // the program is halted here until someone (a new client) joins
				
				ClientHandler clientHandler = new ClientHandler(socket);
				
				System.out.println("[SERVER] " + clientHandler.user.getUsername() + " has connected!");
				System.out.println(ClientHandler.clientHandlers);
				
			} catch (IOException e) {
				//e.printStackTrace();
				closeServer();
			}
		}
	}
	
	public void closeServer() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Server();
	}
	
}
