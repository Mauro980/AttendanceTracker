package Screens;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LoginScreen extends JFrame {

    private final Color PRIMARY_COLOR = new Color(0xC8, 0x15, 0x1D);
    private final Color SECONDARY_TEXT = new Color(0x96, 0x8D, 0x8D);
    private final Color MAIN_TEXT = Color.WHITE;

    public LoginScreen() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("City University Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.setBackground(PRIMARY_COLOR);

        // Title panel
        JPanel titlePanel = createTitlePanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PRIMARY_COLOR);

        JLabel titleLabel = new JLabel("City University");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(MAIN_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Welcome back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        welcomeLabel.setForeground(MAIN_TEXT);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(welcomeLabel);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(PRIMARY_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField fullNameField = createStyledTextField();
        JTextField emailField = createStyledTextField();
        JPasswordField passwordField = createStyledPasswordField();
        JPasswordField confirmPasswordField = createStyledPasswordField();

        addFormRow(panel, gbc, "Email:", emailField, 1);
        addFormRow(panel, gbc, "Password:", passwordField, 2);

        return panel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        styleComponent(field);
        field.setFont(new Font("Arial", Font.BOLD, 14)); // Bold text in form fields
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        styleComponent(field);
        field.setFont(new Font("Arial", Font.BOLD, 14)); // Bold text in password fields
        return field;
    }

    private void styleComponent(JComponent component) {
        component.setBorder(new RoundedBorder(15, Color.WHITE));
        component.setBackground(Color.WHITE);
        component.setOpaque(true);
        component.setPreferredSize(new Dimension(250, 35));
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int yPos) {
        JLabel label = new JLabel(labelText);
        label.setForeground(MAIN_TEXT);
        label.setFont(new Font("Roboto", Font.BOLD, 14)); // Bold text for labels
        gbc.gridx = 0;
        gbc.gridy = yPos;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PRIMARY_COLOR);

        JButton signUpButton = new JButton("Log In");
        styleButton(signUpButton);

        // Center the button using wrapper panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(PRIMARY_COLOR);
        centerPanel.add(signUpButton);

        // Centered secondary text
        JPanel secondaryTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        secondaryTextPanel.setBackground(PRIMARY_COLOR);
        JButton existingAccountLink = new JButton("New Student? Sign Up");
        existingAccountLink.setForeground(SECONDARY_TEXT);
        existingAccountLink.setBorderPainted(false);
        existingAccountLink.setContentAreaFilled(false);
        existingAccountLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        existingAccountLink.setFont(new Font("Arial", Font.PLAIN, 12));
        secondaryTextPanel.add(existingAccountLink);

        panel.add(centerPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(secondaryTextPanel);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(MAIN_TEXT);
        button.setBorder(new RoundedBorder(25, Color.BLACK));
        button.setPreferredSize(new Dimension(250, 40));
        button.setMaximumSize(new Dimension(250, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
    }

    // Custom rounded border class
    class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 1, this.radius + 1);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = this.radius + 1;
            return insets;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}