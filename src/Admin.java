import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Map;


public class Admin implements User
{

    private int port = 12346;
    private Socket AdminSocket;
    private String IPaddress;
    private String user;
    private BufferedReader input;
    private PrintWriter output;
    private ChatServer serverReference;

    public Admin(int port, String IPaddress, String user, ChatServer serverReference)
    {
        this.port = port;
        this.IPaddress = IPaddress;
        this.user = user;
        this.serverReference = serverReference;
    }

    @Override
    public void sendMsg(String message)
    {
        output.println(user + ": " + message);
    }
    @Override
    public String getMsg()
    {
        try
        {
            return input.readLine();
        }
        catch (Exception e)
        { throw new RuntimeException("error reading message"); }

    }
    @Override
    public void setPort(int port)
    {
        this.port = port;
    }
    @Override
    public int getPort()
    {
        return port;
    }

    @Override
    public void setUser(String user)
    {
        this.user = user;
    }
    @Override
    public String getUser()
    {
        return user;
    }

    @Override
    public void setIPaddress(String IPaddress)
    {
        this.IPaddress = IPaddress;
    }
    @Override
    public String getIPaddress()
    {
        return IPaddress;
    }
}






