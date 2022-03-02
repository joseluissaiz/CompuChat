package Jose.CommuniCat.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {

  private Socket socket;
  private App app;

  public Connection(Socket connectionSocket, App app) {
    this.socket = connectionSocket;
    this.app = app;
    System.out.println("Connection established with "+socket.getInetAddress().getHostAddress()+" successfully");
    start();
    sendMessage("Hi, I'm "+socket.getInetAddress().getHostAddress()+", nice to meet you!");
  }

  @Override
  public void run() {
    super.run();
    while (isAlive()) {
      System.out.println("Waiting to receive a message...");
      receiveMessage();
    }
  }

  private void receiveMessage() {
    try {
      DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
      String message = dataInputStream.readUTF();
      System.out.println(message);
      app.receiveMessage(message, this);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public boolean sendMessage(String message) {
    try {
      DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
      dataOutputStream.writeUTF(message);
      dataOutputStream.flush();
      System.out.println("sended");
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public Socket getSocket() {return socket;}

}
