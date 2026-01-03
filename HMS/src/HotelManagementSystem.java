import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

// Main Application Class
public class HotelManagementSystem extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private DataManager dataManager;

    public HotelManagementSystem() {
        dataManager = new DataManager();
        setTitle("Grand Hotel Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new WelcomePanel(this), "WELCOME");
        mainPanel.add(new CustomerPanel(this), "CUSTOMER");
        mainPanel.add(new StaffLoginPanel(this), "STAFF_LOGIN");
        mainPanel.add(new StaffPanel(this), "STAFF");

        add(mainPanel);
        setVisible(true);
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new HotelManagementSystem());
    }
}

// Welcome Panel
class WelcomePanel extends JPanel {
    public WelcomePanel(HotelManagementSystem app) {
        setLayout(new BorderLayout());
        setBackground(new Color(20, 30, 48));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel titleLabel = new JLabel("GRAND HOTEL");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 56));
        titleLabel.setForeground(new Color(255, 215, 0));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centerPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Luxury & Comfort Redefined");
        subtitleLabel.setFont(new Font("SansSerif", Font.ITALIC, 20));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        gbc.gridy = 1;
        centerPanel.add(subtitleLabel, gbc);

        JButton customerBtn = createStyledButton("Guest Portal", new Color(41, 128, 185));
        customerBtn.addActionListener(e -> app.showPanel("CUSTOMER"));
        gbc.gridy = 2; gbc.gridwidth = 1;
        centerPanel.add(customerBtn, gbc);

        JButton staffBtn = createStyledButton("Staff Portal", new Color(155, 89, 182));
        staffBtn.addActionListener(e -> app.showPanel("STAFF_LOGIN"));
        gbc.gridx = 1;
        centerPanel.add(staffBtn, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(250, 60));
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.brighter());
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
        return btn;
    }
}

// Staff Login Panel
class StaffLoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public StaffLoginPanel(HotelManagementSystem app) {
        setLayout(new GridBagLayout());
        setBackground(new Color(44, 62, 80));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Login Panel Container
        JPanel loginContainer = new JPanel(new GridBagLayout());
        loginContainer.setBackground(Color.WHITE);
        loginContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(155, 89, 182), 3),
                new EmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints lgbc = new GridBagConstraints();
        lgbc.insets = new Insets(10, 10, 10, 10);
        lgbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Staff Login");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(155, 89, 182));
        lgbc.gridx = 0; lgbc.gridy = 0; lgbc.gridwidth = 2;
        loginContainer.add(titleLabel, lgbc);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Enter your credentials to continue");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);
        lgbc.gridy = 1;
        loginContainer.add(subtitleLabel, lgbc);

        lgbc.gridwidth = 1;
        lgbc.gridy = 2;

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        lgbc.gridx = 0;
        loginContainer.add(usernameLabel, lgbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(250, 35));
        lgbc.gridx = 1;
        loginContainer.add(usernameField, lgbc);

        // Password
        lgbc.gridy = 3;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        lgbc.gridx = 0;
        loginContainer.add(passwordLabel, lgbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(250, 35));
        lgbc.gridx = 1;
        loginContainer.add(passwordField, lgbc);


        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton loginBtn = new JButton("Login");
        loginBtn.setPreferredSize(new Dimension(120, 40));
        loginBtn.setBackground(new Color(46, 204, 113));
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton backBtn = new JButton("Back");
        backBtn.setPreferredSize(new Dimension(120, 40));
        backBtn.setBackground(new Color(231, 76, 60));
        backBtn.setForeground(Color.BLACK);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginBtn.addActionListener(e -> attemptLogin(app));
        backBtn.addActionListener(e -> app.showPanel("WELCOME"));

        // Add Enter key listener to password field
        passwordField.addActionListener(e -> attemptLogin(app));

        buttonPanel.add(loginBtn);
        buttonPanel.add(backBtn);

        lgbc.gridy = 5; lgbc.gridwidth = 2;
        loginContainer.add(buttonPanel, lgbc);

        gbc.gridx = 0; gbc.gridy = 0;
        add(loginContainer, gbc);
    }

    private void attemptLogin(HotelManagementSystem app) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (app.getDataManager().authenticateStaff(username, password)) {
            JOptionPane.showMessageDialog(this,
                    "Login Successful! Welcome, " + username,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            usernameField.setText("");
            passwordField.setText("");
            app.showPanel("STAFF");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
}

// Customer Panel
class CustomerPanel extends JPanel {
    private HotelManagementSystem app;
    private JPanel contentPanel;
    private CardLayout contentLayout;

    public CustomerPanel(HotelManagementSystem app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(createTopBar(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);

        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);
        contentPanel.add(new SearchRoomsPanel(app), "SEARCH");
        contentPanel.add(new BookRoomPanel(app), "BOOK");
        contentPanel.add(new MyBookingsPanel(app), "MYBOOKINGS");
        contentPanel.add(new CheckoutPanel(app), "CHECKOUT");

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(41, 128, 185));
        topBar.setPreferredSize(new Dimension(0, 70));
        topBar.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("Guest Portal");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        topBar.add(title, BorderLayout.WEST);

        JButton backBtn = new JButton("← Back to Home");
        backBtn.setBackground(new Color(52, 152, 219));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        backBtn.addActionListener(e -> app.showPanel("WELCOME"));
        topBar.add(backBtn, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(20, 0, 20, 0));

        addSidebarButton(sidebar, "Search Rooms", "SEARCH");
        addSidebarButton(sidebar, "Book Room", "BOOK");
        addSidebarButton(sidebar, "My Bookings", "MYBOOKINGS");
        addSidebarButton(sidebar, "Checkout", "CHECKOUT");

        return sidebar;
    }

    private void addSidebarButton(JPanel sidebar, String text, String panel) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(220, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(new Color(44, 62, 80));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(0, 30, 0, 0));
        btn.addActionListener(e -> contentLayout.show(contentPanel, panel));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(52, 73, 94));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(44, 62, 80));
            }
        });
        sidebar.add(btn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
    }
}

