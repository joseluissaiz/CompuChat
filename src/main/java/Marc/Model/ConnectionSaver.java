package Marc.Model;

import java.util.HashMap;

public class ConnectionSaver extends Thread {

    public static HashMap<String, Connection> connections;

    public ConnectionSaver() {
        connections = Connection.connections;
        start();
    }

    @Override
    public void run() {
        for (String k : connections.keySet()) {
            Connection c = connections.get(k);
            if(System.currentTimeMillis() - c.getLastPing() > 5000) {
                String ip = c.IP;
                c.close();
                Connection.connectionSender.connectTo(ip);
            } else {
                c.writeData("00");
            }
        }
    }


}
