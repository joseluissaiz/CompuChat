package Marc.Model;

import Marc.Controller.Controller;

import java.io.IOException;
import java.net.Socket;

public class ClientConnector implements Runnable {


    //Attributes


    private final Controller controller;


    //Constructor


    public ClientConnector(Controller controller) {
        this.controller = controller;
    }


    //Methods


    @Override
    public void run() {

    }

    public Connection connectTo(String ip) {
        try {
            return new Connection(new Socket(ip,controller.PORT));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}
