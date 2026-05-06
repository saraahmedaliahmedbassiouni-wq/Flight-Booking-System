import java.util.List;

public class Customer extends user{
        private long  customerId;
    private String address;
    private String bookingHistory;
    private String preference;
    public Customer(String username , String password, int id,String name,String email,String contactInfo,long customerId,String address,String bookingHistory,String preference){
        super(username, password,id,name,email,contactInfo);
        this.address=address;
        this.bookingHistory=bookingHistory;
        this.customerId=customerId;
        this.preference=preference;
        this.setRole("Customer");

    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getBookingHistory() {
        return bookingHistory;
    }

    public void setBookingHistory(String bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
 public void searchFlights(BookingSystem system, String origin, String destination, String airline,
                              double departureTime, double arrivalTime) {
        List<Flight> results = system.searchFlights(0, airline, origin, destination, departureTime, arrivalTime);
        if (results.isEmpty()) {
            System.out.println("No flights found for your criteria.");
        } else {
            System.out.println("Available Flights:");
            for (Flight f : results) {
                System.out.println("Flight #" + f.getFlightNum() + " | " + f.getAirline() + " | " + f.getOrigin() + " → " +
                        f.getDestination() + " | Departure: " + f.getDepartureTime() + " | Arrival: " + f.getArrivalTime());
            }
        }
    }
public void createBooking(BookingSystem system, Flight flight, List<String> passengers) {
        Booking booking = system.createBooking(this, flight, passengers);
        System.out.println("Booking created with reference: " + booking.getBookingReference());
    }
  public void viewBooking(BookingSystem system) {
        List<Booking> allBookings = system.getBookings();
        boolean found = false;
        for (Booking b : allBookings) {
            if (b.getCustomer().equals(this)) {
                found = true;
                System.out.println("Booking Ref: " + b.getBookingReference());
                System.out.println("Status: " + b.getStatus() + " | Payment: " + b.getPaymentStatus());
                System.out.println("Flight: " + b.getFlight().getFlightNum() + " | Passengers: " + b.getPassengers());
                System.out.println("----------------------------------------");
            }
        }
        if (!found) {
            System.out.println("You have no bookings yet.");
        }
    }
public void cancelBooking(BookingSystem system, String bookingReference) {
        List<Booking> allBookings = system.getBookings();
        for (Booking b : allBookings) {
            if (b.getBookingReference().equals(bookingReference) && b.getCustomer().equals(this)) {
                if (b.getStatus().equalsIgnoreCase("Confirmed") || b.getStatus().equalsIgnoreCase("Pending")) {
                    b.setStatus("Cancelled");
                    b.setPaymentStatus("Refund Initiated");
                    System.out.println("Booking " + bookingReference + " has been cancelled.");
                    return;
                } else {
                    System.out.println("Cannot cancel booking. Current status: " + b.getStatus());
                    return;
                }
            }
        }
        System.out.println("No booking found with reference: " + bookingReference);
    }

}
