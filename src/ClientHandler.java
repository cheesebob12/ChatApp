import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final ChatServer server;
    private PrintWriter output;
    private BufferedReader input;
    private String username;

    public ClientHandler(Socket clientSocket, ChatServer server) {
        this.clientSocket = clientSocket;
        this.server = server;

        try {
            this.output = new PrintWriter(clientSocket.getOutputStream(), true);
            this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.username = input.readLine(); // First line expected: username
            System.out.println("Username received: " + username);
            server.sendMessageToAll(username + " has joined the chat.");
        } catch (IOException e) {
            System.err.println("Error setting up client I/O: " + e.getMessage());
            disconnect();
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = input.readLine()) != null) {
                if (!message.trim().isEmpty()) {
                    System.out.println(username + ": " + message);
                    server.sendMessageToAll(message);
                }
            }
        } catch (IOException e) {
            System.out.println(username + " disconnected.");
        } finally {
            disconnect();
        }
    }

    public String getUsername() {
        return username;
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    public void disconnect() {
        try {
            if (output != null) output.close();
            if (input != null) input.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error disconnecting client: " + e.getMessage());
        } finally {
            server.removeClient(this);
        }
    }
}
