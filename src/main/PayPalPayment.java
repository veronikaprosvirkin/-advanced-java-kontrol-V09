public class PayPalPayment implements PaymentMethod{
    @Override
    public void pay(double amount) {
        if (amount <300){
            throw new IllegalArgumentException("Amount must be greater than 300");
        }
        System.out.println("Pay with PayPal "+ amount);
    }
}
