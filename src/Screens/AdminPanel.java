package Screens;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class AdminPanel extends JFrame {

    private final Color PRIMARY_COLOR = new Color(0xC8, 0x15, 0x1D); // Red
    private final Color SECONDARY_TEXT = new Color(0x96, 0x8D, 0x8D); // Gray
    private final Color MAIN_TEXT = Color.WHITE;
    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);
    private final Font LABEL_FONT = new Font("Arial", Font.BOLD, 12);
    private final Font VALUE_FONT = new Font("Arial", Font.PLAIN, 12);

    public AdminPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Admin Panel - Add New User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);

        // Main container
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
        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        togglePanel.setBackground(PRIMARY_COLOR);

        JRadioButton teacherBtn = createToggleButton("Teacher", true);
        JRadioButton studentBtn = createToggleButton("Student", false);

        ButtonGroup group = new ButtonGroup();
        group.add(teacherBtn);
        group.add(studentBtn);

        togglePanel.add(teacherBtn);
        togglePanel.add(studentBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(togglePanel, BorderLayout.CENTER);

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

        // Form sections
        panel.add(createSectionPanel("Name", "Value"));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createSectionPanel("Surname", "Value"));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createSectionPanel("Email", "Value"));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createSectionPanel("Reference", "Value"));

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

        JTextField field = new JTextField(value);
        field.setFont(VALUE_FONT);
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(field, BorderLayout.CENTER);

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

        panel.add(submitBtn);
        return panel;
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
        SwingUtilities.invokeLater(() -> new AdminPanel());
    }
}