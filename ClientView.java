package presentation;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableModel;

public class ClientView {
    private JFrame frame= new JFrame("Client application");
    private JPanel mainPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel rightPanel = new JPanel();

    private JTable table = new JTable();
    private JScrollPane scrollPane = new JScrollPane();

    private JLabel newClientLabel = new JLabel("New customer?");
    private JLabel clientNameLabel = new JLabel("Name:");
    private JLabel clientAddressLabel = new JLabel("Address:");
    private JLabel clientPhoneLabel = new JLabel("Phone number:");
    private JLabel clientEmailLabel = new JLabel("Email:");
    private JLabel infoLeftLabel = new JLabel("Info:");
    private JTextField clientNameTextField = new JTextField();
    private JTextField clientAddressTextField = new JTextField();
    private JTextField clientPhoneTextField = new JTextField();
    private JTextField clientEmailTextField = new JTextField();

    private JLabel placeOrderLabel = new JLabel("Place an order");
    private JLabel clientIdLabel = new JLabel("Client Id:");
    private JLabel productIdLabel = new JLabel("Product Id:");
    private JLabel productQuantityLabel = new JLabel("Product quantity:");
    private JLabel dateLabel = new JLabel("Date:");
    private JLabel infoCenterLabel = new JLabel("Info:");
    private JTextField clientIdTextField = new JTextField();
    private JTextField productIdTextField = new JTextField();
    private JTextField productQuantityTextField = new JTextField();
    private JTextField dateTextField = new JTextField();

    private JTextArea leftTextArea = new JTextArea(4, 5);
    private JScrollPane infoLeftScrollPane = new JScrollPane(leftTextArea);
    private JTextArea centerTextArea = new JTextArea(4, 5);
    private JScrollPane infoCenterScrollPane = new JScrollPane(centerTextArea);

    private JButton signInButton = new JButton("SIGN UP");
    private JButton placeOrderButton = new JButton("PLACE ORDER");
    private JButton showProductsButton = new JButton("SHOW PRODUCTS");


    public ClientView() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 430);
        frame.setResizable(true);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(Box.createRigidArea(new Dimension(30,0)));

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        leftPanel.add(newClientLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        leftPanel.add(clientNameLabel);
        leftPanel.add(clientNameTextField);
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        leftPanel.add(clientAddressLabel);
        leftPanel.add(clientAddressTextField);
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        leftPanel.add(clientPhoneLabel);
        leftPanel.add(clientPhoneTextField);
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        leftPanel.add(clientEmailLabel);
        leftPanel.add(clientEmailTextField);
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        leftPanel.add(infoLeftLabel);
        leftPanel.add(infoLeftScrollPane);
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        leftPanel.add(signInButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(leftPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(30,0)));

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(placeOrderLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(clientIdLabel);
        centerPanel.add(clientIdTextField);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(productIdLabel);
        centerPanel.add(productIdTextField);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(productQuantityLabel);
        centerPanel.add(productQuantityTextField);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(dateLabel);
        centerPanel.add(dateTextField);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(infoCenterLabel);
        centerPanel.add(infoCenterScrollPane);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(placeOrderButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(centerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(30,0)));

        rightPanel.add(showProductsButton);
        scrollPane.getViewport().add(table);
        rightPanel.add(scrollPane);
        mainPanel.add(rightPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(30,0)));

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public void setTable(JTable t) {
        TableModel mod = t.getModel();
        table.setModel(mod);
    }

    public void addSignInButtonListener(ActionListener a) {
        signInButton.addActionListener(a);
    }
    public void addPlaceOrderButtonListener(ActionListener a) {
        placeOrderButton.addActionListener(a);
    }
    public void addShowProductsButtonListener(ActionListener a) {
        showProductsButton.addActionListener(a);
    }
    public String getName() {
        return clientNameTextField.getText();
    }
    public String getAddress() {
        return clientAddressTextField.getText();
    }
    public String getPhone() {
        return clientPhoneTextField.getText();
    }
    public String getEmail() {
        return clientEmailTextField.getText();
    }
    public String getClientId() {
        return clientIdTextField.getText();
    }
    public String getProductId() {
        return productIdTextField.getText();
    }
    public String getProductQuantity() {
        return productQuantityTextField.getText();
    }
    public String getDate() {
        return dateTextField.getText();
    }
    public void setInfoLeft(String s) {
        leftTextArea.setText(s);
    }
    public void setInfoCenter(String s) {
        centerTextArea.setText(s);
    }
}
