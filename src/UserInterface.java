public interface User {
    // Constant port value
    int DEFAULT_PORT = 12346;

    // Required methods
    void setUser(String user);
    String getUser();

    void setPort(int port);
    int getPort();

    void setIPaddress(String IPaddress);
    String getIPaddress();

    void sendMsg(String message);
    String getMsg();
}