// Search Rooms Panel
class SearchRoomsPanel extends JPanel {
    private JTable roomTable;
    private DefaultTableModel tableModel;
    private HotelManagementSystem app;

    public SearchRoomsPanel(HotelManagementSystem app) {
        this.app = app;
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Available Rooms");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Room No", "Type", "Price/Night", "Status", "Features"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        roomTable = new JTable(tableModel);
        roomTable.setRowHeight(35);
        roomTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        roomTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        roomTable.getTableHeader().setBackground(new Color(41, 128, 185));
        roomTable.getTableHeader().setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(roomTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBackground(Color.WHITE);

        JComboBox<String> typeFilter = new JComboBox<>(new String[]{"All Types", "Basic", "Deluxe", "Suite"});
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBackground(new Color(41, 128, 185));
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.setFocusPainted(false);

        refreshBtn.addActionListener(e -> loadRooms(typeFilter.getSelectedItem().toString()));

        filterPanel.add(new JLabel("Filter by Type:"));
        filterPanel.add(typeFilter);
        filterPanel.add(refreshBtn);

        add(filterPanel, BorderLayout.SOUTH);

        loadRooms("All Types");
    }

    private void loadRooms(String filter) {
        tableModel.setRowCount(0);
        List<Room> rooms = app.getDataManager().getAllRooms();
        for (Room room : rooms) {
            if (filter.equals("All Types") || filter.equals(room.type)) {
                tableModel.addRow(new Object[]{
                        room.roomNumber, room.type, "$" + room.price,
                        room.isAvailable ? "Available" : "Occupied", room.features
                });
            }
        }
    }
}

// Book Room Panel
class BookRoomPanel extends JPanel {
    public BookRoomPanel(HotelManagementSystem app) {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Book a Room");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1; gbc.gridy++;

        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField roomField = new JTextField(20);
        JSpinner nightsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Basic", "Deluxe", "Suite"});

        addFormField("Guest Name:", nameField, gbc);
        addFormField("Email:", emailField, gbc);
        addFormField("Phone:", phoneField, gbc);
        addFormField("Room Number:", roomField, gbc);
        addFormField("Room Type:", typeCombo, gbc);
        addFormField("Nights:", nightsSpinner, gbc);

        JButton bookBtn = new JButton("Book Now");
        bookBtn.setBackground(new Color(46, 204, 113));
        bookBtn.setForeground(Color.BLACK);
        bookBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        bookBtn.setFocusPainted(false);

        bookBtn.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty() || roomField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            String roomNum = roomField.getText().trim();
            Room room = app.getDataManager().getRoomByNumber(roomNum);

            if (room == null) {
                JOptionPane.showMessageDialog(this, "Room not found!");
                return;
            }

            if (!room.isAvailable) {
                JOptionPane.showMessageDialog(this, "Room is not available!");
                return;
            }

            int nights = (Integer) nightsSpinner.getValue();
            Booking booking = new Booking(
                    "B" + System.currentTimeMillis(),
                    nameField.getText().trim(),
                    emailField.getText().trim(),
                    phoneField.getText().trim(),
                    roomNum,
                    typeCombo.getSelectedItem().toString(),
                    nights,
                    room.price * nights
            );

            app.getDataManager().addBooking(booking);
            room.isAvailable = false;
            app.getDataManager().saveRooms();

            JOptionPane.showMessageDialog(this,
                    "Booking Confirmed!\nBooking ID: " + booking.bookingId +
                            "\nTotal Amount: $" + booking.totalAmount);

            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            roomField.setText("");
        });

        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        add(bookBtn, gbc);
    }

    private void addFormField(String label, JComponent field, GridBagConstraints gbc) {
        gbc.gridx = 0;
        add(new JLabel(label), gbc);
        gbc.gridx = 1;
        add(field, gbc);
        gbc.gridy++;
    }
}

