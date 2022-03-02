package Jose.CommuniCat.src;

import java.io.IOException;
import java.net.Socket;

public class ClientConnector {

  private App app;
  private int port;

  public ClientConnector( int port, App app) {
    this.port = port;
    this.app = app;
  }

  public void createConnection(String ip) {
    new Thread(() -> {
      try {
        System.out.println("Connecting to "+ip+" with port "+port);
        Socket connectionSocket = new Socket(ip, port);
        app.createConnection(connectionSocket);
      } catch (IOException e) {
        System.err.println("Couldn't create socket for ip "+ip+" and port "+port);
        e.printStackTrace();
      }
    }).start();
  }
}
