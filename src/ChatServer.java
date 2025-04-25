import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {
    private static  int port = 12346; // Port number for the server
    private static CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>(); // List of clients
    private ServerSocket serverSocket;

    // Constructor to initialize the server
    public ChatServer(int port) {
        try {
            serverSocket = new ServerSocket(port); // Create server socket
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }

    // Start the server and listen for clients
    public void startServer() {
        System.out.println("Waiting for clients...");
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept(); // Accept new client
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start(); // Start client thread
            } catch (IOException e) {
                System.out.println("Error accepting client: " + e.getMessage());
            }
        }
    }
    public void UpdateConfig(int port, String newIPaddress)
    {
        this.port = port;
        this.IP

    }


        private void sendMessageToAll(String message) {
            for (ClientHandler client : clients) {
                client.output.println(message);
            }
        }
    }

}
