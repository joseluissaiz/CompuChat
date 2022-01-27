import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Connection implements Runnable {
    private Socket socket;
    private String ip;
    private int port;
    private BufferedReader bufferReader;
    private OutputStream outputStream;

    public Connection(Socket socket) {
        this.socket = socket;
        this.ip = socket.getInetAddress().getHostAddress();
        this.port = socket.getPort();
        System.out.printf("Connection created with %s on port %n\n",ip, port);
        createBuffers();
    }

    private void createBuffers() {
        // Get the buffers from the socket
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferReader = new BufferedReader(inputStreamReader);
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        // Closing all buffers and the socket
        try {
            bufferReader.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            //Can't close any buffer or connection
            System.err.println("Could not close any socket or connection");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(!socket.isClosed()) {
            readMessages();
        }
    }

    private void readMessages() {
        String line = "";
        String buffer;

        // Read our message
        try {
            while((buffer = bufferReader.readLine()) != null){
                line = line.concat(buffer);
            }
            // Print the result
            System.out.println(ip+": "+line);
        } catch (IOException e) {
            // Can't read that line
            System.err.println("Could not read line in buffered reader");
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        // Send message to the socket ip destination
        try {
            outputStream.write(message.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
