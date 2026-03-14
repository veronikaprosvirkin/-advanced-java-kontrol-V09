public class CardPayment implements PaymentMethod{

    @Override
    public void pay(double amount) {
        if (amount>30000){
            throw new IllegalArgumentException("Amount must be not bigger then 30.000");
        }
        System.out.println("Pay with card: " + amount);
    }
}
