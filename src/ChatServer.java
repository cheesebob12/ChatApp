import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {

    private int port = 12346; // Default port
    private ServerSocket serverSocket;
    private static final CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    // Constructor
    public ChatServer(int port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }

    // Start accepting clients
    public void startServer() {
        System.out.println("Waiting for clients...");
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                System.out.println("Error accepting client: " + e.getMessage());
            }
        }
    }

    // Allow updating config (currently only port is meaningful)
    public void updateConfig(int port) {
        this.port = port;
        System.out.println("Server port updated to: " + port);
        // Note: You would need to restart the server socket for the new port to take effect.
    }

    // Broadcast message to all clients
    public void sendMessageToAll(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    // Optional: Kick a user (used by Admin)
    public void kickClient(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equalsIgnoreCase(username)) {
                client.sendMessage("You have been kicked by the Admin.");
                client.disconnect();
                clients.remove(client);
                System.out.println("User " + username + " has been kicked.");
                break;
            }
        }
    }

    // Remove client cleanly
    public void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client disconnected: " + client.getUsername());
    }
}
