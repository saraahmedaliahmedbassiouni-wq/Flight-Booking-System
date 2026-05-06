import java.time.LocalDateTime;

public class Payment {
    private String paymentId;
    private String bookingReference;
    private double amount;
    private String method;
    private String status;
    private LocalDateTime transactionDate;
    private PaymentProcessor paymentProcessor;

    public Payment(String bookingReference, double baseFare, String method, PaymentProcessor processor) {
        this.paymentId = "PAY-" + System.currentTimeMillis();
        this.bookingReference = bookingReference;
        this.amount = calculateFare(baseFare);
        this.method = method;
        this.status = "Pending";
        this.transactionDate = LocalDateTime.now();
        this.paymentProcessor = processor;
    }

        public String getPaymentId() {
        return paymentId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    
    private double calculateFare(double baseFare) {
        
        double tax = baseFare * 0.10;
        double serviceFee = 15.0;
        return baseFare + tax + serviceFee;
    }

    public boolean processPayment() {
        if (!validatePaymentDetails()) {
            updateStatus("Failed");
            return false;
        }

        boolean success = paymentProcessor.process(amount, method);
        if (success) {
            updateStatus("Completed");
           
            System.out.println("E-ticket generated for booking: " + bookingReference);
            return true;
        } else {
            updateStatus("Failed");
            return false;
        }
    }

    public boolean validatePaymentDetails() {
        return amount > 0 &&
               (method.equalsIgnoreCase("Credit Card") || method.equalsIgnoreCase("Bank Transfer"));
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }


}
