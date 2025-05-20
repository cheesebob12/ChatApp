import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Admin implements User {

    private int port;
    private Socket adminSocket;
    private String IPaddress;
    private String user;
    private BufferedReader input;
    private PrintWriter output;
    private ChatServer serverReference;

    public Admin(int port, String IPaddress, String user, ChatServer serverReference) {
        this.port = port;
        this.IPaddress = IPaddress;
        this.user = user;
        this.serverReference = serverReference;

        try {
            // Establish connection to server
            adminSocket = new Socket(IPaddress, port);
            input = new BufferedReader(new InputStreamReader(adminSocket.getInputStream()));
            output = new PrintWriter(adminSocket.getOutputStream(), true);
        } catch (Exception e) {
            System.err.println("Failed to connect admin to server: " + e.getMessage());
        }
    }

    @Override
    public void sendMsg(String message) {
        if (output != null) {
            output.println(user + ": " + message);
        }
    }

    @Override
    public String getMsg() {
        try {
            return input.readLine();
        } catch (Exception e) {
            throw new RuntimeException("Error reading message from server.");
        }
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setIPaddress(String IPaddress) {
        this.IPaddress = IPaddress;
    }

    @Override
    public String getIPaddress() {
        return IPaddress;
    }

    // Optional: admin-specific functionality like kicking users
    public void kickUser(String targetUsername) {
        if (serverReference != null) {
            serverReference.kickClient(targetUsername);
        }
    }
}
