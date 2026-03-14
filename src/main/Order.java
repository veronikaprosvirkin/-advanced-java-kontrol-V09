import java.util.Arrays;

public class Order {
    private final String id;
    private final OrderItem[] items;
    private double price;
    private String status;


    public Order(String id, OrderItem[] items) {
        this.id = id;
        this.items = Arrays.copyOf(items,items.length);
        this.status = "New";
    }
    @Override
    public String toString(){
        return "Order{id=" + id+", status: "+ status + "ordered items "+ Arrays.toString(items)+"}";}


    //method for changing status to return
    public void markAsReturned() {
        if ("DELIVERED".equals(this.status)) {
            this.status = "RETURNED";
        } else {
            throw new IllegalStateException("Returning is possible only with status DELIVERED");
        }
    }
    public String getId() { return id; }
    public String getStatus() { return status; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }
    public OrderItem[] getItems() { return Arrays.copyOf(items, items.length); }
}
