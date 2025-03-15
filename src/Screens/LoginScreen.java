package Screens;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LoginScreen extends JFrame {

    // Updated PRIMARY_COLOR to 0xBF211E
    private final Color PRIMARY_COLOR = new Color(0xC8151D);
    private final Color SECONDARY_TEXT = new Color(0x96, 0x8D, 0x8D);
    private final Color MAIN_TEXT = Color.WHITE;

    public LoginScreen() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("City University Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Main panel with the new background color
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

        JTextField emailField = createStyledTextField();
        JPasswordField passwordField = createStyledPasswordField();

        // If you have more fields (e.g., fullName, confirmPassword), add them similarly
        addFormRow(panel, gbc, "Email:", emailField, 1);
        addFormRow(panel, gbc, "Password:", passwordField, 2);

        return panel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        styleComponent(field);
        field.setFont(new Font("Arial", Font.BOLD, 14));
        field.setForeground(Color.BLACK);
        // Optional: field.setCaretColor(Color.BLACK);
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        styleComponent(field);
        field.setFont(new Font("Arial", Font.BOLD, 14));
        field.setForeground(Color.BLACK);
        // Optional: field.setCaretColor(Color.BLACK);
        return field;
    }

    /**
     * Remove or comment out setPreferredSize(...) if the text is getting cut off.
     * Also changed the border color to GRAY so it’s visible against the white background.
     */
    private void styleComponent(JComponent component) {
        component.setBorder(new RoundedBorder(15, Color.GRAY));
        component.setBackground(Color.WHITE);
        component.setOpaque(true);
        // component.setPreferredSize(new Dimension(250, 35)); // Remove if text is clipped
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int yPos) {
        JLabel label = new JLabel(labelText);
        label.setForeground(MAIN_TEXT);
        label.setFont(new Font("Roboto", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.weightx = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Let the text field expand horizontally
        panel.add(field, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PRIMARY_COLOR);

        JButton signUpButton = new JButton("Log In");
        styleButton(signUpButton);

        // Center the button using a wrapper panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(PRIMARY_COLOR);
        centerPanel.add(signUpButton);

        // Centered secondary text
        JPanel secondaryTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        secondaryTextPanel.setBackground(PRIMARY_COLOR);

        // Make "Sign Up" white, while "New Student?" remains SECONDARY_TEXT
        JButton existingAccountLink = new JButton();
        existingAccountLink.setText(
                "<html>"
                        + "<font color='#968D8D'>New Student? </font>"
                        + "<font color='white'>Sign Up</font>"
                        + "</html>"
        );
        existingAccountLink.setBorderPainted(false);
        existingAccountLink.setContentAreaFilled(false);
        existingAccountLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        existingAccountLink.setFont(new Font("Arial", Font.BOLD, 12));
//        Link to the Login page
        existingAccountLink.addActionListener(e -> {
            new SignUpScreen();
            dispose();
        });
        secondaryTextPanel.add(existingAccountLink);
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
        SwingUtilities.invokeLater(LoginScreen::new);
    }
}
