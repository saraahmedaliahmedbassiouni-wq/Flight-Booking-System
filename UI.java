import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    private BookingSystem system;
    private Scanner scanner;
    private user currentUser = null;

    public UI(BookingSystem system) {
        this.system = system;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                switch (currentUser.getRole()) {
                    case "Admin" -> showAdminMenu();
                    case "Customer" -> showCustomerMenu();
                    case "Agent" -> showAgentMenu();
                    default -> {
                        System.out.println("Unknown role.");
                        logout();
                    }
                }
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n Login Menu:");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        List<user> users;
          try {
            users = FileManager.loadUsers(); 
        } catch (IOException e) {
             System.out.println(" Error loading users: " + e.getMessage());
            return;
        }

        user matchedUser = users.stream()
            .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
            .findFirst()
            .orElse(null);

        if (matchedUser != null) {
            currentUser = matchedUser;
            System.out.println(" Login successful! Welcome " );
            logLoginAttempt(username, "successful");
        } else {
            System.out.println(" Invalid username or password.");
            logLoginAttempt(username, "failed");
        }
    }

    private void logLoginAttempt(String username, String status) {
    String logEntry = "Login attempt: Username: " + username + " Status: " + status;

    try {
        List<String> logs = FileManager.loadLogs();
        logs.add(logEntry);
        FileManager.saveLogs(logs);
    } catch (IOException e) {
        System.out.println("Error saving login logs: " + e.getMessage());
    }
}

    private void logout() {
        System.out.println(" Logging out...");
        currentUser = null;
    }

    private void showAdminMenu() {
        while (true) {
            System.out.println("\n Admin Menu:");
            System.out.println("1. Create User");
            System.out.println("2. Modify System Settings");
            System.out.println("3. View System Logs");
            System.out.println("4. Manage User Access");
            System.out.println("5. Logout");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                       Administrator admin = (Administrator) currentUser;
                       if (!admin.createUser()) {
                          System.out.println("Failed to create user.");
                    }
                }
                case 2 -> {
                        if (currentUser instanceof Administrator admin) {
                                System.out.print("Enter new theme (light/dark): ");
                                String theme = scanner.nextLine();
                                System.out.print("Enable auto-save? (true/false): ");
                                 boolean autoSave = Boolean.parseBoolean(scanner.nextLine());
                                 System.out.print("Enter language (e.g., en/ar): ");
                                String language = scanner.nextLine();
                                boolean updated = admin.modifySystemSettings(theme, autoSave, language);

                             if (updated) {
                                  System.out.println("System settings updated successfully.");
                            } else {
                                  System.out.println("You don't have permission to modify settings.");
                            }
                     } else {
                          System.out.println("Only administrators can modify system settings.");
                    }
                     
                }
                case 3 -> {
                   if (currentUser instanceof Administrator admin) {
                     String logs = admin.viewSystemLogs();
                     System.out.println(logs);
                 } else {
                      System.out.println("Only administrators can view system logs.");
                 }
                }
                case 4 -> {
                if (currentUser instanceof Administrator admin) {
                 System.out.print("Enter the username of the user: ");
                 String username = scanner.nextLine();
                  System.out.print("Enter the new role (e.g., customer/agent/admin): ");
                 String newRole = scanner.nextLine();
                  boolean updated = admin.manageUserAccess(username, newRole);
                       if (updated) {
                       System.out.println("User role updated successfully.");
                     } else {
                      System.out.println("Failed to update user access. You might not have permission, or user was not found.");
                    }
              } else {
                  System.out.println("Only administrators can manage user access.");
             }}
            case 5 -> {
               logout();
               return;
            }
                default -> System.out.println("Invalid choice.");

                
            }
            
            }
        }
    
            

   
    private void showCustomerMenu() {
        while (true) {
            System.out.println("\n🧍 Customer Menu:");
            System.out.println("1. Search Flights");
            System.out.println("2. Book a Flight");
            System.out.println("3. View My Bookings");
            System.out.println("4. Logout");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Origin: ");
                    String origin = scanner.nextLine();
                    System.out.print("Destination: ");
                    String dest = scanner.nextLine();
                    System.out.print("Airline: ");
                    String airline = scanner.nextLine();
                    System.out.print("Departure Time: ");
                    double dep = scanner.nextDouble();
                    System.out.print("Arrival Time: ");
                    double arr = scanner.nextDouble();
                    scanner.nextLine();

                    List<Flight> results = system.searchFlights(0, airline, origin, dest, dep, arr);
                    results.forEach(System.out::println);
                }
                case 2 -> {
                    System.out.println(" Available Flights:");
                    system.getFlights().forEach(System.out::println);
                    System.out.print("Enter Flight Number to Book: ");
                    int flightNum = scanner.nextInt();
                    scanner.nextLine();

                    Flight chosen = system.getFlights().stream()
                            .filter(f -> f.getFlightNum() == flightNum)
                            .findFirst().orElse(null);

                    if (chosen == null) {
                        System.out.println(" Flight not found.");
                        break;
                    }

                    System.out.print("Enter number of passengers: ");
                    int count = scanner.nextInt();
                    scanner.nextLine();

                    List<String> names = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        System.out.print("Passenger " + (i + 1) + " name: ");
                        names.add(scanner.nextLine());
                    }

                    Booking booking = system.createBooking((Customer) currentUser, chosen, names);
                    System.out.println(" Booking Created: " + booking.getBookingReference());
                }
                case 3 -> {
                    system.getBookings().stream()
                        .filter(b -> b.getCustomer().equals(currentUser))
                        .forEach(System.out::println);
                }
                case 4 -> {
                    logout();
                    return;
                }
                default -> System.out.println(" Invalid choice.");
            }
        }
    }

    private void showAgentMenu() {
        while (true) {
            System.out.println("\n Agent Menu:");
            System.out.println("1. Manage Flights");
            System.out.println("2. Create Booking For Customer");
            System.out.println("3. Modify Booking");
            System.out.println("4. Generate Report ");
            System.out.println("5.Logout");
            System.out.println("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    if (currentUser instanceof Agent agent) {
                         agent.manageFlights();
                    } else {
                        System.out.println("Only agents can manage flights.");
                    }
                }
                case 2 -> {
                    if (currentUser instanceof Agent agent) {
                      agent.createBookingForCustomer();
                    } else {
                        System.out.println("Only agents can create bookings.");
                    }
                }
                case 3 -> {
                     if (currentUser instanceof Agent agent) {
                          agent.modifyBooking();
                    } else {
                         System.out.println("Only agents can modify bookings.");
                }
            }
            case 4 ->{
                if (currentUser instanceof Agent agent) {
                         agent.generateReports();
                      } else {
                          System.out.println("Only agents can generate reports.");
                }
            }
            case 5 -> {
                logout();
                return;
            }
                default -> System.out.println(" Invalid choice.");
            }
        }
    }

    
}
