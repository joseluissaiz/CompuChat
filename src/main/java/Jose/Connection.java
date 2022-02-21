package Jose;

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
        System.out.printf("Jose.Connection created with %s on port %n\n",ip, port);
        createBuffers();
        sendMessage("Hola apruebame porfis");
    }

    private void createBuffers() {
        // Get the buffers from the socket
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferReader = new BufferedReader(inputStreamReader);
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            System.err.println("Cannot create the buffers");
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
        String input;

        // Read our message
        try {
            while(!(input = bufferReader.readLine()).isEmpty()){
                // Print the result
                System.out.println(ip+": "+input);
            }
        } catch (IOException e) {
            // Can't read that line
            System.err.println("Could not read line in buffered reader");
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        // Send message to the socket ip destination
        try {
            System.out.println("Trying to send message...");
            outputStream.write(message.getBytes());
            outputStream.flush();
            System.out.println("Sended");
        } catch (IOException e) {
            System.err.println("Couldn't send the message");
            e.printStackTrace();
        }
    }

}
