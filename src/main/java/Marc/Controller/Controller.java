package Marc.Controller;

import Marc.Model.ClientConnector;
import Marc.Model.Connection;
import Marc.Model.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Controller {


    //Attributes


    public final String IP;
    public final int PORT;

    public final HashMap<String, Connection> connections = new HashMap<>();
    public final Server server;
    public final ClientConnector clientConnector;


    //Constructor


    public Controller(int port) throws IOException {
        this.server = new Server(this);
        this.PORT = port;
        this.IP = server.serverSocket.getInetAddress().getHostAddress();
        this.clientConnector = new ClientConnector(this);
    }


    //Methods


    public void createConnection(Socket socket) {
        if (socket == null) {return;}
        try {
            Connection connection = new Connection(socket);
            connections.put(connection.IP, connection);
        } catch (IOException ioException) {
        }
    }

    public boolean isValidIP (String ip) {
        ip = ip.trim();
        if (ip.isEmpty()) {return false;}
        String[] s = ip.split("\\.");
        if (s.length != 4) {return false;}
        for (int i = 0; ++i<4;) {
            try {
                int b = Integer.parseInt(s[i]);
                if (b > 255 || b < 0) {return false;}
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
