package Marc.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class Connection extends Thread {


    //Attributes


    //Static
    public static boolean running = true;
    public static final int PORT = 7070;
    public static final HashMap<String, Connection> connections = new HashMap<>();
    private static final ArrayList<onConnectionCreatedListener> onConnectionCreatedListeners = new ArrayList<>();

    public static ConnectionSender connectionSender = new ConnectionSender();
    public static ConnectionReceiver connectionReceiver;
    public static ConnectionSaver connectionSaver = new ConnectionSaver();

    static {
        try {
            connectionReceiver = new ConnectionReceiver();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    //Instance
    public final String IP;
    private boolean active = true;

    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;

    private final ArrayList<onDataReceivedListener> onDataReceivedListeners = new ArrayList<>();
    private final ArrayList<onConnectionLostListener> onConnectionLostListeners = new ArrayList<>();

    private long lastPing = System.currentTimeMillis();


    //Constructor


    private Connection(Socket socket) throws IOException {
        this.IP = socket.getInetAddress().getHostAddress();
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.start();
    }


    //Methods


    @Override
    public void run() {
        while (running && active) {
            String d = readData();
            System.out.println("Message received: "+d);
            if (d == null) continue;
            for (onDataReceivedListener listener : onDataReceivedListeners) listener.onDataReceived(d);
        }
        if (running) {
            for (onConnectionLostListener listener : onConnectionLostListeners) listener.onConnectionLost();
            connections.remove(IP);
            System.out.println("Connection Closed");
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public synchronized static void createConnection(Socket socket) {
        if (socket == null) return;
        if (connections.get(socket.getInetAddress().getHostAddress()) != null) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return;
        }
        try {

            Connection connection = new Connection(socket);
            connections.put(connection.IP, connection);
            for (onConnectionCreatedListener listener : onConnectionCreatedListeners)
                listener.onConnectionCreated(connection);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void addOnConnectionCreatedListener(onConnectionCreatedListener listener) {
        onConnectionCreatedListeners.add(listener);
    }

    public void addOnConnectionLostListener(onConnectionLostListener listener) {
        onConnectionLostListeners.add(listener);
    }

    public void addOnDataReceivedListener(onDataReceivedListener listener) {
        onDataReceivedListeners.add(listener);
    }

    public synchronized void close() {
        active = false;
    }

    public long getLastPing() {
        return lastPing;
    }

    public String readData() {
        String s = null;
        try {
            s = in.readUTF();
        } catch (SocketException e) {
            close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        if (s.equals("00")) {
            lastPing = System.currentTimeMillis();
        }

        return s;
    }

    public void writeData(String d) {
        try {
            System.out.println("Message sended: "+d);
            out.writeUTF(d);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    //Listeners


    public interface onConnectionCreatedListener {
        void onConnectionCreated(Connection connection);
    }

    public interface onConnectionLostListener {
        void onConnectionLost();
    }

    public interface onDataReceivedListener {
        void onDataReceived(String data);
    }

}
