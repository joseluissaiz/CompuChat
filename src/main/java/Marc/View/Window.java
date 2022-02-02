package Marc.View;

import Marc.Controller.Controller;
import Marc.Model.Connection;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.awt.GridBagConstraints.BOTH;
import static java.lang.Thread.sleep;

public class Window extends JFrame implements Runnable {


    //Attributes


    private final Controller controller;
    private final JButton create = new JButton("Create Chat");
    private final JTextField ipInput = new JTextField();
    private final JLabel inputLabel = new JLabel("Write the IP: ");
    private final JLabel myIp = new JLabel();
    private final JLabel alert = new JLabel("");
    private final JTabbedPane tabs = new JTabbedPane();


    //Constructor


    public Window(Controller controller) {
        this.controller = controller;
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        myIp.setText("IP: " + ip + " - Port: 7070");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addComponents(getContentPane());
        addListeners();
        setPreferredSize(new Dimension(600, 600));
        alert.setForeground(Color.red);
    }


    //Methods


    @Override
    public void run() {
        while (true) {
            String chatIp = null;
            if (controller.connections.size() != tabs.getTabCount()) {
                for (String ip : controller.connections.keySet()) {
                    for (int i = 0; ++i< tabs.getTabCount();) {
                        chatIp = ((ChatPanel) tabs.getComponentAt(i)).connection.IP;
                        if (ip.equals(chatIp)) {
                            break;
                        }
                    }
                    if (!ip.equals(chatIp)) {
                        addChat(ip);
                    }
                }
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void addChat(String ip) {
        ChatPanel chat = new ChatPanel(controller, ip);
        tabs.addTab(ip, chat);
        tabs.setSelectedIndex(tabs.getTabCount() - 1);
        new Thread(chat).start();
    }

    private void addComponents(Container p) {
        p.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = BOTH;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 5, 5, 5);

        add(myIp, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(inputLabel, gbc);

        gbc.weightx = 1;
        gbc.gridx = 1;
        add(ipInput, gbc);

        gbc.weightx = 0;
        gbc.gridx = 2;
        add(create, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(alert, gbc);

        gbc.gridy = 3;
        gbc.weighty = 1;
        add(tabs, gbc);

    }

    private void addListeners() {
        ActionListener chatMaker = e -> {
            String ip = ipInput.getText();
            if (!controller.isValidIP(ip)) {
                alert.setText("\""+ ip + "\" is a not valid IP");
                return;
            }
            if (controller.connections.get(ip) != null) {
                alert.setText("You already have a chat with " + ip);
                return;
            }
            ipInput.setText("");
            controller.clientConnector.connectTo(ip);
        };

        create.addActionListener(chatMaker);
        ipInput.addActionListener(chatMaker);

        ipInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                alert.setText("");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                alert.setText("");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                alert.setText("");
            }
        });

    }

    public void open() {
        setVisible(true);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        new Thread(this).start();
    }
}