package Screens;

import Classes.Brigde;
import Classes.User;
import DatabaseConnection.UserController;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class SignUpScreen extends JFrame {

    // Updated primary color to 0xBF211E
    private final Color PRIMARY_COLOR = new Color(0xC8151D);
    private final Color SECONDARY_TEXT = new Color(0x96, 0x8D, 0x8D);
    private final Color MAIN_TEXT = Color.WHITE;
    private JTextField txtName;
    private JTextField txtEmail;
    private  JPasswordField txtPassword;
    private  JPasswordField txtPasswordConfirm;

    public SignUpScreen() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("City University Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Main panel with PRIMARY_COLOR background
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

        JLabel welcomeLabel = new JLabel("Welcome To CityU");
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

        txtName = createStyledTextField();
        txtEmail = createStyledTextField();
        txtPassword = createStyledPasswordField();
        txtPasswordConfirm = createStyledPasswordField();

        addFormRow(panel, gbc, "Full Name:", txtName, 0);
        addFormRow(panel, gbc, "Email:", txtEmail, 1);
        addFormRow(panel, gbc, "Password:", txtPassword, 2);
        addFormRow(panel, gbc, "Confirm Password:", txtPasswordConfirm, 3);

        return panel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        styleComponent(field);
        field.setFont(new Font("Arial", Font.BOLD, 14)); // Bold text in form fields
        field.setForeground(Color.BLACK);
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        styleComponent(field);
        field.setFont(new Font("Arial", Font.BOLD, 14)); // Bold text in password fields
        field.setForeground(Color.BLACK);
        return field;
    }

    // Set a rounded border and ensure the component's background remains white
    private void styleComponent(JComponent component) {
        // Using a white border here. Change if needed to improve visibility.
        component.setBorder(new RoundedBorder(15, Color.WHITE));
        component.setBackground(Color.WHITE);
        component.setOpaque(true);
        component.setPreferredSize(new Dimension(250, 45));
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int yPos) {
        JLabel label = new JLabel(labelText);
        label.setForeground(MAIN_TEXT);
        label.setFont(new Font("Roboto", Font.BOLD, 14)); // Bold text for labels
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

        JButton signUpButton = new JButton("Sign Up");
        styleButton(signUpButton);

        signUpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                signUp();
            }
        });
        // Center the button using a wrapper panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(PRIMARY_COLOR);
        centerPanel.add(signUpButton);

        // Secondary text with a clickable "Log In" link
        JPanel secondaryTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        secondaryTextPanel.setBackground(PRIMARY_COLOR);
        JButton existingAccountLink = new JButton();
        // Set HTML so that "Already Have an Account?" uses the secondary color and "Log In" is white.
        existingAccountLink.setText("<html>"
                + "<font color='#968D8D'><b>Already Have an Account? </b></font>"
                + "<font color='white'><b>Log In</b></font>"
                + "</html>");
        existingAccountLink.setBorderPainted(false);
        existingAccountLink.setContentAreaFilled(false);
        existingAccountLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        existingAccountLink.setFont(new Font("Arial", Font.BOLD, 12));
        // When clicked, open the LoginScreen and close the current screen.
        existingAccountLink.addActionListener(e -> {
            new LoginScreen();
            dispose();
        });
        secondaryTextPanel.add(existingAccountLink);

        panel.add(centerPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(secondaryTextPanel);

        return panel;
    }

    private void signUp() {
        String fullName = txtName.getText();
        String email = txtEmail.getText();
        String pass = new String(txtPassword.getPassword());
        String pass2 = new String(txtPasswordConfirm.getPassword());

        if(fullName.isEmpty() || email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Check your entry and try again");
        }else{
            if(!pass.equals(pass2)){
                JOptionPane.showMessageDialog(this,"Password does not Match. Try again");
            }else{
                User user = new User(fullName,email, Brigde.roles[1],pass );
                UserController.createUser(user);
                JOptionPane.showMessageDialog(this,"User Added Sucessfuly");
                txtName.setText("");
                txtPasswordConfirm.setText("");
                txtEmail.setText("");
                txtPassword.setText("");

            }
        }
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
        SwingUtilities.invokeLater(() -> new SignUpScreen());
    }
}
