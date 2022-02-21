package Jose;

import java.util.ArrayList;
import java.util.List;

public class P2PTask {
    private ServerConnector serverConnector;
    private ClientConnector clientConnector;
    private static P2PTask instance;
    private List<Connection> connectionList;

    private P2PTask() {connectionList = new ArrayList<>();}

    public static P2PTask getInstance() {
        if (instance == null) {
            instance = new P2PTask();
        }
        return instance;
    }

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu(P2PTask.getInstance());
    }

    public void createServerListener() {
        serverConnector = new ServerConnector(8060, connectionList);
    }

    public void createClientConnection(String  ip) {
        clientConnector = new ClientConnector(ip, connectionList);
    }

    // DTO

    public List<Connection> getConnectionList() {
        return connectionList;
    }
}
