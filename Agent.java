import java.util.*;

public class Agent extends user {
    private String department;
    private long agentID;
    private double commission;
    private List<Flight> managedFlights = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public Agent(String username, String password, int id, String name, String email, String contactInfo,
                 String department, long agentID, double commission) {
        super(username, password, id, name, email, contactInfo);
        this.department = department;
        this.agentID = agentID;
        this.commission = commission;
        this.setRole("Agent");
    }

    public String getDepartment() {
      return department;
    }



    public void setDepartment(String department) {
       this.department = department;
    }

    public long getAgentID() {
        return agentID;
    }

    public void setAgentID(long agentID) {
        this.agentID = agentID;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public List<Flight> getManagedFlights() {
        return managedFlights;
    }

    public void setManagedFlights(List<Flight> managedFlights) {
        this.managedFlights = managedFlights;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void manageFlights() {
    while (true) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Flight Management Menu ---");
        System.out.println("1. Add Flight");
        System.out.println("2. View Flights");
        System.out.println("3. Remove Flight");
        System.out.println("4. Exit Management");
        System.out.print("Choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1 -> addFlight();
            case 2 -> viewFlights();
            case 3 -> removeFlight();
            case 4 -> {
                System.out.println("Exiting flight management...");
                return;
            }
            default -> System.out.println("Invalid choice.");
        }
    }
}


    public void createBookingForCustomer() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("\n--- Create New Booking ---");

    if (managedFlights.isEmpty()) {
        System.out.println(" No available flights to book.");
        return;
    }

    System.out.println("Available Flights:");
    for (int i = 0; i < managedFlights.size(); i++) {
        Flight f = managedFlights.get(i);
        System.out.println((i + 1) + ". Flight #" + f.getFlightNum() + " | " + f.getOrigin() + " → " + f.getDestination());
    }

    System.out.print("Select flight by number: ");
    int flightIndex = scanner.nextInt() - 1;
    scanner.nextLine();

    if (flightIndex < 0 || flightIndex >= managedFlights.size()) {
        System.out.println(" Invalid selection.");
        return;
    }

    Flight selectedFlight = managedFlights.get(flightIndex);

    System.out.print("Enter customer username: ");
    String username = scanner.nextLine();
    System.out.print("Enter password: ");
    String password = scanner.nextLine();
    System.out.print("Enter full name: ");
    String name = scanner.nextLine();
    System.out.print("Enter email: ");
    String email = scanner.nextLine();
    System.out.print("Enter contact info: ");
    String contact = scanner.nextLine();
    System.out.print("Enter customer ID: ");
    long customerId = scanner.nextLong();
    scanner.nextLine();
    System.out.print("Enter address: ");
    String address = scanner.nextLine();
    System.out.print("Enter preferences: ");
    String preference = scanner.nextLine();

    Customer customer = new Customer(
        username, password, 0, name, email, contact,
        customerId, address, "None", preference
    );

    System.out.print("How many passengers? ");
    int numPassengers = scanner.nextInt();
    scanner.nextLine();

    List<String> passengers = new ArrayList<>();
    List<String> seatSelections = new ArrayList<>();

    for (int i = 0; i < numPassengers; i++) {
        System.out.print("Passenger " + (i + 1) + " name: ");
        passengers.add(scanner.nextLine());

        System.out.print("Select seat class (Economy/Business/First Class): ");
        String seatClass = scanner.nextLine();

        Integer available = selectedFlight.getAvailableseats().getOrDefault(seatClass, 0);
        if (available > 0) {
            selectedFlight.getAvailableseats().put(seatClass, available - 1);
            seatSelections.add(seatClass);
        } else {
            System.out.println("No available seats in " + seatClass + ". Skipping this passenger.");
        }
    }

    if (passengers.isEmpty()) {
        System.out.println(" No valid passengers added. Booking cancelled.");
        return;
    }

    String bookingId = "BK" + (bookings.size() + 1); 
    Booking newBooking = new Booking(
        bookingId, customer, selectedFlight,
        passengers, seatSelections, "Confirmed", "Paid"
    );

    bookings.add(newBooking);
    System.out.println(" Booking created for " + customer.getName() +
        " on flight " + selectedFlight.getFlightNum());
}


