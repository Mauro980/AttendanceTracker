package Screens;

import Classes.Brigde;
import DatabaseConnection.DatabaseConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainScreen extends JFrame {
    private final Color PRIMARY_COLOR = new Color(0xC8151D); // Red background
    private final Color TEXT_COLOR = Color.BLACK;
    JButton markAttendanceButton;
    JButton trackAttendanceButton;

    private String userName;
    private String userRole;

    public MainScreen() {
        this.userName = Brigde.loggedUser.getName();
        this.userRole = Brigde.loggedUser.getRole();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("User Dashboard");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel userPanel = createUserPanel();
        mainPanel.add(userPanel, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        markAttendanceButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PRIMARY_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Name: " + userName);
        nameLabel.setForeground(TEXT_COLOR);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel roleLabel = new JLabel("Role: " + userRole);
        roleLabel.setForeground(TEXT_COLOR);
        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(nameLabel);
        panel.add(roleLabel);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        markAttendanceButton = new JButton("Mark Attendance");
        trackAttendanceButton = new JButton("Track Attendance");

        styleButton(markAttendanceButton);
        styleButton(trackAttendanceButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(markAttendanceButton, gbc);

        gbc.gridy = 1;
        panel.add(trackAttendanceButton, gbc);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
       // button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainScreen());

    }
}
