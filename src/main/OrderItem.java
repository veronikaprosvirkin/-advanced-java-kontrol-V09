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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem item = (OrderItem) o;
        return quantity == item.quantity &&
                java.util.Objects.equals(name, item.name);
    }
    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, quantity);
    }
}
