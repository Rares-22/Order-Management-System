package model;

public class WarehouseOrder {
    private int id_order;
    private int id_client;
    private int id_product;
    private int quantity;
    private String date;

    public WarehouseOrder() {

    }

    public WarehouseOrder(int id_order, int id_client, int id_product, int quantity, String date) {
        super();
        this.id_order = id_order;
        this.id_client = id_client;
        this.id_product = id_product;
        this.quantity = quantity;
        this.date = date;
    }

    public WarehouseOrder(int id_client, int id_product, int quantity, String date) {
        super();
        this.id_client = id_client;
        this.id_product = id_product;
        this.quantity = quantity;
        this.date = date;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "WarehouseOrder [id_order=" + id_order + ", id_client=" + id_client + ", id_product=" + id_product
                + ", quantity=" + quantity + ", date=" + date + "]";
    }

}
