package Screens;

import Classes.Main;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    private final Color PRIMARY_COLOR = new Color(0xC8, 0x15, 0x1D); // Red background
    private final Color TEXT_COLOR = Color.WHITE;

    private String userName;
    private String userRole;

    public MainScreen() {
        this.userName = "userName";
        this.userRole = "userRole";
        initializeUI();
    }

    private void initializeUI() {
        setTitle("User Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(PRIMARY_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel userPanel = createUserPanel();
        topPanel.add(userPanel, BorderLayout.EAST);

        JPanel buttonPanel = createButtonPanel();
        topPanel.add(buttonPanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PRIMARY_COLOR);

        JLabel nameLabel = new JLabel("Name: " + userName);
        nameLabel.setForeground(TEXT_COLOR);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel roleLabel = new JLabel("Role: " + userRole);
        roleLabel.setForeground(TEXT_COLOR);
        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(nameLabel);
        panel.add(roleLabel);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(PRIMARY_COLOR);

        JButton markAttendanceButton = new JButton("Mark Attendance");
        JButton trackAttendanceButton = new JButton("Track Attendance");

        styleButton(markAttendanceButton);
        styleButton(trackAttendanceButton);

        panel.add(markAttendanceButton);
        panel.add(trackAttendanceButton);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(PRIMARY_COLOR);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        //button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainScreen());
    }
}
