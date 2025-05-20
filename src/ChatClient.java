import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient implements User {

    private int port;
    private Socket clientSocket;
    private String IPaddress;
    private String user;
    private BufferedReader input;
    private PrintWriter output;

    public ChatClient(int port, String IPaddress, String user) {
        this.port = port;
        this.IPaddress = IPaddress;
        this.user = user;

        try {
            this.clientSocket = new Socket(IPaddress, port);
            this.output = new PrintWriter(clientSocket.getOutputStream(), true);
            this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Client connected at IP: " + IPaddress + " on port: " + port);
        } catch (Exception e) {
            throw new RuntimeException("Error connecting to server: " + e.getMessage());
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
            throw new RuntimeException("Error reading message: " + e.getMessage());
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
}
