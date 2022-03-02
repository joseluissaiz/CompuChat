package Marc.Model;

import java.util.HashMap;

import static Marc.Model.Connection.running;

public class ConnectionSaver extends Thread {

    public static HashMap<String, Connection> connections;

    public ConnectionSaver() {
        connections = Connection.connections;
        start();
    }

    @Override
    public void run() {
        while (running) {
            for (String k : connections.keySet()) {
                Connection c = connections.get(k);
                if(System.currentTimeMillis() - c.getLastPing() > 5000) {
                    String ip = c.IP;
                    c.close();
                } else {
                    c.writeData("-ping-");
                }
            }
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
