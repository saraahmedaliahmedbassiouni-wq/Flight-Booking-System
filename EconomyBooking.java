import java.util.List;

public class EconomyBooking extends Booking {
    public EconomyBooking(String bookingReference, Customer customer, Flight flight,
                          List<String> passengers, List<String> seatSelections, String status, String paymentStatus) {
        super(bookingReference, customer, flight, passengers, seatSelections, status, paymentStatus);
    }
    public String generateTicket() {
        return "=== Economy Class Ticket ===\n" +
               "Booking Ref: " + getBookingReference()+ "\n" +
               "Passenger(s): " + getPassengers()+ "\n" +
               "Flight: " + getFlight().getFlightNum() + " (" + getFlight().getAirline() + ")\n" +
               "Seat(s): " + getSeatSelections() + "\n" +
               "Enjoy your affordable journey!";
    }
}
