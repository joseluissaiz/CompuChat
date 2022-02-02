package Marc.View;

import Marc.Controller.Controller;
import Marc.Model.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.BOTH;
import static java.lang.Thread.sleep;

public class ChatPanel extends JPanel implements Runnable {


    //Attributes


    private  final Controller controller;

    public final Connection connection;
    private final JTextArea output = new JTextArea();
    private final JTextField input = new JTextField();
    private final JButton send = new JButton("Send");


    //Constructor


    public ChatPanel(Controller controller, String ip) {
        this.controller = controller;
        this.connection = controller.connections.get(ip);
        output.append("New connection created with "+ connection.IP + "\n\n");
        addComponents();
        addListeners();
        output.setEditable(false);
    }


    //Methods


    @Override
    public void run() {
        while (true) {
            if (!connection.messageQueue.isEmpty()) {
                String msg = connection.messageQueue.poll();
                addMessage(connection.IP, msg);
            }
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 2;
        gbc.fill = BOTH;
        gbc.insets = new Insets(5,5,5,5);

        add(output,gbc);

        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        add(input,gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        add(send,gbc);
    }

    public void addListeners() {

        ActionListener messageSender = e -> {
            String msg = input.getText();
            if (msg.isBlank()) {return;}
            addMessage(controller.IP,msg);
            connection.sendMessage(msg);
            input.setText("");
        };

        send.addActionListener(messageSender);

        input.addActionListener(messageSender);
    }

    private void addMessage(String sender, String message) {
        output.append("    [" + sender + "] - "+message+"\n");
    }


}