// My Bookings Panel
class MyBookingsPanel extends JPanel {
    private JTable bookingTable;
    private DefaultTableModel tableModel;

    public MyBookingsPanel(HotelManagementSystem app) {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("My Bookings");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Booking ID", "Guest Name", "Room", "Type", "Nights", "Total", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        bookingTable = new JTable(tableModel);
        bookingTable.setRowHeight(35);
        bookingTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        bookingTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        bookingTable.getTableHeader().setBackground(new Color(41, 128, 185));
        bookingTable.getTableHeader().setForeground(Color.BLACK);

        add(new JScrollPane(bookingTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton refreshBtn = new JButton("Refresh");
        JButton cancelBtn = new JButton("Cancel Selected");

        refreshBtn.setBackground(new Color(41, 128, 185));
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.setFocusPainted(false);

        cancelBtn.setBackground(new Color(231, 76, 60));
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setFocusPainted(false);

        refreshBtn.addActionListener(e -> loadBookings(app));
        cancelBtn.addActionListener(e -> cancelBooking(app));

        buttonPanel.add(refreshBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        loadBookings(app);
    }

    private void loadBookings(HotelManagementSystem app) {
        tableModel.setRowCount(0);
        List<Booking> bookings = app.getDataManager().getAllBookings();
        for (Booking b : bookings) {
            tableModel.addRow(new Object[]{
                    b.bookingId, b.guestName, b.roomNumber, b.roomType,
                    b.numberOfNights, "$" + b.totalAmount, b.status
            });
        }
    }

    private void cancelBooking(HotelManagementSystem app) {
        int row = bookingTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking!");
            return;
        }

        String bookingId = tableModel.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Cancel this booking?");

        if (confirm == JOptionPane.YES_OPTION) {
            Booking booking = app.getDataManager().getBookingById(bookingId);
            if (booking != null) {
                booking.status = "Cancelled";
                Room room = app.getDataManager().getRoomByNumber(booking.roomNumber);
                if (room != null) {
                    room.isAvailable = true;
                }
                app.getDataManager().saveBookings();
                app.getDataManager().saveRooms();
                loadBookings(app);
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!");
            }
        }
    }
}

// Checkout Panel
class CheckoutPanel extends JPanel {
    public CheckoutPanel(HotelManagementSystem app) {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Checkout");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1; gbc.gridy++;

        JTextField bookingIdField = new JTextField(20);
        JTextArea detailsArea = new JTextArea(8, 30);
        detailsArea.setEditable(false);
        detailsArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JComboBox<String> paymentMethod = new JComboBox<>(new String[]{"Cash", "Credit Card", "Debit Card", "UPI"});

        gbc.gridx = 0;
        add(new JLabel("Booking ID:"), gbc);
        gbc.gridx = 1;
        add(bookingIdField, gbc);
        gbc.gridy++;

        JButton searchBtn = new JButton("Search Booking");
        searchBtn.setBackground(new Color(41, 128, 185));
        searchBtn.setForeground(Color.BLACK);
        searchBtn.setFocusPainted(false);

        searchBtn.addActionListener(e -> {
            String id = bookingIdField.getText().trim();
            Booking booking = app.getDataManager().getBookingById(id);
            if (booking == null) {
                detailsArea.setText("Booking not found!");
            } else {
                detailsArea.setText(
                        "Booking ID: " + booking.bookingId + "\n" +
                                "Guest: " + booking.guestName + "\n" +
                                "Email: " + booking.email + "\n" +
                                "Phone: " + booking.phone + "\n" +
                                "Room: " + booking.roomNumber + "\n" +
                                "Type: " + booking.roomType + "\n" +
                                "Nights: " + booking.numberOfNights + "\n" +
                                "Total Amount: $" + booking.totalAmount + "\n" +
                                "Status: " + booking.status
                );
            }
        });

        gbc.gridx = 0; gbc.gridwidth = 2;
        add(searchBtn, gbc);
        gbc.gridy++;

        add(new JScrollPane(detailsArea), gbc);
        gbc.gridy++;

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        add(new JLabel("Payment Method:"), gbc);
        gbc.gridx = 1;
        add(paymentMethod, gbc);
        gbc.gridy++;

        JButton checkoutBtn = new JButton("Complete Checkout");
        checkoutBtn.setBackground(new Color(46, 204, 113));
        checkoutBtn.setForeground(Color.BLACK);
        checkoutBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        checkoutBtn.setFocusPainted(false);

        checkoutBtn.addActionListener(e -> {
            String id = bookingIdField.getText().trim();
            Booking booking = app.getDataManager().getBookingById(id);
            if (booking == null) {
                JOptionPane.showMessageDialog(this, "Please search for a valid booking first!");
                return;
            }

            if (booking.status.equals("Completed")) {
                JOptionPane.showMessageDialog(this, "This booking is already checked out!");
                return;
            }

            booking.status = "Completed";
            Room room = app.getDataManager().getRoomByNumber(booking.roomNumber);
            if (room != null) {
                room.isAvailable = true;
            }
            app.getDataManager().saveBookings();
            app.getDataManager().saveRooms();

            JOptionPane.showMessageDialog(this,
                    "Checkout Successful!\nPayment Method: " + paymentMethod.getSelectedItem() +
                            "\nAmount Paid: $" + booking.totalAmount + "\nThank you for staying with us!");

            bookingIdField.setText("");
            detailsArea.setText("");
        });

        gbc.gridx = 0; gbc.gridwidth = 2;
        add(checkoutBtn, gbc);
    }
}


// Staff Panel
class StaffPanel extends JPanel {
    private HotelManagementSystem app;
    private JPanel contentPanel;
    private CardLayout contentLayout;

    public StaffPanel(HotelManagementSystem app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(createTopBar(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);

        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);
        contentPanel.add(new ManageRoomsPanel(app), "ROOMS");
        contentPanel.add(new ViewBookingsPanel(app), "BOOKINGS");
        contentPanel.add(new ReportsPanel(app), "REPORTS");

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(155, 89, 182));
        topBar.setPreferredSize(new Dimension(0, 70));
        topBar.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("Staff Portal");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        topBar.add(title, BorderLayout.WEST);

        JButton logoutBtn = new JButton("← Logout");
        logoutBtn.setBackground(new Color(142, 68, 173));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutBtn.addActionListener(e -> app.showPanel("WELCOME"));
        topBar.add(logoutBtn, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(20, 0, 20, 0));

        addSidebarButton(sidebar, "Manage Rooms", "ROOMS");
        addSidebarButton(sidebar, "View Bookings", "BOOKINGS");
        addSidebarButton(sidebar, "Reports", "REPORTS");

        return sidebar;
    }

    private void addSidebarButton(JPanel sidebar, String text, String panel) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(220, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(new Color(44, 62, 80));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(0, 30, 0, 0));
        btn.addActionListener(e -> contentLayout.show(contentPanel, panel));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(52, 73, 94));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(44, 62, 80));
            }
        });
        sidebar.add(btn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
    }
}

