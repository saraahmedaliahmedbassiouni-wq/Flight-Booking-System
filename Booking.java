import java .util.List;
public class Booking {
    private String bookingReference;
    private Customer customer;
    private Flight flight;
    private List<String> passengers;
    private List<String> seatSelections;
    private String status;
    private String paymentStatus;

     public Booking(String bookingReference, Customer customer, Flight flight, List<String> passengers,
            List<String> seatSelections, String status, String paymentStatus) {
        this.bookingReference = bookingReference;
        this.customer = customer;
        this.flight = flight;
        this.passengers = passengers;
        this.seatSelections = seatSelections;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    public Booking(Customer customer, Flight flight) {
       this.customer = customer;
       this.flight = flight;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<String> passengers) {
        this.passengers = passengers;
    }

    public List<String> getSeatSelections() {
        return seatSelections;
    }

    public void setSeatSelections(List<String> seatSelections) {
        this.seatSelections = seatSelections;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void addPassenger(String passenger) {
        this.passengers.add(passenger);
    }
    public double calculateTotalPrice() {
          double total = 0.0;
        for (int i = 0; i < passengers.size(); i++) {
            String seatClass = seatSelections.get(i);
            total += flight.calculatePrice(seatClass);
        }
        return total;
    }

    public void confirmBooking() {
        this.status = "confirmed";
        this.paymentStatus = "paid";
        System.out.println("Booking confirmed successfully! \n Total: " + calculateTotalPrice());
    }
    public void cancelBooking() {
         this.status = "Cancelled";
        System.out.println("Booking cancelled.");
    }
    public String generateItinerary() {
    return String.format(
        "ITINERARY%nRef: %s | Status: %s (%s)%n" +
        "Customer: %s%n" +
        "Flight: %s %s→%s%n" +
        "Time: %s - %s%n" +
        "Passengers: %s%n" +
        "Seats: %s%n" +
        "Total: $%.2f",
        bookingReference, status, paymentStatus,
        customer.getName(),
        flight.getFlightNum(), flight.getOrigin(), flight.getDestination(),
        flight.getDepartureTime(), flight.getArrivalTime(),
        String.join(", ", passengers),
        String.join(", ", seatSelections),
        calculateTotalPrice()
    );
}
    
    public String generatTicket () {
        return "Ticket for booking " + bookingReference + " [Generic Format]";
    }
}
