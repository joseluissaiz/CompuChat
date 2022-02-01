package Marc.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Connection implements Runnable {


    //Attributes


    public final String IP;

    public final Queue<String> messageQueue = new LinkedList<>();
    private final DataInputStream input;
    private final DataOutputStream output;
    private final Socket socket;


    //Constructor


    public Connection(Socket socket) {
        this.socket = socket;
        IP = socket.getInetAddress().getHostAddress();
        DataInputStream i = null;
        DataOutputStream o = null;
        try {
            i = new DataInputStream(socket.getInputStream());
            o = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        input = i;
        output = o;
    }


    //Methods


    @Override
    public void run() {
        while (true) {
            String msg = null;
            try {
                msg = input.readUTF();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            messageQueue.add(msg);
        }
    }

    public void sendMessage(String msg) {
        try {
            output.writeUTF(msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
