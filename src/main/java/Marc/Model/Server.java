package Marc.Model;

import Marc.Controller.Controller;
import jdk.swing.interop.SwingInterOpUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {


    //Attributes


    private final String IP;
    private final int PORT;

    private final Controller controller;
    public final ServerSocket serverSocket;


    //Constructor


    public Server(Controller controller) throws IOException {
        this.IP = controller.IP;
        this.PORT = controller.PORT;
        this.controller = controller;
        this.serverSocket = new ServerSocket(PORT);
        new Thread(this).start();
    }


    //Methods


    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress().getHostAddress());
                controller.createConnection(socket);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
