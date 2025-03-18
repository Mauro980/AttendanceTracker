package Screens;

import Classes.Brigde;
import Classes.Student;
import DatabaseConnection.StudentController;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class RegisterStudent extends JFrame {

    private final Color PRIMARY_COLOR = new Color(0xC8, 0x15, 0x1D); // Red
    private final Color SECONDARY_TEXT = new Color(0x96, 0x8D, 0x8D); // Gray
    private final Color MAIN_TEXT = Color.WHITE;
    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);
    private final Font LABEL_FONT = new Font("Arial", Font.BOLD, 12);
    private final Font VALUE_FONT = new Font("Arial", Font.PLAIN, 12);

    private  JTextField txtStudentID;
    private  JTextField txtName;
    private  JTextField txtEmail;
    private  JTextField txtContact;
    private  JRadioButton radioMale;
    private JRadioButton radioFemale;

    public RegisterStudent() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Admin Panel - Add New User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);

        // Classes.Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(PRIMARY_COLOR);

        // Header section
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        // Form section
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);

        // Submit section
        mainPanel.add(createSubmitPanel(), BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_COLOR);

        // Title
        JLabel title = new JLabel("Add New User");
        title.setFont(TITLE_FONT);
        title.setForeground(MAIN_TEXT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Toggle buttons


        panel.add(title, BorderLayout.NORTH);
        //;

        return panel;
    }

//    Create Toggle button to switch from adding student to teacher.
    private JRadioButton createToggleButton(String text, boolean selected) {
        JRadioButton btn = new JRadioButton(text, selected);
        btn.setFont(LABEL_FONT);
        btn.setForeground(selected ? MAIN_TEXT : MAIN_TEXT);
        btn.setBackground(selected ? PRIMARY_COLOR : PRIMARY_COLOR);
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(selected ? PRIMARY_COLOR : SECONDARY_TEXT),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        btn.setOpaque(false);

        btn.addItemListener(e -> {
            if (btn.isSelected()) {
                btn.setBackground(Color.BLACK);
                btn.setForeground(MAIN_TEXT);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(PRIMARY_COLOR),
                        BorderFactory.createEmptyBorder(5, 15, 5, 15)
                ));
            } else {
                btn.setBackground(PRIMARY_COLOR);
                btn.setForeground(MAIN_TEXT);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(SECONDARY_TEXT),
                        BorderFactory.createEmptyBorder(5, 15, 5, 15)
                ));
            }
        });

        return btn;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        radioMale = createToggleButton("Male", true);
        radioFemale = createToggleButton("Female", false);
        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        togglePanel.setBackground(PRIMARY_COLOR);



        ButtonGroup group = new ButtonGroup();
        group.add(radioFemale);
        group.add(radioMale);

        togglePanel.add(radioFemale);
        togglePanel.add(radioMale);

        panel.add(togglePanel, BorderLayout.AFTER_LAST_LINE);
        // Form sections
        panel.add(createSectionPanel("Student ID", ""));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createSectionPanel("Name", ""));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createSectionPanel("Email", ""));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createSectionPanel("Contact", ""));

        return panel;
    }

    private JPanel createSectionPanel(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());

        // Custom border with red title
        TitledBorder border = new TitledBorder(
                new LineBorder(SECONDARY_TEXT),
                label,
                TitledBorder.LEFT,
                TitledBorder.DEFAULT_POSITION,
                LABEL_FONT,
                PRIMARY_COLOR // Title color
        );
        panel.setBorder(border);
        panel.setBackground(Color.WHITE);

        if(label=="Student ID"){
            txtStudentID = new JTextField(value);
            txtStudentID.setFont(VALUE_FONT);
            txtStudentID.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            panel.add(txtStudentID, BorderLayout.CENTER);
        }else if(label=="Name"){
            txtName = new JTextField(value);
            txtName.setFont(VALUE_FONT);
            txtName.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            panel.add(txtName, BorderLayout.CENTER);
        }else if(label=="Email"){
            txtEmail = new JTextField(value);
            txtEmail.setFont(VALUE_FONT);
            txtEmail.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            panel.add(txtEmail, BorderLayout.CENTER);
        }
        else if(label=="Contact"){
            txtContact = new JTextField(value);
            txtContact.setFont(VALUE_FONT);
            txtContact.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            panel.add(txtContact, BorderLayout.CENTER);
        }

        return panel;
    }

    private JPanel createSubmitPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(PRIMARY_COLOR);

        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(LABEL_FONT);
        submitBtn.setBackground(Color.BLACK);
        submitBtn.setForeground(MAIN_TEXT);
        submitBtn.setBorder(new RoundedBorder(25, Color.BLACK));
        submitBtn.setPreferredSize(new Dimension(250, 40));
        submitBtn.setFocusPainted(false);
        submitBtn.setOpaque(true);
        submitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Register();
            }
        });
        panel.add(submitBtn);
        return panel;
    }

    private void Register() {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String id = txtStudentID.getText();
        String gender;
        if(radioMale.isSelected()){
            gender = Brigde.gender[0];
        }else{
            gender = Brigde.gender[1];
        }

        if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || id.isEmpty()) {
           JOptionPane.showMessageDialog(this,"Please fill in all fields.");
        } else {
            Student student = new Student(id,name,gender,email,contact);
            StudentController.addStudent(student);
            JOptionPane.showMessageDialog(this,"Student added Successfully!");
            txtName.setText("");
            txtStudentID.setText("");
            txtContact.setText("");
            txtEmail.setText("");
        }
    }

    // Custom rounded border class
    class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterStudent());
    }
}