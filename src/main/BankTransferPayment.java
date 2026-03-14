public class BankTransferPayment implements PaymentMethod{
    @Override
    public void pay(double amount) {
        double amountToPay = amount * 1.02;
        System.out.println("Pay with bank (commission 2%): " + amountToPay);
    }
}
