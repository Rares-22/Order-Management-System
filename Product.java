package model;

public class Product {
    private int id_product;
    private String name;
    private String manufacturer;
    private int quantity;
    private int price;

    public Product() {

    }

    public Product(int id_product, String name, String manufacturer, int quantity, int price) {
        super();
        this.id_product = id_product;
        this.name = name;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(String name, String manufacturer, int quantity, int price) {
        super();
        this.name = name;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product [id_product=" + id_product + ", name=" + name + ", manufacturer=" + manufacturer + ", quantity="
                + quantity + ", price=" + price + "]";
    }

}
