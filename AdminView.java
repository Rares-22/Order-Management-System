package presentation;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableModel;

public class AdminView {
    private JFrame frame= new JFrame("Aministrator application");
    private JPanel mainPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel rightPanel = new JPanel();

    private JTable table = new JTable();
    private JScrollPane scrollPane = new JScrollPane();

    private JLabel editClientLabel = new JLabel("Edit client                   ");
    private JLabel clientIdLabel = new JLabel("Id:");
    private JLabel clientNameLabel = new JLabel("Name:");
    private JLabel clientAddressLabel = new JLabel("Address:");
    private JLabel clientPhoneLabel = new JLabel("Phone number:");
    private JLabel clientEmailLabel = new JLabel("Email:");
    private JLabel infoLeftLabel = new JLabel("Info:");
    private JTextField clientIdTextField = new JTextField();
    private JTextField clientNameTextField = new JTextField();
    private JTextField clientAddressTextField = new JTextField();
    private JTextField clientPhoneTextField = new JTextField();
    private JTextField clientEmailTextField = new JTextField();

    private JLabel editProductLabel = new JLabel("Add or edit product");
    private JLabel productIdLabel = new JLabel("Id:");
    private JLabel productNameLabel = new JLabel("Name:");
    private JLabel productManufacturerLabel = new JLabel("Manufacturer:");
    private JLabel productQuantityLabel = new JLabel("Quantity:");
    private JLabel productPriceLabel = new JLabel("Price:");
    private JLabel infoCenterLabel = new JLabel("Info:");
    private JTextField productIdTextField = new JTextField();
    private JTextField productNameTextField = new JTextField();
    private JTextField productManufacturerTextField = new JTextField();
    private JTextField productQuantityTextField = new JTextField();
    private JTextField productPriceTextField = new JTextField();

    private JTextArea leftTextArea = new JTextArea(4, 5);
    private JScrollPane infoLeftScrollPane = new JScrollPane(leftTextArea);
    private JTextArea centerTextArea = new JTextArea(4, 5);
    private JScrollPane infoCenterScrollPane = new JScrollPane(centerTextArea);

    private JButton editClientButton = new JButton("EDIT");
    private JButton editProductButton = new JButton("EDIT");
    private JButton showClientsButton = new JButton("SHOW CLIENTS");
    private JButton showOrdersButton = new JButton("SHOW ORDERS");
    private JButton showProductsButton = new JButton("SHOW PRODUCTS");

    public AdminView() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 430);
        frame.setResizable(true);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(Box.createRigidArea(new Dimension(30,0)));

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        leftPanel.add(editClientLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        leftPanel.add(clientIdLabel);
        leftPanel.add(clientIdTextField);
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
        leftPanel.add(editClientButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(leftPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(30,0)));

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(editProductLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(productIdLabel);
        centerPanel.add(productIdTextField);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(productNameLabel);
        centerPanel.add(productNameTextField);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(productManufacturerLabel);
        centerPanel.add(productManufacturerTextField);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(productQuantityLabel);
        centerPanel.add(productQuantityTextField);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(productPriceLabel);
        centerPanel.add(productPriceTextField);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(infoCenterLabel);
        centerPanel.add(infoCenterScrollPane);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(editProductButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(centerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(30,0)));

        JPanel rightSubpanel = new JPanel();
        rightSubpanel.setLayout(new BoxLayout(rightSubpanel, BoxLayout.X_AXIS));
        rightSubpanel.add(showClientsButton);
        rightSubpanel.add(Box.createRigidArea(new Dimension(5,0)));
        rightSubpanel.add(showProductsButton);
        rightSubpanel.add(Box.createRigidArea(new Dimension(5,0)));
        rightSubpanel.add(showOrdersButton);
        rightPanel.add(rightSubpanel);
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

    public void addEditClientButtonListener(ActionListener a) {
        editClientButton.addActionListener(a);
    }
    public void addEditProductButtonListener(ActionListener a) {
        editProductButton.addActionListener(a);
    }
    public void addShowClientsButtonListener(ActionListener a) {
        showClientsButton.addActionListener(a);
    }
    public void addShowOrdersButtonListener(ActionListener a) {
        showOrdersButton.addActionListener(a);
    }
    public void addShowProductsButtonListener(ActionListener a) {
        showProductsButton.addActionListener(a);
    }
    public String getClientId() {
        return clientIdTextField.getText();
    }
    public String getClientName() {
        return clientNameTextField.getText();
    }
    public String getClientAddress() {
        return clientAddressTextField.getText();
    }
    public String getClientPhone() {
        return clientPhoneTextField.getText();
    }
    public String getClientEmail() {
        return clientEmailTextField.getText();
    }
    public String getProductId() {
        return productIdTextField.getText();
    }
    public String getProductName() {
        return productNameTextField.getText();
    }
    public String getProductManufacturer() {
        return productManufacturerTextField.getText();
    }
    public String getProductQuantity() {
        return productQuantityTextField.getText();
    }
    public String getProductPrice() {
        return productPriceTextField.getText();
    }
    public void setInfoLeft(String s) {
        leftTextArea.setText(s);
    }
    public void setInfoCenter(String s) {
        centerTextArea.setText(s);
    }
}
