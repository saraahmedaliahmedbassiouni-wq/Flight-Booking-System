import java.io.*;
import java.util.*;
import java.util.stream.*;

public class FileManager {
    private static final String DATA_DIR = "data/";
    private static final String[] FILES = {
        "users.txt", "flights.txt", "bookings.txt", "passengers.txt" , "logs.txt"
    };

    static { new File(DATA_DIR).mkdirs(); }

// define save method
    private static void save(String path, List<String> lines) throws IOException {
        try (PrintWriter pw = new PrintWriter(path)) {
            lines.forEach(pw::println);
        }
    }
// define load method
    private static List<String> load(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) return new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines().collect(Collectors.toList());
        }
    }

    public static void saveUsers(List<user> users) throws IOException {
        save(DATA_DIR + FILES[0], users.stream().map(FileManager::userToString).toList());
    }

    public static List<user> loadUsers() throws IOException {
    List<String> lines = load(DATA_DIR + FILES[0]);
    if (lines == null) return new ArrayList<>();
    return new ArrayList<>(lines.stream()
    .map(FileManager::stringToUser)
    .filter(Objects::nonNull)
    .toList());

}

    public static void saveFlights(List<Flight> flights) throws IOException {
        save(DATA_DIR + FILES[1], flights.stream().map(FileManager::flightToString).toList());
    }

    public static List<Flight> loadFlights() throws IOException {
        return load(DATA_DIR + FILES[1]).stream().map(FileManager::stringToFlight).filter(Objects::nonNull).toList();
    }

    public static void saveBookings(List<Booking> bookings) throws IOException {
        save(DATA_DIR + FILES[2], bookings.stream().map(FileManager::bookingToString).toList());
    }

    public static List<Booking> loadBookings(List<Customer> customers, List<Flight> flights) throws IOException {
        return load(DATA_DIR + FILES[2]).stream()
            .map(line -> stringToBooking(line, customers, flights))
            .filter(Objects::nonNull)
            .toList();
    }

    public static void savePassengers(List<Passenger> passengers) throws IOException {
        save(DATA_DIR + FILES[3], passengers.stream().map(FileManager::passengerToString).toList());
    }

    public static List<Passenger> loadPassengers() throws IOException {
        return load(DATA_DIR + FILES[3]).stream()
            .map(FileManager::stringToPassenger)
            .filter(Objects::nonNull)
            .toList();
    }
    public static void saveLogs(List<String> logs) throws IOException {
    save(DATA_DIR + FILES[4], logs);
 }


   public static List<String> loadLogs() throws IOException {
    return load(DATA_DIR + FILES[4]);
 }

    // Conversion methods
    private static String userToString(user u) {
        String base = String.join(",", u.getUsername(), u.getPassword(), 
            String.valueOf(u.getId()), u.getName(), u.getEmail(), u.getContactInfo());
        
        if (u instanceof Customer) {
            Customer c = (Customer)u;
            return String.join(",", base,"Customer" , String.valueOf(c.getCustomerId()), 
                c.getAddress(), c.getBookingHistory(), c.getPreference());
        } else if (u instanceof Agent) {
            Agent a = (Agent)u;
            return String.join(",", base,"Agent", String.valueOf( a.getAgentID()), 
                a.getDepartment(), String.valueOf(a.getCommission()));
        } else if (u instanceof Administrator) {
            Administrator admin = (Administrator)u;
            return String.join(",", base,"Administrator", String.valueOf((int) admin.getAdminID()), 
                String.valueOf(admin.getSecurityLevel()));
        }
        return base;
    }

   
    private static user stringToUser(String s) {
        String[] parts = s.split(",");
        try {
            switch (parts[6]) {
                case "customer","Customer" -> {
                    return new Customer(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3],
                            parts[4], parts[5], Long.parseLong(parts[7]), parts[8], parts[9], parts[10]);
                }
                case "agent","Agent" -> {
                    return new Agent(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3],
                            parts[4], parts[5], parts[8], Long.parseLong(parts[7]), Double.parseDouble(parts[9]));
                }
                case "admin" , "Administrator" -> {
                    return new Administrator(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3],
                            parts[4], parts[5], Long.parseLong(parts[7]), Integer.parseInt(parts[8]));
                }
                default -> {
                     System.out.println("Unknown user type: " + parts[6]);
                  return null;
                }
            }
        } catch (NumberFormatException e) { return null; }
    }

   private static String flightToString(Flight f) {
    String flightnum = String.valueOf(f.getFlightNum());
    String dep = String.valueOf(f.getDepartureTime());
    String arv = String.valueOf(f.getArrivalTime());
    String base = String.join(",", flightnum, f.getAirline(), 
        f.getOrigin(), f.getDestination(), 
        dep, arv, f instanceof Domestic ? "Domestic" : "International");

    for (String seat : f.getPrices().keySet()) {
        base += String.format(",%s:%.2f:%d", seat, 
            f.getPrices().get(seat), f.getAvailableseats().get(seat));
    }
    return base;
}


    private static Flight stringToFlight(String s) {
    String[] parts = s.split(",");
    int flightNum = Integer.parseInt(parts[0]);
    try {
        String type = parts[6];
        if (type.equals("Domestic")) {
            Map<String, Double> prices = new HashMap<>();
            Map<String, Integer> availableseats = new HashMap<>();

            for (int i = 8; i < parts.length; i++) {
                String[] seatInfo = parts[i].split(":");
                prices.put(seatInfo[0], Double.valueOf(seatInfo[1]));
                availableseats.put(seatInfo[0], Integer.valueOf(seatInfo[2]));
            }

            return new Domestic(
                flightNum,
                parts[1], 
                parts[2], 
                parts[3], 
                Double.parseDouble(parts[4]), 
                Double.parseDouble(parts[5]), 
                availableseats,
                prices
            );
        } else if(type.equals("International")) {
           Map<String, Double> prices = new HashMap<>();
            Map<String, Integer> availableseats = new HashMap<>();

            for (int i = 8; i < parts.length; i++) {
                String[] seatInfo = parts[i].split(":");
                prices.put(seatInfo[0], Double.valueOf(seatInfo[1]));
                availableseats.put(seatInfo[0], Integer.valueOf(seatInfo[2]));
            }
            return new International(
                flightNum,
                parts[1],
                parts[2],
                parts[3],
                Double.parseDouble(parts[4]),
                Double.parseDouble(parts[5]),
                availableseats,
                prices
            );
        }
    } catch (NumberFormatException e) {
        return null;
    }
    return null;
}

    private static String bookingToString(Booking b) {
         String customerId = String.valueOf(b.getCustomer().getCustomerId());
         String passengersStr = String.join(":", b.getPassengers());
         String seatsStr = String.join(":", b.getSeatSelections());
         String flightnum = String.valueOf(b.getFlight().getFlightNum());
         String joined = String.join(",", 
            b.getBookingReference(), 
            customerId,
            flightnum,
            passengersStr,
            seatsStr,
            b.getStatus(),
            b.getPaymentStatus() );
            return joined;
    }
   private static Booking stringToBooking(String line, List<Customer> customers, List<Flight> flights) {
    try {
        String[] parts = line.split(",");
        if (parts.length != 7) return null;

        String bookingRef = parts[0];
        long customerId = Long.parseLong(parts[1]);
        String flightNum = parts[2];

        Customer customer = null;
        for (Customer c : customers) {
            if (c.getCustomerId() == customerId) {
                customer = c;
                break;
            }
        }

        Flight flight = null;
        for (Flight f : flights) {
            if (String.valueOf(f.getFlightNum()).equals(flightNum)) {
                flight = f;
                break;
            }
        }

        if (customer == null || flight == null) return null;

        Booking booking = new Booking(customer, flight);
        booking.setBookingReference(bookingRef);

        String[] passengerNames = parts[3].split(":");
        String[] seatClasses = parts[4].split(":");

        for (int i = 0; i < passengerNames.length && i < seatClasses.length; i++) {
            booking.getPassengers().add(passengerNames[i]);
            booking.getSeatSelections().add(seatClasses[i]);
        }

        booking.setStatus(parts[5]);
        booking.setPaymentStatus(parts[6]);

        return booking;

    } catch (Exception e) {
        System.out.println(" " + e.getMessage());
        return null;
    }
}
/*public Passenger(long passengerId, String name, String passportNumber, String dateOfBirth, String specialRequests) */
   private static String passengerToString (Passenger p) {
    String id = String.valueOf(p.getPassengerId());
    String joined = String.join("," , id , p.getName() ,p.getPassportNumber() , p.getDateOfBirth() , p.getSpecialRequests());
    return joined;
}

private static Passenger stringToPassenger (String joined) {
    try {
        String[] parts = joined.split(",", -1); // -1 keeps empty fields
        if (parts.length != 5) return null; // invalid data format

        long id = Long.parseLong(parts[0]);
        String name = parts[1];
        String passportNumber = parts[2];
        String dateOfBirth = parts[3];
        String specialRequests = parts[4];

        return new Passenger(id, name, passportNumber, dateOfBirth, specialRequests);
    } catch (Exception e) {
        e.printStackTrace(); 
        return null;
    }
}

   
}
