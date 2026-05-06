import java.io.IOException;
import java.util.*;

public class main {
    public static void main(String[] args) throws IOException {
       
        BookingSystem system = new BookingSystem();
        Administrator admin = new Administrator("sara", "d757", 656, "sara", "sara@gmaik", "9909", 576, 90);
        Administrator admin2 = new Administrator("Ahmed", "d757", 656, "sara", "sara@gmaik", "9909", 576, 90);
        system.getUsers().add(admin);
        system.getUsers().add(admin2);

        Customer customer = new Customer("custUser", "custPass", 3, "Customer Name", "customer@example.com", "0100011111", 3003L, "123 Street", "No bookings", "Window seat");
        system.getUsers().add(customer);
       
        Agent agent = new Agent("youssef", "ghfghf", 887, "sara", "ssas", "9898", "saeas", 68, 0);
        system.getUsers().add(agent);
        try {
            FileManager.saveUsers(system.getUsers());
        } catch (IOException e) {
        } 

        Map<String, Integer> availableSeats = new HashMap<>();
        availableSeats.put("Economy", 100);
        availableSeats.put("Business", 20);

        Map<String, Double> prices = new HashMap<>();
        prices.put("Economy", 1500.0);
        prices.put("Business", 4000.0);

        Flight domesticFlight = new Domestic(101, "Nile Air", "Cairo", "Aswan", 9.0, 11.0, availableSeats, prices);
        system.getFlights().add(domesticFlight);

        Passenger passenger = new Passenger(1L, "Customer Name", "A12345678", "2000-01-01", "Vegan meal");
        Passenger passenger1 = new Passenger(2L, "Customer Name", "A12345678", "2000-01-01", "Vegan meal");
    
String bookingReference = "BKG12345";

availableSeats.put("Economy", 100);
Flight flight = new Domestic(101, "Nile Air", "Cairo", "Aswan", 9.0, 11.0, availableSeats, prices);


List<String> passengers = new ArrayList<>();
passengers.add("Sara Ahmed");

List<String> seatSelections = new ArrayList<>();
seatSelections.add("Economy");


String status = "Confirmed";


String paymentStatus = "Paid";


try {
    FileManager.saveFlights(system.getFlights());
} catch (IOException e) {

    System.out.println("Error saving flights: " + e.getMessage());
}

        system.getPassengers().add(passenger);
        system.getPassengers().add(passenger1);

        try {

    FileManager.savePassengers(system.getPassengers());
     } catch (IOException e) {
    System.out.println("Error saving passengers: " + e.getMessage());
}

        PaymentProcessor creditProcessor = new CreditCard();
        Payment creditPayment = new Payment("BOOK123", 200.0, "Credit Card", creditProcessor);
        creditPayment.processPayment();
        PaymentProcessor bankProcessor = new BankTransfer();
        Payment bankPayment = new Payment("BOOK456", 150.0, "Bank Transfer", bankProcessor);
        bankPayment.processPayment();
       
        
        UI ui = new UI(system);
       
        System.out.println("System initialized and test data created.");
        ui.start();
    }

    
}