// Manage Rooms Panel
class ManageRoomsPanel extends JPanel {
    private JTable roomTable;
    private DefaultTableModel tableModel;

    public ManageRoomsPanel(HotelManagementSystem app) {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Manage Rooms");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Room No", "Type", "Price", "Status", "Features"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        roomTable = new JTable(tableModel);
        roomTable.setRowHeight(35);
        roomTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        roomTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        roomTable.getTableHeader().setBackground(new Color(155, 89, 182));
        roomTable.getTableHeader().setForeground(Color.BLACK);

        add(new JScrollPane(roomTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton addBtn = new JButton("Add Room");
        JButton editBtn = new JButton("Edit Room");
        JButton refreshBtn = new JButton("Refresh");

        addBtn.setBackground(new Color(46, 204, 113));
        addBtn.setForeground(Color.BLACK);
        addBtn.setFocusPainted(false);

        editBtn.setBackground(new Color(52, 152, 219));
        editBtn.setForeground(Color.BLACK);
        editBtn.setFocusPainted(false);

        refreshBtn.setBackground(new Color(155, 89, 182));
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.setFocusPainted(false);

        addBtn.addActionListener(e -> addRoom(app));
        editBtn.addActionListener(e -> editRoom(app));
        refreshBtn.addActionListener(e -> loadRooms(app));

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(refreshBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        loadRooms(app);
    }

    private void loadRooms(HotelManagementSystem app) {
        tableModel.setRowCount(0);
        List<Room> rooms = app.getDataManager().getAllRooms();
        for (Room room : rooms) {
            tableModel.addRow(new Object[]{
                    room.roomNumber, room.type, "$" + room.price,
                    room.isAvailable ? "Available" : "Occupied", room.features
            });
        }
    }

    private void addRoom(HotelManagementSystem app) {
        JTextField roomNumField = new JTextField();
        JComboBox<String> typeField = new JComboBox<>(new String[]{"Basic", "Deluxe", "Suite"});
        JTextField priceField = new JTextField();
        JTextField featuresField = new JTextField();

        Object[] message = {
                "Room Number:", roomNumField,
                "Type:", typeField,
                "Price per Night:", priceField,
                "Features:", featuresField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String roomNum = roomNumField.getText().trim();
                String type = typeField.getSelectedItem().toString();
                double price = Double.parseDouble(priceField.getText().trim());
                String features = featuresField.getText().trim();

                Room room = new Room(roomNum, type, price, features);
                app.getDataManager().addRoom(room);
                loadRooms(app);
                JOptionPane.showMessageDialog(this, "Room added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please check all fields.");
            }
        }
    }

    private void editRoom(HotelManagementSystem app) {
        int row = roomTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room to edit!");
            return;
        }

        String roomNum = tableModel.getValueAt(row, 0).toString();
        Room room = app.getDataManager().getRoomByNumber(roomNum);

        if (room != null) {
            JTextField priceField = new JTextField(String.valueOf(room.price));
            JTextField featuresField = new JTextField(room.features);
            JCheckBox availableCheck = new JCheckBox("Available", room.isAvailable);

            Object[] message = {
                    "Price per Night:", priceField,
                    "Features:", featuresField,
                    "", availableCheck
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Edit Room", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    room.price = Double.parseDouble(priceField.getText().trim());
                    room.features = featuresField.getText().trim();
                    room.isAvailable = availableCheck.isSelected();
                    app.getDataManager().saveRooms();
                    loadRooms(app);
                    JOptionPane.showMessageDialog(this, "Room updated successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input!");
                }
            }
        }
    }
}

