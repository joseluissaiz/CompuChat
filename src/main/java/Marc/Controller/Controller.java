package Marc.Controller;

import Marc.Model.ClientConnector;
import Marc.Model.Connection;
import Marc.Model.Server;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Controller {


    //Attributes


    public final String IP;
    public final int PORT = 7070;

    public final HashMap<String, Connection> connections = new HashMap<>();
    public final Server server;
    public final ClientConnector clientConnector;


    //Constructor


    public Controller() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        IP = ip;
        server = new Server(this);
        clientConnector = new ClientConnector(this);
        runThreads();
    }


    //Methods


    private void runThreads() {
        new Thread(server).start();
        new Thread(clientConnector).start();
    }

    public void addNewConnection(String ip) {
        new Thread(()->{
            Connection c = clientConnector.connectTo(ip);
            connections.put(c.IP,c);
            new Thread(c).start();
        }).start();
    }

    public void addNewConnection(Socket s) {
        new Thread(()->{
            Connection c = new Connection(s);
            connections.put(c.IP,c);
            new Thread(c).start();
        }).start();
    }

    public boolean isValidIP (String ip) {
        try {
            if ( ip == null || ip.isBlank() ) { return false; }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) { return false; }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }

            if ( ip.endsWith(".") ) { return false; }
            return true;

        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
