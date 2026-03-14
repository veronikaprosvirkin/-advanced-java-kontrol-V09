import java.util.logging.Logger;

public class BankTransferPayment implements PaymentMethod{
    private static final Logger logger = Logger.getLogger(BankTransferPayment.class.getName());
    @Override
    public void pay(double amount) {
        double fee = amount * 0.02;
        double finalAmount = amount + fee;

        logger.info("Processing bank transfer. Amount: " + amount + ", Fee (2%): " + fee);
        logger.info("Bank transfer successful. Total charged: " + finalAmount);
    }
}
