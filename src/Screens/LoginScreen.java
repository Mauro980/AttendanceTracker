package Screens;
import Classes.Brigde;
import Classes.User;
import DatabaseConnection.DatabaseConnect;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.util.ArrayList;

public class LoginScreen extends JFrame {

    private final Color PRIMARY_COLOR = new Color(0xC8, 0x15, 0x1D);
    private final Color SECONDARY_TEXT = new Color(0x96, 0x8D, 0x8D);
    private final Color MAIN_TEXT = Color.WHITE;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton signUpButton;
    private JButton existingAccountLink;
    private ArrayList<User> userList;

    public LoginScreen() {

        initializeUI();

    }

    private void initializeUI() {

        setTitle("City University Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.setBackground(PRIMARY_COLOR);

        JPanel titlePanel = createTitlePanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        signUpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               validateInput();
               loadData();
            }
        });
    }

    private void loadData() {
        userList = new ArrayList();
        Connection n =  DatabaseConnect.DbConnect();
        User user1 = new User(101, "Alice Johnson","alice", "Admin", "admin123");
        User user2 = new User(102, "Bob Smith", "ha","Lecturer", "lecturer456");
        User user3 = new User(103, "Charlie Brown", "ja","Student", "student789");
        User user4 = new User(104, "David White", "ka","Registrar", "registrar321");
        User user5 = new User(105, "Emma Davis", "ua","Accountant", "accountant654");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
    }

    private void validateInput() {
        String email = emailField.getText();
        String pass = new String(passwordField.getPassword());
        if(email.isEmpty() || pass.isEmpty()){
            JOptionPane.showMessageDialog(this,"Check your input and try again!");
        }else{
            loginUser(email,pass);
        }
    }

    private void loginUser(String email, String pass) {
        boolean loggedIn = false;
        System.out.println(email);
        System.out.println(pass);
        for(User user:userList){

            if(user.getEmail().equals(email) && user.getPassword().equals((pass))){
                loggedIn = true;
                Brigde.loggedUser = user;
            }
        }
        if(loggedIn){
            MainScreen mainFrame = new MainScreen();
            mainFrame.setVisible(true);
            this.setVisible(false);
        }else {
            JOptionPane.showMessageDialog(this,"Wrong email or Password!Try Again");
            emailField.setText("");
            passwordField.setText("");
        }
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

        emailField = createStyledTextField();
        passwordField = createStyledPasswordField();

        addFormRow(panel, gbc, "Email:", emailField, 1);
        addFormRow(panel, gbc, "Password:", passwordField, 2);

        return panel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        styleComponent(field);
        field.setFont(new Font("Arial", Font.BOLD, 14));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        styleComponent(field);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        return field;
    }

    private void styleComponent(JComponent component) {
        component.setBorder(new RoundedBorder(15, Color.WHITE));
        component.setBackground(Color.WHITE);
        component.setOpaque(true);
        component.setPreferredSize(new Dimension(250, 20));
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int yPos) {
        JLabel label = new JLabel(labelText);
        label.setForeground(MAIN_TEXT);
        label.setFont(new Font("Roboto", Font.BOLD, 14));
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

        signUpButton = new JButton("Log In");
        existingAccountLink = new JButton("New Student? Sign Up");
        existingAccountLink.setForeground(SECONDARY_TEXT);
        existingAccountLink.setBorderPainted(false);
        existingAccountLink.setContentAreaFilled(false);
        existingAccountLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        existingAccountLink.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(PRIMARY_COLOR);
        centerPanel.add(signUpButton);

        JPanel secondaryTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        secondaryTextPanel.setBackground(PRIMARY_COLOR);
        secondaryTextPanel.add(existingAccountLink);

        panel.add(centerPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(secondaryTextPanel);

        return panel;
    }

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
}
