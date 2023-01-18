package negocio.chat;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Server {
	
	public static final int PORT = 4321;
	public ServerSocket serverSocket;
	
	public static Logger logger = Logger.getLogger("Deusteam server");
	
	public Server() {
		try (FileInputStream fis = new FileInputStream("conf/serverLogger.properties")) {
            LogManager.getLogManager().readConfiguration(fis);
        } catch (IOException e) {
            logger.log(Level.WARNING, "No se pudo cargar el fichero de configuracion del logger para el servidor.");
        }
		
		try {
			
			this.serverSocket = new ServerSocket(PORT);
			
			startServer();
			
		} catch (IOException e) {
			logger.log(Level.WARNING, String.format("* Error al iniciar el servidor: %s", e.getMessage()));
		}
	}
	
	public void startServer() {
		
		logger.log(Level.INFO, "[SERVER] Server running on port: " + PORT);
		
		while (!serverSocket.isClosed()) {
			try {
				
				Socket socket = serverSocket.accept(); // the program is halted here until someone (a new client) joins
				
				ClientHandler clientHandler = new ClientHandler(socket);
				
				logger.log(Level.INFO, "[SERVER] " + clientHandler.user.getUsername() + " has connected!");
				logger.log(Level.INFO, "[SERVER] CH List: " + ClientHandler.clientHandlers.toString());
				
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
