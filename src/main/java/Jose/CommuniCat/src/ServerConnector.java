package Jose.CommuniCat.src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnector extends Thread {

  private int port;
  private ServerSocket serverSocket;
  private App app;

  public ServerConnector(int port, App app) {
    this.port = port;
    this.app = app;
    setServerSocket();
    start();
  }

  private void setServerSocket() {
    try {
      this.serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      System.err.println("Couldn't create server socket for port " + port);
      e.printStackTrace();
    }
  }

  private void acceptConnections() {
    try {
      System.out.println("Accepting requests...");
      Socket connectionSocket = serverSocket.accept();
      createConnection(connectionSocket);
    } catch (IOException e) {
      System.err.println("Cannot create connection : Accept error");
      e.printStackTrace();
    }
  }

  private void createConnection(Socket connectionSocket) {
    if (connectionSocket != null) {
      app.createConnection(connectionSocket);
    }
  }

  @Override
  public void run() {
    super.run();
    if (serverSocket != null) {
      while (this.isAlive()) {
        acceptConnections();
      }
    }
  }
}
