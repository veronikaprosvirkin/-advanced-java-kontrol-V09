import java.util.logging.Logger;
import java.util.logging.Level;


public abstract class OrderProcessorTemplate {
    private static final Logger logger = Logger.getLogger(OrderProcessorTemplate.class.getName());
    public final void process(Order order, PaymentMethod paymentMethod) {
        logger.log(Level.INFO, "Starting process for Order ID: {0}", order.getId());
        try {
            validate(order);
            fraudCheck(order);
            calculate(order);
            pay(order, paymentMethod);
            notifyUser(order);
            logger.log(Level.INFO, "Order {0} processed successfully", order.getId());
        }catch (AppException e){
            logger.log(Level.WARNING, "Business logic violation: {0}", e.getMessage());
            throw e;
        }catch (Exception e){
            logger.log(Level.SEVERE, "Unexpected error during order processing", e);
            throw new AppException("Internal processing error", e);
        }
    }
    protected void validate(Order order){
        if (order.getItems().length == 0){
            throw new AppException("Order is empty");
        }
        logger.info("Validation passed");
    }
    protected abstract void fraudCheck(Order order) throws FraudCheckFailedException;

    protected void calculate(Order order) {
        double total = 0;
        for (OrderItem item : order.getItems()) {
            total += item.getQuantity() * 5000;
        }

        if (total >= 20000) {
            total *= 0.9;
            logger.info("Discount 10% applied");
        }
        order.setPrice(total);
    }
    protected void pay(Order order, PaymentMethod paymentMethod) {
        paymentMethod.pay(order.getPrice());
        order.setStatus("PAID");
        logger.info("Payment confirmed");
    }

    protected void notifyUser(Order order) {
        logger.info("User notification sent");
    }
}
