import java.awt.*;
import java.io.*;
import java.net.*;


interface User
{
    public final int port = 12346;
    public Socket socket = new Socket();
    public String user ="user";
    public BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    public PrintWriter output = null;
    public String IPaddress = "0.0.0.0";

    public void setUser(String user);
    public String getUser();

    public void setPort(int port);
    public int getPort();

    public void setIPaddress(String IPaddress);
    public String getIPaddress();

    public void sendMsg(String message);
    public String getMsg();

}
