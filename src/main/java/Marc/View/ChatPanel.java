package Marc.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;

import static java.awt.GridBagConstraints.BOTH;
import static javax.swing.SwingConstants.VERTICAL;

public class ChatPanel extends JPanel {


    //Components


    public final JLabel primaryLabel = new JLabel("{Primary Label}");

    public final JToggleButton toggleButton = new JToggleButton("{Toggle Button}");

    public final JLabel fieldLabel1 = new JLabel("{Field 1}");
    public final JTextField field1 = new JTextField();

    public final JLabel fieldLabel2 = new JLabel("{Field 2}");
    public final JTextField field2 = new JTextField();

    public final JButton sendButton = new JButton("{Send button}");

    public final JTabbedPane tabbedPane = new JTabbedPane();


    //Attributes


    protected final HashMap<String, ChatTab> tabs = new HashMap<>();
    protected final ArrayList<SubmitListener> submitListeners = new ArrayList<>();
    protected final ArrayList<ToggleListener> toggleListeners = new ArrayList<>();


    //Constructor


    public ChatPanel() {
        addComponents();
        addListeners();
    }

    public ChatPanel(String primaryLabelText, String toggleButtonText, String fiel1Text,
                     String field2Text, String sendButtonText) {

        addComponents();
        addListeners();

        primaryLabel.setText(primaryLabelText);
        toggleButton.setText(toggleButtonText);
        fieldLabel1.setText(fiel1Text);
        fieldLabel2.setText(field2Text);
        sendButton.setText(sendButtonText);
    }


    //Methods


    private void addComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = BOTH;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(5, 5, 5, 5);

        add(primaryLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 5;
        add(toggleButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(fieldLabel1, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        add(field1,gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        add(new JSeparator(VERTICAL), gbc);

        gbc.gridx = 3;
        add(fieldLabel2, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0.5f;
        add(field2, gbc);

        gbc.gridx = 5;
        gbc.weightx = 0;
        add(sendButton, gbc);

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        add(tabbedPane, gbc);
    }

    private void addListeners() {

        field1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                field1.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        field2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                field2.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        field1.addActionListener(e -> field2.requestFocus());

        ActionListener submit = e -> {
            String f1 = field1.getText();
            String f2 = field2.getText();

            field1.requestFocus();

            for (SubmitListener listener : submitListeners) listener.onSubmit(f1,f2);

        };

        field2.addActionListener(submit);

        sendButton.addActionListener(submit);

        toggleButton.addActionListener(e -> {
            for (ToggleListener listener : toggleListeners) {
                if (toggleButton.isSelected()) listener.onToggleOn();
                else listener.onToggleOff();
            }
        });

    }

    public void addSubmitListener(SubmitListener listener) {
        submitListeners.add(listener);
    }

    public synchronized void addTab(String tabName) {
        if (tabs.get(tabName) != null) return;
        tabs.put(tabName, new ChatTab(tabName));
        tabbedPane.addTab(tabName, tabs.get(tabName));
        tabs.get(tabName).addCloseListener(e -> {
            tabbedPane.remove(tabs.get(tabName));
            tabs.remove(tabName);
        });
    }

    public void addToggleListener(ToggleListener listener) {
        toggleListeners.add(listener);
    }

    public void closeTab(String tabName) {
        tabs.get(tabName).close();
    }

    public ChatTab getTab(String tabName) {
        return tabs.get(tabName);
    }

    public HashMap<String, ChatTab> getTabs() {
        return tabs;
    }


    //Inner class


    public interface SubmitListener {

        void onSubmit(String f1text, String f2text);

    }

    public interface ToggleListener {

        void onToggleOn();

        void onToggleOff();

    }

}
