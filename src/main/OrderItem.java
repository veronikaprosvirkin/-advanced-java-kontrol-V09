public class OrderItem {
    private final String name;
    private final int quantity;

    public OrderItem(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
        if (quantity>5 || quantity <1){
            throw new IllegalArgumentException("Quantity must be between 1 and 5");

        }
    }
    public String getName(){
        return name;
    }
    public int getQuantity(){
        return quantity;
    }
}
