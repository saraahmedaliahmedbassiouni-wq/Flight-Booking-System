import java.util.*;
import java.util.stream.Collectors;

public class BookingSystem {
    private List<user> users;
    private List<Flight> flights;
    private List<Booking> bookings;
    private List<Passenger> passengers;
    
    public BookingSystem() {
        this.users = new ArrayList<>();
        this.flights = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }
    public List<user> getUsers() { 
        return users; 
    }
    public List<Flight> getFlights() {
         return flights; 
        }
    public List<Booking> getBookings() { 
        return bookings;
     }


    public List<Flight> searchFlights(int flightNum, String airline, String origin, String destination, double departureTime, double arrivalTime) {
       return flights.stream()
             .filter(f -> matchesFlightCriteria(f, origin, destination, airline, departureTime, arrivalTime))
             .collect(Collectors.toList());
}

    private boolean matchesFlightCriteria(Flight f, String origin, String destination, String airline, double departureTime, double arrivalTime) {
       return f.getOrigin().equals(origin) && f.getDestination().equals(destination)
            && f.getAirline().equals(airline) && f.getDepartureTime() == departureTime
            && f.getArrivalTime() == arrivalTime;
}

    public Booking createBooking(Customer customer, Flight flight, List<String> passengerNames) {
        Booking booking = new Booking(
            "BOOK-" + System.currentTimeMillis(),
            customer,
            flight,
            passengerNames,
            new ArrayList<>(), 
            "Pending",
            "Unpaid"
        );
        bookings.add(booking);
        return booking;
    }
    
    

    public boolean processPayment(Booking booking, String paymentMethod) {
        if (booking.getStatus().equals("Pending")) {
            booking.confirmBooking();
            return true;
        }
        return false;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
    
    
}