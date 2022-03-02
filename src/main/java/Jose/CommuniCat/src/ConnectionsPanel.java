package Jose.CommuniCat.src;

import javax.swing.*;
import java.awt.*;

public class ConnectionsPanel extends JPanel {

  private App app;

  private JTextField ipTextField;
  private JButton connectButton;
  private JScrollPane connectionScrollPane;
  private JTextField messageTextField;
  private JPanel chatListPanel;
  private Connection currentTab;
  private JLabel messageBoardClient;
  private JScrollPane clientScroller;

  public ConnectionsPanel(App app) {
    this.app = app;
    setLayout(new GridBagLayout());
    chatListPanel = new JPanel();
    chatListPanel.setLayout(new GridBagLayout());
    chatListPanel.setPreferredSize(new Dimension(250,0));
    chatListPanel.setMinimumSize(new Dimension(250,0));
    addCreateConnectionPanel();
    addConnectionListPanel();
    createChatPanel();
  }

  private void addConnectionListPanel() {
    connectionScrollPane = new JScrollPane();
    connectionScrollPane.getViewport().setLayout(new BoxLayout(connectionScrollPane.getViewport(), BoxLayout.Y_AXIS));
    connectionScrollPane.setBackground(Color.BLACK);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.weightx = 0.3f;
    gbc.weighty = 100f;
    gbc.fill = GridBagConstraints.BOTH;
    chatListPanel.add(connectionScrollPane, gbc);
    GridBagConstraints chatListPanelConstraints = new GridBagConstraints();
    chatListPanelConstraints.gridx = 1;
    chatListPanelConstraints.gridy = 0;
    chatListPanelConstraints.weightx = 0f;
    chatListPanelConstraints.weighty = 1f;
    chatListPanelConstraints.fill = GridBagConstraints.BOTH;
    this.add(chatListPanel, chatListPanelConstraints);
  }

  private void addCreateConnectionPanel() {
    JPanel createConnectionPanel = new JPanel();
    createConnectionPanel.setBackground(Color.GRAY);
    ipTextField = new JTextField();
    ipTextField.setPreferredSize(new Dimension(150,20));
    connectButton = new JButton("Connect");
    connectButton.addActionListener(e -> createConnection());
    createConnectionPanel.add(ipTextField);
    createConnectionPanel.add(connectButton);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 0.1f;
    gbc.weighty = 0f;
    gbc.fill = GridBagConstraints.BOTH;
    chatListPanel.add(createConnectionPanel, gbc);
  }

  private void createConnection() {
    app.createConnection(ipTextField.getText());
  }

  private void createChatPanel() {
    JPanel chatPanel = new JPanel();
    chatPanel.setLayout(new GridBagLayout());
    chatPanel.setBackground(new Color(0xFAF0EE));
    JPanel boardPanel = new JPanel();
    boardPanel.setLayout(new GridBagLayout());
    boardPanel.setBackground(Color.GRAY);
    //Client
    messageBoardClient = new JLabel("<html>"+
            "<div style='width: 550px;>"+
            "<p>Hello my darling computer</p></div></html>");
    messageBoardClient.setVerticalAlignment(JLabel.TOP);
    messageBoardClient.setBackground(Color.BLACK);
    clientScroller = new JScrollPane(
            messageBoardClient,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)
    ;
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 1f;
    gbc.weightx = 1f;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.NORTH;
    boardPanel.add(clientScroller, gbc);


    JPanel messagePanel = new JPanel();
    messagePanel.setLayout(new GridBagLayout());
    //Message TF
    messageTextField = new JTextField();
    gbc.gridy = 0;
    gbc.gridx = 0;
    gbc.weighty = 0f;
    gbc.weightx = 1f;
    gbc.fill = GridBagConstraints.BOTH;
    messagePanel.add(messageTextField, gbc);
    //Send Button
    JButton sendMessageButton = new JButton("Send");
    sendMessageButton.addActionListener(e -> sendMessage());
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 0.1f;
    gbc.weighty = 0f;
    gbc.fill = GridBagConstraints.BOTH;
    messagePanel.add(sendMessageButton, gbc);

    //BoardPanel Add
    gbc.gridy = 0;
    gbc.gridx = 0;
    gbc.weightx = 1f;
    gbc.weighty = 1f;
    chatPanel.add(boardPanel, gbc);

    //MessagePanel Add
    gbc.gridy = 1;
    gbc.gridx = 0;
    gbc.weightx = 1f;
    gbc.weighty = 0f;
    chatPanel.add(messagePanel, gbc);

    //Add chat panel
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0.9f;
    gbc.weighty = 1f;
    gbc.fill = GridBagConstraints.BOTH;
    this.add(chatPanel, gbc);
  }

  private void sendMessage() {
    if (currentTab != null && !messageTextField.getText().equals("")) {
      if (currentTab.sendMessage(messageTextField.getText())) {
        writeMessage(messageTextField.getText(), "Me");
      }
    }
  }

  public synchronized void writeMessage(String message, String sender) {
    if (sender.equals("Me")) {
      messageBoardClient.setText(
              messageBoardClient.getText().substring(0, messageBoardClient.getText().length()-13)
                      +"<div style='float: right;'><p style='text-align: right; background-color: #005500; color: white; padding: 10px;'>"
                      +sender+": "+message+"</p></div></div></html>");
      messageTextField.setText("");
    } else {
      messageBoardClient.setText(
              messageBoardClient.getText().substring(0, messageBoardClient.getText().length()-13)+
                      "<p style='background-color: #55ff55; padding: 10px;'>"+sender+": "+message+"</p></div></html>");
      messageTextField.setText("");
    }

    JScrollBar vertical = clientScroller.getVerticalScrollBar();
    vertical.setValue(vertical.getMaximum()+1000);
  }

  public void addClientTab(Connection connection) {
    JButton clientTab = new JButton(connection.getSocket().getInetAddress().getHostAddress());
    clientTab.addActionListener(e -> setCurrentTab(connection));
    connectionScrollPane.getViewport().add(clientTab);
  }

  public void setCurrentTab(Connection connection) {
    this.currentTab = connection;
  }
}