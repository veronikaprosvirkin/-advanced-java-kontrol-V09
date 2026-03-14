import java.util.Optional;

public class StandardOrderProcessor extends OrderProcessorTemplate{
    @Override
    protected void fraudCheck(Order order) throws FraudCheckFailedException {
        if (order.getId().contains("FRAUD")) {
            throw new FraudCheckFailedException("Fraudulent activity detected for order: " + order.getId());
        }
        System.out.println("INFO: Fraud check passed for order " + order.getId());
    }
    public Optional<Order> findById(String id) {
        if (id == null || id.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Order(id, new OrderItem[0]));
    }
}
