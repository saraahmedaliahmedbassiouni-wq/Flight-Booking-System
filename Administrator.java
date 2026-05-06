import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Administrator extends user {
    private long adminID;
    private int securityLevel;
    private  SystemSettings systemSettings = new SystemSettings("dark" , false ,"en");
    
    public Administrator(String username, String password, int id, String name, String email, String contactInfo,
                       long adminID, int securityLevel) {
        super(username, password, id, name, email, contactInfo);
        this.adminID = adminID;
        this.securityLevel = securityLevel;
        this.setRole("Admin");
    }

    public long getAdminID() {
        return adminID;
    }

    public void setAdminID(long adminID) {
        this.adminID = adminID;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }
    public boolean createUser() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter user role (Customer / Agent / Admin): ");
    String role = scanner.nextLine().trim().toLowerCase();

    return switch (role) {
        case "customer" -> createCustomer();
        case "agent" -> createAgent();
        case "admin" -> createAdmin();
        default -> {
            System.out.println(" Invalid role. User creation aborted.");
            yield  false;
        }
    };
}

    private boolean createCustomer() {
    Scanner scanner = new Scanner(System.in);
    try {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        if (isUsernameTaken(username)) {
           System.out.println(" This username is already taken. Please choose another one.");
           username = scanner.nextLine();  
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();
        while (!isValidPassword(password)) {
            System.out.println(" Password must be at least 6 characters and contain both letters and numbers.");
            System.out.print("Please enter a valid password: ");
            password = scanner.nextLine();
        }

        System.out.print("User ID: ");
        int id = scanner.nextInt(); scanner.nextLine();

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Contact Info: ");
        String contact = scanner.nextLine();

        System.out.print("Customer ID: ");
        long customerId = scanner.nextLong(); scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("Booking History: ");
        String history = scanner.nextLine();

        System.out.print("Preference: ");
        String pref = scanner.nextLine();

        Customer customer = new Customer(username, password, id, name, email, contact, customerId, address, history, pref);

        List<user> users = FileManager.loadUsers();
        users.add(customer);
        FileManager.saveUsers(users);

        System.out.println(" Customer created successfully.");
        return true;
    } catch (Exception e) {
        System.err.println("Error creating customer: " + e.getMessage());
        return false;
    }
}
    private boolean createAgent() {
    Scanner scanner = new Scanner(System.in);
    try {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        if (isUsernameTaken(username)) {
           System.out.println(" This username is already taken. Please choose another one.");
           username = scanner.nextLine();  
        }
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        while (!isValidPassword(password)) {
            System.out.println(" Password must be at least 6 characters and contain both letters and numbers.");
            System.out.print("Please enter a valid password: ");
            password = scanner.nextLine();
        }
        System.out.print("User ID: ");
        int id = scanner.nextInt(); scanner.nextLine();

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Contact Info: ");
        String contact = scanner.nextLine();

        System.out.print("Department: ");
        String department = scanner.nextLine();

        System.out.print("Agent ID: ");
        long agentId = scanner.nextLong(); scanner.nextLine();

        System.out.print("Commission: ");
        double commission = scanner.nextDouble();

        Agent agent = new Agent(username, password, id, name, email, contact, department, agentId, commission);

        List<user> users = FileManager.loadUsers();
        users.add(agent);
        FileManager.saveUsers(users);

        System.out.println(" Agent created successfully.");
        return true;
    } catch (Exception e) {
          e.printStackTrace();
        System.err.println("Error creating agent: " + e.getMessage());
        return false;
    }
}
private boolean createAdmin() {
    Scanner scanner = new Scanner(System.in);
    try {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        if (isUsernameTaken(username)) {
           System.out.println(" This username is already taken. Please choose another one.");
           username = scanner.nextLine();  
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();
        while (!isValidPassword(password)) {
            System.out.println(" Password must be at least 6 characters and contain both letters and numbers.");
            System.out.print("Please enter a valid password: ");
            password = scanner.nextLine();
        }

        System.out.print("User ID: ");
        int id = scanner.nextInt(); scanner.nextLine();

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Contact Info: ");
        String contact = scanner.nextLine();

        System.out.print("Admin ID: ");
        long adminId = scanner.nextLong();

        System.out.print("Security Level (1-3): ");
        int level = scanner.nextInt();

        Administrator admin = new Administrator(username, password, id, name, email, contact, adminId, level);

        List<user> users = FileManager.loadUsers();
        users.add(admin);
        FileManager.saveUsers(users);

        System.out.println(" Admin created successfully.");
        return true;
    } catch (Exception e) {
        System.err.println("Error creating admin: " + e.getMessage());
        return false;
    }
}




    
    public boolean  modifySystemSettings(String theme, boolean autoSave, String language) {
        if (securityLevel >= 2) {
            systemSettings.setTheme(theme);
            systemSettings.setAutoSave(autoSave);
            systemSettings.setLanguage(language);
            System.out.println("Settings updated!");
            return true;
        }
        return false;
    }

    public String viewSystemLogs() {
    if (securityLevel >= 1) {
        try {
            List<String> logs = FileManager.loadLogs();
            
            if (logs.isEmpty()) {
                return "No logs available.";
            }
            return String.join("\n", logs);
        } catch (IOException e) {
            return "Error loading logs: " + e.getMessage();
        }
    } else {
        return "Access denied";
    }
}

   public boolean manageUserAccess(String username, String newRole) {
    if (securityLevel < 3) return false;  // Only allow if the security level is 3 or higher

    // Check if the new role is valid (either "Agent" or "Customer")
    if (!newRole.equalsIgnoreCase("Agent") && !newRole.equalsIgnoreCase("Customer")) {
        System.out.println("Invalid role. Only 'Agent' or 'Customer' are allowed.");
        return false;
    }

    try {
        List<user> users = FileManager.loadUsers();
        for (user u : users) {
            if (u.getUsername().equals(username)) {
                u.setRole(newRole);  
                FileManager.saveUsers(users); 
                return true;
            }
        }

        System.out.println("User not found.");
        return false;
    } catch (IOException e) {
        System.err.println("Error: " + e.getMessage());
        return false;
    }
}


    public static boolean isValidPassword(String password) {
    if (password.length() < 6) return false;
    boolean hasLetter = false;
    boolean hasDigit = false;
    for (char ch : password.toCharArray()) {
        if (Character.isLetter(ch)) hasLetter = true;
        if (Character.isDigit(ch)) hasDigit = true;
    }
    return hasLetter && hasDigit;
}
private boolean isUsernameTaken(String username) {
    try {
        List<user> users = FileManager.loadUsers();
        return users.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    } catch (IOException e) {
        System.out.println(" Error checking username: " + e.getMessage());
        return false; 
    }
}




}