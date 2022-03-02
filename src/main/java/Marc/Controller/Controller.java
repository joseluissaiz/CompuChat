package Marc.Controller;

import Marc.Model.Connection;
import Marc.Model.ConnectionReceiver;
import Marc.Model.ConnectionSender;
import Marc.View.ChatPanel;
import Marc.View.ChatTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class Controller {


    //Attributes


    public ConnectionReceiver connectionReceiver = new ConnectionReceiver();
    public ConnectionSender connectionSender = new ConnectionSender();

    public final ChatPanel chat = new ChatPanel("IP - " + InetAddress.getLocalHost().getHostAddress(),
            "Receiver", "IP: ", "PORT: ", "Send");


    //Constructor


    public Controller() throws IOException {
        chat.addSubmitListener((ip, port) -> {
            try {
                Connection.createConnection(new Socket(ip,Connection.PORT));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        Connection.addOnConnectionCreatedListener( connection -> {
            createTab(connection.IP);
            connection.addOnConnectionLostListener(() -> chat.closeTab(connection.IP));
            connection.addOnDataReceivedListener(d -> chat.getTab(connection.IP).writeLine(String.valueOf(d)));
        });
    }

    public void createTab(String ip) {
        chat.addTab(ip);
        ChatTab tab = chat.getTab(ip);
        tab.addSendListener(d -> Connection.connections.get(ip).writeData(d));
        tab.addCloseListener(i -> Connection.connections.get(ip).close());
    }

    public void launch() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setExtendedState(Frame.MAXIMIZED_BOTH);
        f.setMinimumSize(new Dimension(800, 500));
        f.getContentPane().add(chat);
        f.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                Connection.running = false;
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        f.setVisible(true);
    }


}
