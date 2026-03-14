
import java.util.logging.Logger;

public class PayPalPayment implements PaymentMethod{
    private static final Logger logger = Logger.getLogger(PayPalPayment.class.getName());
    @Override
    public void pay(double amount) {
        if (amount <300){
            logger.warning("Payment rejected: amount must be greater than 300");
            throw new IllegalArgumentException("Amount must be greater than 300");
        }
        logger.info("Paypal payment successful: " + amount);
    }
}
