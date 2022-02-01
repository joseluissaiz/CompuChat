package Marc.View;

import Marc.Model.Connection;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.BOTH;

public class ChatPanel extends JPanel implements Runnable {


    //Attributes


    private final JTextArea output = new JTextArea();
    private final JTextField input = new JTextField();
    private final JButton send = new JButton("Send");
    private final Connection connection;


    //Constructor


    public ChatPanel(Connection connection) {

        this.connection = connection;
        output.append("New connection created with "+ connection.IP);
        addComponents();
        addListeners();
        output.setEditable(false);

    }


    //Methods


    @Override
    public void run() {
        while (true) {
            if (!connection.messageQueue.isEmpty()) {
                String s = connection.messageQueue.poll();
                output.append("\n"+s);
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
        send.addActionListener(e->{
            String msg = input.getText();
            if (msg.isBlank()) {return;}
            connection.sendMessage(msg);
            input.setText("");
        });
    }

}
