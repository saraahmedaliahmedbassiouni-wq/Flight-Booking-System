import java.util.List;

public class BusinessBooking extends Booking {

    public BusinessBooking(String bookingReference, Customer customer, Flight flight,
                           List<String> passengers, List<String> seatSelections, String status, String paymentStatus) {
        super(bookingReference, customer, flight, passengers, seatSelections, status, paymentStatus);
    }

   
    public String generateTicket() {
        return """
               === Business Class Ticket ===
               Booking Ref: """ + getBookingReference() + "\n" +
               "Passenger(s): " + getPassengers() + "\n" +
               "Flight: " + getFlight().getFlightNum() + " (" + getFlight().getAirline() + ")\n" +
               "Seat(s): " + getSeatSelections() + "\n" +
               "Access to lounge and in-flight meals included!";
    }

}