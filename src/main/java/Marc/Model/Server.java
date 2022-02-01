package Marc.Model;

import Marc.Controller.Controller;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable {


    //Attributes


    private final int PORT;

    private final Controller controller;
    private final ServerSocket serverSocket;


    //Constructor


    public Server(Controller controller) {
        ServerSocket ss = null;
        this.controller = controller;
        PORT = controller.PORT;
        try {
            ss = new ServerSocket(PORT);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        serverSocket = ss;
    }


    //Methods


    @Override
    public void run() {
        while (true) {
            try {
                controller.addNewConnection(serverSocket.accept());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
