import java.io.IOException;
import java.net.Socket;

public class ClientConnector {
    private Socket clientSocket;
    private String ip;
    private int port;

    public ClientConnector(String ip, int port) {
        this.ip = ip;
        this.port = port;
        openPort();
    }

    private void openPort() {
        if (clientSocket != null) {
            System.err.println("Client socket is already created");
        }
        // Connect to the server
        try {
            clientSocket = new Socket(ip, port);
            System.out.printf("Connected succesfully with server %s on port %n\n", ip, port);
        } catch (IOException e) {
            System.err.printf("Cannot connect to server with ip %s on port %n\n", ip, port);
            e.printStackTrace();
        }
    }

}
