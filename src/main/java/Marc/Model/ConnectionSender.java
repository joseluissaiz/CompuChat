package Marc.Model;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static Marc.Model.Connection.running;

public class ConnectionSender extends Thread {


    //Attributes



    private final ArrayList<String> ips = new ArrayList<>();


    //Constructor


    public ConnectionSender() {
        this.start();
    }


    //Methods


    @Override
    public void run() {
        while (running) {
            for (String ip : ips) {
                createConnection(ip);
                if (Connection.connections.get(ip) != null) ips.remove(ip);
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createConnection(String ip) {
        try {
            Socket socket = new Socket(ip, Connection.PORT);
            Connection.createConnection(socket);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public synchronized void connectTo(String ip) {
        ips.add(ip);
    }

}
