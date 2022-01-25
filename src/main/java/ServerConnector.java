import java.io.IOException;
import java.net.ServerSocket;

public class ServerConnector implements Runnable {
    private ServerSocket serverSocket;
    private int port;
    private P2PTask mainTask;

    public ServerConnector(int port, P2PTask mainTask) {
        this.port = port;
        this.mainTask = mainTask;
        openPort();
    }

    private void openPort() {
        // Don't do anything if the server socket is already opened.
        if (serverSocket != null) {
            System.out.println("Server socket already opened");
            return;
        }
        // Open our server socket.
        try {
            serverSocket = new ServerSocket(port);
            System.out.printf("ServerSocket opened on port %n\n", port);
            System.out.println("Listening to connections...");
            run();
        } catch (IOException e) {
            // Can't be opened.
            System.err.printf("Could not open server socket on port %n\n", port);
            e.printStackTrace();
        }
    }

    private void closePort() {
        // Return if there's no server socket yet.
        if (serverSocket == null) {
            return;
        }

        // Close our server socket.
        try {
            serverSocket.close();
        } catch (IOException e) {
            // Can't be closed.
            System.err.printf("Can't close Server socket on port %n\n", port);
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            getRequests();
        }
    }

    private void getRequests() {
        try {
            mainTask.getConnectionList().add(new Connection(serverSocket.accept()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
