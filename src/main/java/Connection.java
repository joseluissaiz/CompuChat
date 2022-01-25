import java.net.Socket;

public class Connection {
    private Socket socket;
    private String ip;
    private int port;

    public Connection(Socket socket) {
        this.socket = socket;
        this.ip = socket.getInetAddress().getHostAddress();
        this.port = socket.getPort();
        System.out.printf("Connection created with %s on port %n\n",ip, port);
    }

}
