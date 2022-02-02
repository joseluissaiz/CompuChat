package Marc.Model;

import Marc.Controller.Controller;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Thread.sleep;

public class ClientConnector implements Runnable {


    //Attributes


    private final Controller controller;
    private final Queue<String> ips = new LinkedList<>();


    //Constructor


    public ClientConnector(Controller controller) {
        this.controller = controller;
        new Thread(this).start();
    }


    //Methods


    @Override
    public void run() {
        while (true) {
            while (!ips.isEmpty()) {
                String ip = ips.poll();
                try {
                    Socket socket = new Socket(ip, controller.PORT);
                    controller.createConnection(socket);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void connectTo(String ip) {
        ips.add(ip);
    }
}
