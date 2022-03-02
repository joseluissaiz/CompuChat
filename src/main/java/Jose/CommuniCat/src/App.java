package Jose.CommuniCat.src;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;
import java.util.ArrayList;

public class App extends JFrame {

  private final int PORT = 7070;

  private ClientConnector clientConnector;
  private ServerConnector serverConnector;
  private ArrayList<Connection> connections;

  private ConnectionsPanel connectionsPanel;

  public App() {
    init();
    setLayout(new GridBagLayout());
    createWindowComponents();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(1000,500);
    setResizable(false);
    setVisible(true);
  }

  private void createWindowComponents() {
    connectionsPanel = new ConnectionsPanel(this);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.weighty = 1f;
    gbc.weightx = 1f;
    gbc.fill = GridBagConstraints.BOTH;
    this.add(connectionsPanel, gbc);
  }

  private void init() {
    connections = new ArrayList<>();
    clientConnector = new ClientConnector(PORT, this);
    serverConnector = new ServerConnector(PORT, this);
  }

  public static void main(String[] args) {
    new App();
  }

  public void createConnection(String ip) {
    clientConnector.createConnection(ip);
  }

  public void createConnection(Socket socket) {
    Connection connection = new Connection(socket, this);
    connections.add(connection);
    connectionsPanel.addClientTab(connection);
    connectionsPanel.setCurrentTab(connection);
  }

  public void receiveMessage(String message, Connection connection) {
    connectionsPanel.writeMessage(message, connection.getSocket().getInetAddress().getHostAddress());
  }

}
