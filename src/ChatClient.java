import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class ChatClient implements User {

    private int port = 12346;
    private Socket clientSocket;
    private String IPaddress;
    private String user;
    private BufferedReader input;
    private PrintWriter output;

   public ChatClient(int port, String IPaddress, String user)
    {
        this.port = port;
        this.IPaddress = IPaddress;
       try{ this.clientSocket = new Socket(IPaddress, port);

            this.output = new PrintWriter(clientSocket.getOutputStream(), true);
            this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Client connected at IPaddress: " + IPaddress + "port: " + port);
       }
       catch (Exception e) {
           throw new RuntimeException("error connecting to server"+ e.getMessage());
       }
        this.user = user;
    }
    @Override
    public void sendMsg(String message)
    {
        output.println(user + ": " + message);
    }
    @Override
    public String getMsg()
    {
        try {
            return input.readLine();
        }
        catch (Exception e) { throw new RuntimeException("error reading message"); }

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
