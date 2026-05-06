public class BankTransfer implements PaymentProcessor {
    @Override
    public boolean process(double amount, String method) {
        System.out.println("Processing Bank Transfer of " + amount + "...");
        return method.equalsIgnoreCase("Bank Transfer");
    }
}
