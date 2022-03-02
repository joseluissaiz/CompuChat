package Marc.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.awt.GridBagConstraints.*;

public class ChatTab extends JPanel {


    //Components


    public final JButton closeButton = new JButton("[x] Close");

    public final JTextArea output = new JTextArea();
    public final JScrollPane outputScrollPane = new JScrollPane (output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    public final JTextField input = new JTextField();
    public final JButton sendButton = new JButton("Send");


    //Attributes


    public final String name;
    protected final ArrayList<CloseListener> closeListeners = new ArrayList<>();
    protected final ArrayList<SendListener> sendListeners = new ArrayList<>();


    //Constructor


    public ChatTab(String name) {
        this.name = name;
        addComponents();
        addListeners();
    }


    //Methods


    private void addComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = NONE;
        gbc.anchor = FIRST_LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);

        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        add(closeButton, gbc);

        gbc.fill = BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        output.setEditable(false);
        add(outputScrollPane, gbc);

        gbc.weighty = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;

        add(input, gbc);

        gbc.weightx = 0;
        gbc.gridx = 1;
        add(sendButton, gbc);
    }

    private void addListeners() {

        ActionListener send = e -> {
            String message = input.getText();
            input.setText("");

            for (SendListener listener : sendListeners) listener.onSend(message);
        };

        input.addActionListener(send);
        sendButton.addActionListener(send);

        closeButton.addActionListener(e -> {
            for (CloseListener listener : closeListeners) listener.onClose(this);
        });

    }

    public void addCloseListener(CloseListener listener) {
        closeListeners.add(listener);
    }

    public void addSendListener(SendListener listener) {
        sendListeners.add(listener);
    }

    public void close() {
        for (CloseListener listener : closeListeners) listener.onClose(this);
    }

    public void write(String message) {
        output.append(message);
    }

    public void writeLine(String message) {
        output.append(message+"\n");
    }


    //Inner Class


    public interface CloseListener {

        void onClose(ChatTab tab);

    }

    public interface SendListener {

        void onSend(String message);

    }

}
