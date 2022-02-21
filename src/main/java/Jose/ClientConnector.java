package Jose;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientConnector {
    private Socket clientSocket;
    private String ip;
    private final int port = 8060;
    private List<Connection> connections;

    public ClientConnector(String ip, List<Connection> connections) {
        this.ip = ip;
        this.connections = connections;
        openPort();
    }

    private void openPort() {
        if (clientSocket != null) {
            System.err.println("Client socket is already created");
        }
        // Connect to the server
        try {
            clientSocket = new Socket(ip, port);
            Connection connection = new Connection(clientSocket);
            System.out.printf("Connected succesfully with server %s on port %n\n", ip, port);
        } catch (IOException e) {
            System.err.printf("Cannot connect to server with ip %s on port %n\n", ip, port);
            e.printStackTrace();
        }
    }

}
