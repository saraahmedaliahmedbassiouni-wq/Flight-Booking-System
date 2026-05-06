import java.util.List;

public class FirstClassBooking  extends Booking{

    public FirstClassBooking(String bookingReference, Customer customer, Flight flight,
                             List<String> passengers, List<String> seatSelections, String status, String paymentStatus) {
        super(bookingReference, customer, flight, passengers, seatSelections, status, paymentStatus);
    }
    public String generateTicket() {
        return """
               === First Class Royal Ticket ===
               Booking Ref: """ + getBookingReference() + "\n" +
               "VIP Passenger(s): " + getPassengers() + "\n" +
               "Flight: " + getFlight().getFlightNum() + " (" + getFlight().getAirline() + ")\n" +
               "Luxury Seat(s): " + getSeatSelections() + "\n" +
               "Private suite access, chef-prepared meals, and more!";
    }
}
