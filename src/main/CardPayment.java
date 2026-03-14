import java.io.IOException;
import java.util.logging.Logger;

public class CardPayment implements PaymentMethod{
    private static final Logger logger = Logger.getLogger(CardPayment.class.getName());
    @Override
    public void pay(double amount) {
        if (amount>30000){
            logger.warning("Payment rejected: amount exceeds 30,000");
            throw new IllegalArgumentException("Amount must be not bigger then 30.000");
        }
        try {
            // Simulate a checked infrastructure exception (e.g., network failure)
            simulateBankConnection();
            logger.info("Card payment successful: " + amount);
        } catch (IOException e) {
            throw new AppException("Bank server connection failed", e);
        }
    }

    private void simulateBankConnection() throws IOException {
        boolean networkUp = true;
        if (!networkUp) {
            throw new IOException("Connection timeout");
        }
    }
}
