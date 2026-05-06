import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FlightBookingUI extends JFrame implements ActionListener {

    private BookingSystem bookingSystem;
    private user currentUser = null; 


    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu menuFlights, menuBookings, menuAccount;
    private JMenuItem menuItemSearch, menuItemNewBooking, menuItemViewBookings, menuItemProfile, menuItemLogout;

    // Flight Search Components
    private JPanel searchPanel;
    private JLabel labelOrigin, labelDestination, labelDepartureTime, labelArrivalTime, labelAirline;
    private JTextField textOrigin, textDestination, textDepartureTime, textArrivalTime, textAirline;
    private JButton buttonSearch;
    private JTable flightsTable;
    private DefaultTableModel flightsTableModel;

    // Booking Form Components
    private JPanel bookingPanel;
    private JLabel labelFlightNumberForBooking, labelPassengerNames;
    private JTextField textFlightNumberForBooking, textPassengerNames;
    private JButton buttonBook;

    // View Bookings Panel
    private JPanel viewBookingsPanel;
    private JTable bookingsTable;
    private DefaultTableModel bookingsTableModel;

    // Dashboard Components
    private JPanel dashboardPanel;
    private JLabel labelDashboardWelcome;

    // login components
    // Login Panel
private JPanel loginPanel;
private JTextField loginUsernameField;
private JPasswordField loginPasswordField;
private JButton loginButton;


    public FlightBookingUI() {
        setTitle("Flight Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        bookingSystem = new BookingSystem();
        loadInitialData();

        // GUI Structure
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createMenuBar();
        createDashboardPanel();
        createSearchPanel();
        createBookingPanel();
        createViewBookingsPanel();
        createLoginPanel();
        mainPanel.add(loginPanel, "login");


        mainPanel.add(dashboardPanel, "dashboard");
        mainPanel.add(searchPanel, "search");
        mainPanel.add(bookingPanel, "booking");
        mainPanel.add(viewBookingsPanel, "viewBookings");

        add(mainPanel);
        setJMenuBar(menuBar);

        currentUser = bookingSystem.getUsers().stream().filter(u -> u instanceof Customer).findFirst().orElse(null);
        if (currentUser != null) {
            labelDashboardWelcome.setText("Welcome, " + currentUser.getName() + "!");
        }

        cardLayout.show(mainPanel, "dashboard");
        setVisible(true);

        populateFlightsTable();
        updateViewBookingsTable();
    }

    private void loadInitialData() {
        try {
            List<user> users = FileManager.loadUsers();
            bookingSystem.getUsers().addAll(users);
            List<Flight> flights = FileManager.loadFlights();
            bookingSystem.getFlights().addAll(flights);
            List<Passenger> passengers = FileManager.loadPassengers();
            bookingSystem.setPassengers(passengers);

            List<Customer> customers = users.stream()
                    .filter(u -> u instanceof Customer)
                    .map(u -> (Customer) u)
                    .collect(Collectors.toList());
            List<Booking> bookings = FileManager.loadBookings(customers, flights);
            bookingSystem.getBookings().addAll(bookings);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        menuFlights = new JMenu("Flights");
        menuBookings = new JMenu("Bookings");
        menuAccount = new JMenu("Account");

        menuItemSearch = new JMenuItem("Search Flights");
        menuItemNewBooking = new JMenuItem("New Booking");
        menuItemViewBookings = new JMenuItem("View Bookings");
        menuItemProfile = new JMenuItem("Profile");
        menuItemLogout = new JMenuItem("Logout");

        menuItemSearch.addActionListener(this);
        menuItemNewBooking.addActionListener(this);
        menuItemViewBookings.addActionListener(this);
        menuItemProfile.addActionListener(this);
        menuItemLogout.addActionListener(this);

        menuFlights.add(menuItemSearch);
        menuBookings.add(menuItemNewBooking);
        menuBookings.add(menuItemViewBookings);
        menuAccount.add(menuItemProfile);
        menuAccount.add(menuItemLogout);

        menuBar.add(menuFlights);
        menuBar.add(menuBookings);
        menuBar.add(menuAccount);
        setJMenuBar(menuBar);
    }

    private void createDashboardPanel() {
        dashboardPanel = new JPanel();
        labelDashboardWelcome = new JLabel("Welcome to the Flight Booking System!");
        labelDashboardWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        dashboardPanel.add(labelDashboardWelcome);
    }

    private void createSearchPanel() {
        searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        labelOrigin = new JLabel("Origin:");
        textOrigin = new JTextField();
        labelDestination = new JLabel("Destination:");
        textDestination = new JTextField();
        labelDepartureTime = new JLabel("Departure Time:");
        textDepartureTime = new JTextField();
        labelArrivalTime = new JLabel("Arrival Time:");
        textArrivalTime = new JTextField();
        labelAirline = new JLabel("Airline:");
        textAirline = new JTextField();
        buttonSearch = new JButton("Search");
        buttonSearch.addActionListener(this);

        inputPanel.add(labelOrigin);
        inputPanel.add(textOrigin);
        inputPanel.add(labelDestination);
        inputPanel.add(textDestination);
        inputPanel.add(labelDepartureTime);
        inputPanel.add(textDepartureTime);
        inputPanel.add(labelArrivalTime);
        inputPanel.add(textArrivalTime);
        inputPanel.add(labelAirline);
        inputPanel.add(textAirline);
        inputPanel.add(new JLabel()); 
        inputPanel.add(buttonSearch);

        flightsTableModel = new DefaultTableModel(new Object[]{"Flight #", "Airline", "Origin", "Destination", "Departure", "Arrival", "Price"}, 0);
        flightsTable = new JTable(flightsTableModel);
        JScrollPane scrollPane = new JScrollPane(flightsTable);

        searchPanel.add(inputPanel, BorderLayout.NORTH);
        searchPanel.add(new JLabel("Search Results:", SwingConstants.CENTER), BorderLayout.CENTER);
        searchPanel.add(scrollPane, BorderLayout.SOUTH);
    }

    private void populateFlightsTable() {
        flightsTableModel.setRowCount(0);
        for (Flight flight : bookingSystem.getFlights()) {
            String price = flight.getPrices().containsKey("Economy") ? "$" + flight.getPrices().get("Economy") : "N/A";
            flightsTableModel.addRow(new Object[]{
                    flight.getFlightNum(),
                    flight.getAirline(),
                    flight.getOrigin(),
                    flight.getDestination(),
                    flight.getDepartureTime(),
                    flight.getArrivalTime(),
                    price
            });
        }
    }

    private void createBookingPanel() {
        bookingPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        bookingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        labelFlightNumberForBooking = new JLabel("Flight Number:");
        textFlightNumberForBooking = new JTextField();
        labelPassengerNames = new JLabel("Passenger Name(s) (comma-separated):");
        textPassengerNames = new JTextField();
        buttonBook = new JButton("Book Flight");
        buttonBook.addActionListener(this);

        bookingPanel.add(labelFlightNumberForBooking);
        bookingPanel.add(textFlightNumberForBooking);
        bookingPanel.add(labelPassengerNames);
        bookingPanel.add(textPassengerNames);
        bookingPanel.add(new JLabel()); 
        bookingPanel.add(buttonBook);
    }

    private void createViewBookingsPanel() {
        viewBookingsPanel = new JPanel(new BorderLayout());
        viewBookingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        bookingsTableModel = new DefaultTableModel(new Object[]{"Booking Ref", "Flight #", "Passengers", "Status", "Payment"}, 0);
        bookingsTable = new JTable(bookingsTableModel);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);

        viewBookingsPanel.add(new JLabel("Your Bookings:", SwingConstants.CENTER), BorderLayout.NORTH);
        viewBookingsPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void displaySearchResults(String origin, String destination, String departureTimeStr, String arrivalTimeStr, String airline) {
        flightsTableModel.setRowCount(0);
        try {
            double departureTime = Double.parseDouble(departureTimeStr.isEmpty() ? "-1" : departureTimeStr);
            double arrivalTime = Double.parseDouble(arrivalTimeStr.isEmpty() ? "-1" : arrivalTimeStr);

            List<Flight> results = bookingSystem.searchFlights(-1, airline.isEmpty() ? null : airline, origin, destination, departureTime, arrivalTime);
            for (Flight flight : results) {
                String price = flight.getPrices().containsKey("Economy") ? "$" + flight.getPrices().get("Economy") : "N/A";
                flightsTableModel.addRow(new Object[]{
                        flight.getFlightNum(),
                        flight.getAirline(),
                        flight.getOrigin(),
                        flight.getDestination(),
                        flight.getDepartureTime(),
                        flight.getArrivalTime(),
                        price
                });
            }
            JOptionPane.showMessageDialog(this, "Search results for " + origin + " to " + destination);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid time format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performBooking(String flightNumberStr, String passengerNamesStr) {
        if (currentUser instanceof Customer customer) {
            try {
                int flightNumber = Integer.parseInt(flightNumberStr);
                List<Flight> foundFlights = bookingSystem.getFlights().stream()
                        .filter(f -> f.getFlightNum() == flightNumber)
                        .collect(Collectors.toList());

                if (!foundFlights.isEmpty()) {
                    Flight flightToBook = foundFlights.get(0);
                    List<String> passengers = List.of(passengerNamesStr.split(","));
                    Booking booking = bookingSystem.createBooking(customer, flightToBook, passengers);
                    JOptionPane.showMessageDialog(this, "Booking created with reference: " + booking.getBookingReference());
                    cardLayout.show(mainPanel, "dashboard");
                    updateViewBookingsTable(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Flight number not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid flight number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "You must be logged in as a customer to book.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logout() {
        currentUser = null;
        labelDashboardWelcome.setText("Welcome to the Flight Booking System!");
        cardLayout.show(mainPanel, "dashboard");
        JOptionPane.showMessageDialog(this, "Logged out successfully.");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == menuItemSearch) cardLayout.show(mainPanel, "search");
        else if (src == menuItemNewBooking) cardLayout.show(mainPanel, "booking");
        else if (src == menuItemViewBookings) {
            updateViewBookingsTable();
            cardLayout.show(mainPanel, "viewBookings");
        }
        else if (src == menuItemProfile) JOptionPane.showMessageDialog(this, "User Profile: " + currentUser.getName());
        else if (src == menuItemLogout) System.exit(0);
        else if (src == buttonSearch) displaySearchResults(textOrigin.getText(), textDestination.getText(), textDepartureTime.getText(), textArrivalTime.getText(), textAirline.getText());
        else if (src == buttonBook) performBooking(textFlightNumberForBooking.getText(), textPassengerNames.getText());
        else if (src == loginButton)performLogin(loginUsernameField.getText(), new String(loginPasswordField.getPassword()));

    }

    private void updateViewBookingsTable() {
        bookingsTableModel.setRowCount(0);
        if (currentUser instanceof Customer customer) {
            for (Booking booking : bookingSystem.getBookings()) {
                if (booking.getCustomer().equals(customer)) {
                    bookingsTableModel.addRow(new Object[] {
                        booking.getBookingReference(),
                        booking.getFlight().getFlightNum(),
                        booking.getPassengers().stream().collect(Collectors.joining(", ")),
                        booking.getStatus(),
                        booking.getPaymentStatus()
                    });
                }
            }}
        }

        private void createLoginPanel() {
    loginPanel = new JPanel(new GridLayout(4, 2, 10, 10));
    loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

    JLabel usernameLabel = new JLabel("Username:");
    loginUsernameField = new JTextField();

    JLabel passwordLabel = new JLabel("Password:");
    loginPasswordField = new JPasswordField();

    loginButton = new JButton("Login");
    loginButton.addActionListener(this);

    loginPanel.add(usernameLabel);
    loginPanel.add(loginUsernameField);
    loginPanel.add(passwordLabel);
    loginPanel.add(loginPasswordField);
    loginPanel.add(new JLabel());
    loginPanel.add(loginButton);
}
private void performLogin(String username, String password) {
    for (user u : bookingSystem.getUsers()) {
        if (u.getName().equals(username) && u.getPassword().equals(password)) {
            currentUser = u;
            labelDashboardWelcome.setText("Welcome, " + currentUser.getName() + "!");
            JOptionPane.showMessageDialog(this, "Login successful. Welcome " + u.getName() + "!");
            cardLayout.show(mainPanel, "dashboard");
            return;
        }
    }
    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
}



    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> {
        FlightBookingUI app = new FlightBookingUI();
        app.cardLayout.show(app.mainPanel, "login");
    });


    }
}