   public void modifyBooking() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("\n--- Modify a Booking ---");

    if (bookings.isEmpty()) {
        System.out.println("No bookings to modify.");
        return;
    }

    System.out.println("Available Bookings:");
    for (int i = 0; i < bookings.size(); i++) {
        Booking b = bookings.get(i);
        System.out.println((i + 1) + ". " + b.getBookingReference() + " | " +
                b.getCustomer().getName() + " | " + b.getFlight().getFlightNum());
    }

    System.out.print("Select booking by number: ");
    int choice = scanner.nextInt();
    scanner.nextLine(); 

    if (choice < 1 || choice > bookings.size()) {
        System.out.println("Invalid selection.");
        return;
    }

    Booking booking = bookings.get(choice - 1);

    System.out.print("Enter new status (Confirmed / Cancelled / Rescheduled): ");
    String newStatus = scanner.nextLine();

    System.out.print("Enter new payment status (Paid / Pending / Refunded): ");
    String newPaymentStatus = scanner.nextLine();

    booking.setStatus(newStatus);
    booking.setPaymentStatus(newPaymentStatus);

    System.out.println("\n Booking updated successfully!");
    System.out.println("Booking Reference: " + booking.getBookingReference());
    System.out.println("New Status: " + booking.getStatus());
    System.out.println("Payment Status: " + booking.getPaymentStatus());
}


    public void generateReports() {
        System.out.println("Generating agent report...");

        System.out.println("Managed Flights: " + managedFlights.size());
        System.out.println("Total Bookings: " + bookings.size());

        int totalSeatsBooked = 0;
        for (Booking b : bookings) {
            totalSeatsBooked += b.getPassengers().size();
        }

        double totalRevenue = totalSeatsBooked * 500; 
        System.out.println("Estimated Revenue: $" + totalRevenue);
    }

    private void addFlight() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter Flight Number: ");
    int flightNum = scanner.nextInt();
    scanner.nextLine();

    System.out.print("Enter Airline: ");
    String airline = scanner.nextLine();

    System.out.print("Enter Origin: ");
    String origin = scanner.nextLine();

    System.out.print("Enter Destination: ");
    String destination = scanner.nextLine();

    System.out.print("Enter Departure Time (e.g. 10.30): ");
    double departureTime = scanner.nextDouble();

    System.out.print("Enter Arrival Time (e.g. 14.00): ");
    double arrivalTime = scanner.nextDouble();
    scanner.nextLine();

    Map<String, Integer> seatMap = new HashMap<>();
    Map<String, Double> priceMap = new HashMap<>();

    String[] classes = {"Economy", "Business" , "First Class"};
    for (String c : classes) {
        System.out.print("Enter seats for " + c + ": ");
        int seats = scanner.nextInt();
        System.out.print("Enter price for " + c + ": ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        seatMap.put(c, seats);
        priceMap.put(c, price);
    }

    Flight newFlight = new Flight(flightNum, airline, origin, destination,
            departureTime, arrivalTime, seatMap, priceMap);

    managedFlights.add(newFlight);
    System.out.println(" Flight added: " + newFlight.getFlightNum());
}

private void viewFlights() {
    if (managedFlights.isEmpty()) {
        System.out.println("No flights available.");
        return;
    }

    for (Flight flight : managedFlights) {
        System.out.println("Flight #" + flight.getFlightNum() + " | " +
                flight.getAirline() + " | " + flight.getOrigin() + " → " +
                flight.getDestination() + " | Departure: " + flight.getDepartureTime() +
                " | Arrival: " + flight.getArrivalTime());
    }
}

private void removeFlight() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter flight number to remove: ");
    int flightNum = scanner.nextInt();
    scanner.nextLine();

    boolean removed = managedFlights.removeIf(f -> f.getFlightNum() == flightNum);
    if (removed) {
        System.out.println("Flight removed.");
    } else {
        System.out.println("Flight not found.");
    }
}

}
