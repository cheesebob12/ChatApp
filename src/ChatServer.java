import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {
    private static final int PORT = 12346; // Port number for the server
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

    // Inner class to handle each client
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader input;
        private PrintWriter output;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("Error setting up client: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = input.readLine()) != null) {
                    System.out.println("Message: " + message);
                    sendMessageToAll(message);
                }
            } catch (IOException e) {
                System.out.println("Client disconnected: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e.getMessage());
                }
                clients.remove(this);
            }
        }

        // Send message to all clients
        private void sendMessageToAll(String message) {
            for (ClientHandler client : clients) {
                client.output.println(message);
            }
        }
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer(PORT);
        server.startServer();
    }
}
