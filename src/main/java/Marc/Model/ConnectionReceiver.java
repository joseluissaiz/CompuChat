package Marc.Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static Marc.Model.Connection.running;

public class ConnectionReceiver extends ServerSocket implements Runnable {


    //Constructor


    public ConnectionReceiver() throws IOException {
        super(Connection.PORT);
        new Thread(this).start();
    }


    //Methods


    @Override
    public void run() {
        while (running) {
            Socket socket = accept();
            Connection.createConnection(socket);
        }
    }

    public Socket accept() {
        try {
            return super.accept();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }


}
