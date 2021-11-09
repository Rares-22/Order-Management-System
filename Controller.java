package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.JTable;

import bll.AbstractBLL;
import model.Client;
import model.Product;
import model.WarehouseOrder;

public class Controller {

    private AbstractBLL<Client> clientBLL;
    private AbstractBLL<WarehouseOrder> warehouseOrderBLL;
    private AbstractBLL<Product> productBLL;
    private ClientView clientView;
    private AdminView adminView;

    public Controller() {
        clientBLL = new AbstractBLL<Client>(Client.class);
        warehouseOrderBLL = new AbstractBLL<WarehouseOrder>(WarehouseOrder.class);
        productBLL = new AbstractBLL<Product>(Product.class);

        clientView = new ClientView();
        clientView.addSignInButtonListener(new SignInListener());
        clientView.addPlaceOrderButtonListener(new PlaceOrderListener());
        clientView.addShowProductsButtonListener(new ShowProductsListener());

        adminView = new AdminView();
        adminView.addEditClientButtonListener(new EditClientListener());
        adminView.addEditProductButtonListener(new EditProductListener());
        adminView.addShowClientsButtonListener(new ShowClientsListener());
        adminView.addShowOrdersButtonListener(new ShowOrdersListener());
        adminView.addShowProductsButtonListener(new ShowAdminProductsListener());
    }

    public void doIT() {}

    public static <T> JTable createTable(List<T> list) {
        String[] columnNames1 = new String[1];
        Object[] objectArray = new Object[1];
        Object[][] rowData1 = new Object[1][1];
        List<String> columnNamesList1 = new ArrayList<String>();
        List<Object> objectsList1 = new ArrayList<Object>();
        List<Object[]> objectsArraysList1 = new ArrayList<Object[]>();

        if(list.size() == 0) {
            throw new NoSuchElementException("Table is empty.");
        }

        for (Field field : list.get(0).getClass().getDeclaredFields()) {
            field.setAccessible(true);
            columnNamesList1.add(field.getName());
        }
        columnNames1 = columnNamesList1.toArray(columnNames1);

        for(T mainListElem: list) {
            objectsList1.clear();
            for (Field field : mainListElem.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = null;
                try {
                    value = field.get(mainListElem);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                objectsList1.add(value);
            }
            objectArray = new Object[1];
            objectArray = objectsList1.toArray(objectArray);
            objectsArraysList1.add(objectArray);
        }
        rowData1 = objectsArraysList1.toArray(rowData1);

        return new JTable(rowData1, columnNames1);
    }

    class SignInListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name1 = clientView.getName();
            String address1 = clientView.getAddress();
            String phone1 = clientView.getPhone();
            String email1 = clientView.getEmail();
            if(name1.equals("") || address1.equals("") || phone1.equals("") || email1.equals("")) {
                clientView.setInfoLeft("Incorrect input data.\nPlease retry.");
            }
            else {
                Client c = new Client(name1, address1, phone1, email1);

                int	id = clientBLL.insert(c);
                if(id>=0) {
                    clientView.setInfoLeft("The sign up was successful.\nYour new id is "
                            +id+".\nUse it to place orders.");
                }
                else {
                    clientView.setInfoLeft("Incorrect input data.\nPlease retry.");
                }
            }
        }
    }

    class PlaceOrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int clientID = Integer.parseInt(clientView.getClientId());
                int productID = Integer.parseInt(clientView.getProductId());
                int quantity = Integer.parseInt(clientView.getProductQuantity());
                String date = clientView.getDate();
                if(date.equals("")) {
                    clientView.setInfoCenter("Incorrect input data.\nPlease retry.");
                }
                else {
                    try {
                        Product product = productBLL.findById(productID);
                        if(product.getQuantity()<=0 || product.getQuantity()<quantity) {
                            clientView.setInfoCenter("Not enough products in stock.\n" +
                                    "Select a smaller quantity of products.");
                        }
                        else {
                            product.setQuantity(product.getQuantity()-quantity);
                            productBLL.update(product);
                            WarehouseOrder o = new WarehouseOrder(clientID, productID,quantity,date);
                            int id = warehouseOrderBLL.insert(o);
                            if(id >= 0) {
                                clientView.setInfoCenter("Order placind was successful.\nYour order id is "
                                        +id+".\nWe will email you with further information.");
                            }
                            else {
                                clientView.setInfoCenter("Incorrect input data.\n" +
                                        "Check if your id and the product id are correct.\n" +
                                        "Date format must be yyyy-mm-dd.");
                            }
                        }
                    }
                    catch(NoSuchElementException ex) {
                        System.out.println(ex.getMessage());
                        clientView.setInfoCenter("Incorrect input data.\n" +
                                "Check if your id and the product id are correct.\n" +
                                "Date format must be yyyy-mm-dd.");
                    }

                }
            }
            catch(NumberFormatException ex) {
                clientView.setInfoCenter("Incorrect input data.\nPlease retry.");
            }
        }
    }

    class ShowProductsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                clientView.setTable(Controller.createTable(productBLL.findAll()));
            }
            catch(NoSuchElementException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    class EditClientListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(adminView.getClientId());
                String name1 = adminView.getClientName();
                String address1 = adminView.getClientAddress();
                String phone1 = adminView.getClientPhone();
                String email1 = adminView.getClientEmail();
                if(name1.equals("") || address1.equals("") || phone1.equals("") || email1.equals("")) {
                    adminView.setInfoLeft("All fields are mandatory.");
                }
                else {
                    Client cl = new Client(id, name1, address1, phone1, email1);
                    try {
                        clientBLL.update(cl);
                        adminView.setInfoLeft("Update was successful.");
                    }
                    catch(NoSuchElementException exm) {
                        adminView.setInfoLeft("Client with the given id was not found.");
                    }
                }
            }
            catch(NumberFormatException ex) {
                adminView.setInfoLeft("Inserted id is incorrect.");
            }
        }
    }

    class EditProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String idString = adminView.getProductId();
                String name1 = adminView.getProductName();
                String manufacturer1 = adminView.getProductManufacturer();
                int quantity1 = Integer.parseInt(adminView.getProductQuantity());
                int price1 = Integer.parseInt(adminView.getProductPrice());
                if( name1.equals("") || manufacturer1.equals("") ) {
                    adminView.setInfoCenter("All fields are mandatory.");
                }
                else {
                    if(idString.equals("")) {
                        Product pr = new Product(name1, manufacturer1, quantity1, price1);
                        productBLL.insert(pr);
                    }
                    else {
                        int id = Integer.parseInt(idString);
                        Product pr = new Product(id, name1, manufacturer1, quantity1, price1);
                        try {
                            productBLL.update(pr);
                        }
                        catch(NoSuchElementException ex2) {
                            adminView.setInfoCenter("Product with the given id not found.");
                        }
                    }
                }
            }
            catch(NumberFormatException ex) {
                adminView.setInfoCenter("Incorrect input data.");
            }
        }
    }

    class ShowClientsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                adminView.setTable(Controller.createTable(clientBLL.findAll()));
            }
            catch(NoSuchElementException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    class ShowOrdersListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                adminView.setTable(Controller.createTable(warehouseOrderBLL.findAll()));
            }
            catch(NoSuchElementException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    class ShowAdminProductsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                adminView.setTable(Controller.createTable(productBLL.findAll()));
            }
            catch(NoSuchElementException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
