package Marc.View;

import Marc.Controller.Controller;
import Marc.Model.ClientConnector;
import Marc.Model.Connection;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.awt.GridBagConstraints.BOTH;

public class Window extends JFrame {


    //Attributes


    private final Controller controller = new Controller();
    private final JButton create = new JButton("Create Chat");
    private final JTextField ipInput = new JTextField();
    private final JLabel inputLabel = new JLabel("Write the IP: ");
    private final JLabel myIp = new JLabel();
    private final JLabel alert = new JLabel("");
    private final JTabbedPane tabs = new JTabbedPane();


    //Constructor


    public Window() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        myIp.setText("IP: "+ip+" - Port: 7070");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addComponents(getContentPane());
        addListeners();
        setPreferredSize(new Dimension(600,600));

    }


    //Methods


    private void addChat(String ip) {
        ChatPanel chat = new ChatPanel(controller.connections.get(ip));
        tabs.addTab(ip, chat);
        tabs.setSelectedIndex(tabs.getTabCount()-1);
        new Thread(chat).start();
    }

    private void addComponents(Container p) {
        p.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = BOTH;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(5,5,5,5);

        add(myIp, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(inputLabel, gbc);

        gbc.weightx = 1;
        gbc.gridx = 1;
        add(ipInput,gbc);

        gbc.weightx = 0;
        gbc.gridx = 2;
        add(create,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(alert,gbc);

        gbc.gridy = 3;
        gbc.weighty = 1;
        add(tabs,gbc);

    }

    private void addListeners() {
        create.addActionListener(e -> {
            String ip = ipInput.getText();
            if (controller.isValidIP(ip)) {
                alert.setText("is a not valid IP");
                return;
            } else {
                controller.addNewConnection(ip);
                addChat(ip);
                ipInput.setText("");
                alert.setText("");
            }

        });
    }

    public void open() {
        setVisible(true);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
