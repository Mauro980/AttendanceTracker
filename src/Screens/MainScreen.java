package Screens;

import Classes.Brigde;

// Importing required screens that will open on button clicks
import Screens.MarkAttendanceScreen;
import Screens.StudentDashboard;
import Screens.RegisterStudent;
import Screens.TeacherDashboard;
import Screens.EnrollmentScreen;
import Screens.EnrollTeacherScreen;

import javax.swing.*;
import java.awt.*;

/**
 * MainScreen is the dashboard UI shown to the user (likely a teacher),
 * displaying user details and buttons to navigate to different sections.
 */
public class MainScreen extends JFrame {

    // Custom colors for the UI
    private final Color PRIMARY_COLOR = new Color(0xC8151D); // Red
    private final Color TEXT_COLOR = Color.BLACK;

    // Declare UI components (buttons)
    private JButton markAttendanceButton;
    private JButton trackAttendanceButton;
    private JButton registerStudentButton;
    private JButton teacherDashboardButton;
    private JButton searchStudentButton;
    private JButton assignTeacherButton;

    // Store logged-in user information
    private String userName;
    private String userRole;

    // Constructor
    public MainScreen() {
        // Retrieve user details from Brigde.loggedTeacher
        if (Brigde.loggedTeacher != null) {
            this.userName = Brigde.loggedTeacher.getName();
            this.userRole = Brigde.loggedTeacher.getRole();
        } else {
            this.userName = "Unknown";
            this.userRole = "Unknown";
        }

        // Initialize the UI
        initializeUI();
    }

    // Setup the main screen UI layout and event listeners
    private void initializeUI() {
        setTitle("User Dashboard");
        setSize(350, 600); // Window size
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top section: user info
        mainPanel.add(createUserPanel(), BorderLayout.NORTH);

        // Center section: navigation buttons
        mainPanel.add(createButtonPanel(), BorderLayout.CENTER);

        // Add everything to the JFrame
        add(mainPanel);
        setVisible(true); // Show the window

        // Event listeners for buttons
        markAttendanceButton.addActionListener(e -> new MarkAttendanceScreen());
        trackAttendanceButton.addActionListener(e -> new StudentDashboard());
        registerStudentButton.addActionListener(e -> new RegisterStudent());
        teacherDashboardButton.addActionListener(e -> new TeacherDashboard());
        searchStudentButton.addActionListener(e -> new EnrollmentScreen());
        assignTeacherButton.addActionListener(e -> new EnrollTeacherScreen());
    }

    // Creates the top user panel that shows the teacher's name and role
    private JPanel createUserPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PRIMARY_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        // Display user's name
        JLabel nameLabel = new JLabel("Name: " + userName);
        nameLabel.setForeground(TEXT_COLOR);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Display user's role
        JLabel roleLabel = new JLabel("Role: " + userRole);
        roleLabel.setForeground(TEXT_COLOR);
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add both labels to the panel
        panel.add(nameLabel);
        panel.add(roleLabel);

        return panel;
    }

    // Creates the central button panel with all navigation buttons
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        // Layout constraints for placing buttons vertically
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Margin between buttons

        // Initialize buttons with text
        markAttendanceButton = new JButton("Mark Attendance");
        trackAttendanceButton = new JButton("Track Attendance");
        registerStudentButton = new JButton("Register Student");
        teacherDashboardButton = new JButton("Teacher Dashboard");
        searchStudentButton = new JButton("Search Student");
        assignTeacherButton = new JButton("Assign Teacher");

        // Create an array of buttons to style and add to panel in order
        JButton[] buttons = {
                markAttendanceButton,
                trackAttendanceButton,
                registerStudentButton,
                teacherDashboardButton,
                searchStudentButton,
                assignTeacherButton
        };

        // Loop to apply styles and place them on the panel
        for (int i = 0; i < buttons.length; i++) {
            styleButton(buttons[i]);
            gbc.gridy = i; // Row index
            panel.add(buttons[i], gbc);
        }

        return panel;
    }

    // Apply styling to each button to match theme
    private void styleButton(JButton button) {
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40)); // Consistent size
    }

    // Entry point of the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainScreen::new); // Run UI in EDT
    }
}