public class CreditCard implements PaymentProcessor {
    @Override
    public boolean process(double amount, String method) {
        System.out.println("Processing Credit Card payment of " + amount + "...");
        return method.equalsIgnoreCase("Credit Card");
    }
}