// View Bookings Panel
class ViewBookingsPanel extends JPanel {
    private JTable bookingTable;
    private DefaultTableModel tableModel;

    public ViewBookingsPanel(HotelManagementSystem app) {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("All Bookings");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Booking ID", "Guest", "Email", "Phone", "Room", "Type", "Nights", "Total", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        bookingTable = new JTable(tableModel);
        bookingTable.setRowHeight(35);
        bookingTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        bookingTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        bookingTable.getTableHeader().setBackground(new Color(155, 89, 182));
        bookingTable.getTableHeader().setForeground(Color.BLACK);

        add(new JScrollPane(bookingTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton refreshBtn = new JButton("Refresh");
        JButton detailsBtn = new JButton("View Details");

        refreshBtn.setBackground(new Color(155, 89, 182));
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.setFocusPainted(false);

        detailsBtn.setBackground(new Color(52, 152, 219));
        detailsBtn.setForeground(Color.BLACK);
        detailsBtn.setFocusPainted(false);

        refreshBtn.addActionListener(e -> loadBookings(app));
        detailsBtn.addActionListener(e -> viewDetails(app));

        buttonPanel.add(refreshBtn);
        buttonPanel.add(detailsBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        loadBookings(app);
    }

    private void loadBookings(HotelManagementSystem app) {
        tableModel.setRowCount(0);
        List<Booking> bookings = app.getDataManager().getAllBookings();
        for (Booking b : bookings) {
            tableModel.addRow(new Object[]{
                    b.bookingId, b.guestName, b.email, b.phone, b.roomNumber,
                    b.roomType, b.numberOfNights, "$" + b.totalAmount, b.status
            });
        }
    }

    private void viewDetails(HotelManagementSystem app) {
        int row = bookingTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking!");
            return;
        }

        String bookingId = tableModel.getValueAt(row, 0).toString();
        Booking booking = app.getDataManager().getBookingById(bookingId);

        if (booking != null) {
            String details = String.format(
                    "Booking Details\n\n" +
                            "Booking ID: %s\n" +
                            "Guest Name: %s\n" +
                            "Email: %s\n" +
                            "Phone: %s\n" +
                            "Room Number: %s\n" +
                            "Room Type: %s\n" +
                            "Number of Nights: %d\n" +
                            "Total Amount: $%.2f\n" +
                            "Status: %s\n" +
                            "Booking Date: %s",
                    booking.bookingId, booking.guestName, booking.email, booking.phone,
                    booking.roomNumber, booking.roomType, booking.numberOfNights,
                    booking.totalAmount, booking.status, booking.bookingDate
            );
            JOptionPane.showMessageDialog(this, details, "Booking Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

// Reports Panel
class ReportsPanel extends JPanel {
    private JTextArea reportArea;

    public ReportsPanel(HotelManagementSystem app) {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Reports & Statistics");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        reportArea = new JTextArea();
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        reportArea.setEditable(false);
        reportArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton generateBtn = new JButton("Generate Report");
        generateBtn.setBackground(new Color(155, 89, 182));
        generateBtn.setForeground(Color.BLACK);
        generateBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        generateBtn.setFocusPainted(false);

        generateBtn.addActionListener(e -> generateReport(app));

        buttonPanel.add(generateBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        generateReport(app);
    }

    private void generateReport(HotelManagementSystem app) {
        List<Room> rooms = app.getDataManager().getAllRooms();
        List<Booking> bookings = app.getDataManager().getAllBookings();

        int totalRooms = rooms.size();
        int availableRooms = 0;
        int occupiedRooms = 0;
        int basicRooms = 0, deluxeRooms = 0, suiteRooms = 0;

        for (Room room : rooms) {
            if (room.isAvailable) availableRooms++;
            else occupiedRooms++;

            if (room.type.equals("Basic")) basicRooms++;
            else if (room.type.equals("Deluxe")) deluxeRooms++;
            else if (room.type.equals("Suite")) suiteRooms++;
        }

        int totalBookings = bookings.size();
        int activeBookings = 0;
        int completedBookings = 0;
        int cancelledBookings = 0;
        double totalRevenue = 0;

        for (Booking booking : bookings) {
            if (booking.status.equals("Active")) activeBookings++;
            else if (booking.status.equals("Completed")) {
                completedBookings++;
                totalRevenue += booking.totalAmount;
            }
            else if (booking.status.equals("Cancelled")) cancelledBookings++;
        }

        double occupancyRate = totalRooms > 0 ? (occupiedRooms * 100.0 / totalRooms) : 0;

        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════════════\n");
        report.append("                    HOTEL MANAGEMENT REPORT\n");
        report.append("═══════════════════════════════════════════════════════════\n\n");

        report.append("ROOM STATISTICS\n");
        report.append("───────────────────────────────────────────────────────────\n");
        report.append(String.format("Total Rooms:              %d\n", totalRooms));
        report.append(String.format("Available Rooms:          %d\n", availableRooms));
        report.append(String.format("Occupied Rooms:           %d\n", occupiedRooms));
        report.append(String.format("Occupancy Rate:           %.2f%%\n\n", occupancyRate));

        report.append("ROOM DISTRIBUTION\n");
        report.append("───────────────────────────────────────────────────────────\n");
        report.append(String.format("Basic Rooms:              %d\n", basicRooms));
        report.append(String.format("Deluxe Rooms:             %d\n", deluxeRooms));
        report.append(String.format("Suite Rooms:              %d\n\n", suiteRooms));

        report.append("BOOKING STATISTICS\n");
        report.append("───────────────────────────────────────────────────────────\n");
        report.append(String.format("Total Bookings:           %d\n", totalBookings));
        report.append(String.format("Active Bookings:          %d\n", activeBookings));
        report.append(String.format("Completed Bookings:       %d\n", completedBookings));
        report.append(String.format("Cancelled Bookings:       %d\n\n", cancelledBookings));

        report.append("REVENUE STATISTICS\n");
        report.append("───────────────────────────────────────────────────────────\n");
        report.append(String.format("Total Revenue:            $%.2f\n", totalRevenue));
        report.append(String.format("Average per Booking:      $%.2f\n",
                completedBookings > 0 ? totalRevenue / completedBookings : 0));

        report.append("\n═══════════════════════════════════════════════════════════\n");
        report.append("Report Generated: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        report.append("\n═══════════════════════════════════════════════════════════\n");

        reportArea.setText(report.toString());
    }
}

// Data Models
class Room implements Serializable {
    String roomNumber;
    String type;
    double price;
    boolean isAvailable;
    String features;

    public Room(String roomNumber, String type, double price, String features) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isAvailable = true;
        this.features = features;
    }

    public String toFileString() {
        return roomNumber + "|" + type + "|" + price + "|" + isAvailable + "|" + features;
    }

    public static Room fromFileString(String str) {
        String[] parts = str.split("\\|");
        Room room = new Room(parts[0], parts[1], Double.parseDouble(parts[2]), parts[4]);
        room.isAvailable = Boolean.parseBoolean(parts[3]);
        return room;
    }
}

class Booking implements Serializable {
    String bookingId;
    String guestName;
    String email;
    String phone;
    String roomNumber;
    String roomType;
    int numberOfNights;
    double totalAmount;
    String status;
    String bookingDate;

    public Booking(String bookingId, String guestName, String email, String phone,
                   String roomNumber, String roomType, int numberOfNights, double totalAmount) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.email = email;
        this.phone = phone;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.numberOfNights = numberOfNights;
        this.totalAmount = totalAmount;
        this.status = "Active";
        this.bookingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String toFileString() {
        return bookingId + "|" + guestName + "|" + email + "|" + phone + "|" +
                roomNumber + "|" + roomType + "|" + numberOfNights + "|" +
                totalAmount + "|" + status + "|" + bookingDate;
    }

    public static Booking fromFileString(String str) {
        String[] parts = str.split("\\|");
        Booking booking = new Booking(parts[0], parts[1], parts[2], parts[3],
                parts[4], parts[5], Integer.parseInt(parts[6]),
                Double.parseDouble(parts[7]));
        booking.status = parts[8];
        booking.bookingDate = parts[9];
        return booking;
    }
}

// Staff User Model
class StaffUser implements Serializable {
    String username;
    String password;
    String role;

    public StaffUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String toFileString() {
        return username + "|" + password + "|" + role;
    }

    public static StaffUser fromFileString(String str) {
        String[] parts = str.split("\\|");
        return new StaffUser(parts[0], parts[1], parts[2]);
    }
}

// Data Manager
class DataManager {
    private List<Room> rooms;
    private List<Booking> bookings;
    private List<StaffUser> staffUsers;
    private static final String ROOMS_FILE = "rooms.txt";
    private static final String BOOKINGS_FILE = "bookings.txt";
    private static final String STAFF_FILE = "staff.txt";

    public DataManager() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
        staffUsers = new ArrayList<>();
        loadRooms();
        loadBookings();
        loadStaffUsers();

        if (rooms.isEmpty()) {
            initializeSampleRooms();
        }

        if (staffUsers.isEmpty()) {
            initializeDefaultStaff();
        }
    }

    private void initializeSampleRooms() {
        rooms.add(new Room("101", "Basic", 100, "Single Bed, WiFi, TV"));
        rooms.add(new Room("102", "Basic", 100, "Single Bed, WiFi, TV"));
        rooms.add(new Room("103", "Basic", 100, "Single Bed, WiFi, TV"));
        rooms.add(new Room("201", "Deluxe", 200, "Queen Bed, WiFi, TV, Mini Bar"));
        rooms.add(new Room("202", "Deluxe", 200, "Queen Bed, WiFi, TV, Mini Bar"));
        rooms.add(new Room("203", "Deluxe", 200, "Queen Bed, WiFi, TV, Mini Bar"));
        rooms.add(new Room("301", "Suite", 350, "King Bed, WiFi, TV, Mini Bar, Jacuzzi, Ocean View"));
        rooms.add(new Room("302", "Suite", 350, "King Bed, WiFi, TV, Mini Bar, Jacuzzi, Ocean View"));
        saveRooms();
    }

    private void initializeDefaultStaff() {
        staffUsers.add(new StaffUser("admin", "admin123", "Administrator"));
        staffUsers.add(new StaffUser("manager", "manager123", "Manager"));
        saveStaffUsers();
    }

    public void loadRooms() {
        rooms.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ROOMS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                rooms.add(Room.fromFileString(line));
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }
    }

    public void saveRooms() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ROOMS_FILE))) {
            for (Room room : rooms) {
                pw.println(room.toFileString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBookings() {
        bookings.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                bookings.add(Booking.fromFileString(line));
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }
    }

    public void saveBookings() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKINGS_FILE))) {
            for (Booking booking : bookings) {
                pw.println(booking.toFileString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadStaffUsers() {
        staffUsers.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(STAFF_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                staffUsers.add(StaffUser.fromFileString(line));
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }
    }

    public void saveStaffUsers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(STAFF_FILE))) {
            for (StaffUser user : staffUsers) {
                pw.println(user.toFileString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateStaff(String username, String password) {
        for (StaffUser user : staffUsers) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return true;
            }
        }
        return false;
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }

    public Room getRoomByNumber(String roomNumber) {
        for (Room room : rooms) {
            if (room.roomNumber.equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    public Booking getBookingById(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.bookingId.equals(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    public void addRoom(Room room) {
        rooms.add(room);
        saveRooms();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        saveBookings();
    }
}