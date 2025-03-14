package Screens;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    public StudentDashboard() {
        // Basic frame setup
        setTitle("Student Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===================== TOP PANEL (RED) =====================
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0xBF211E)); // Red background
        topPanel.setPreferredSize(new Dimension(900, 60));

        // Left side: "STUDENT" label
        JLabel titleLabel = new JLabel("STUDENT");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Right side: user info panel
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        userInfoPanel.setOpaque(false);
        JLabel userNameLabel = new JLabel("Hi, Nuru R Matora");
        userNameLabel.setForeground(Color.WHITE);
        userNameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        userInfoPanel.add(userNameLabel);
        topPanel.add(userInfoPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ===================== LEFT SIDEBAR PANEL (BLACK) =====================
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setPreferredSize(new Dimension(180, 0));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Sidebar labels (all text in bold)
        leftPanel.add(createSidebarLabel("Student Portal"));
        leftPanel.add(createSidebarLabel("LMS - Class Online"));
        leftPanel.add(Box.createVerticalStrut(10));

        // For Semester 1, create a clickable panel that expands to list the subjects
        String[] semester1Subjects = {
                "Software Engineering",
                "Operating Systems",
                "Data Communication",
                "Introduction to Multimedia",
                "English for Employment"
        };
        leftPanel.add(createSemesterPanel("SEMESTER 1", semester1Subjects));
        leftPanel.add(Box.createVerticalStrut(10));

        // For Semesters 2 to 8, just display bold labels
        for (int i = 2; i <= 8; i++) {
            leftPanel.add(createSidebarLabel("SEMESTER " + i));
        }
        add(leftPanel, BorderLayout.WEST);

        // ===================== CENTER PANEL (TABLE + GPA/CGPA) =====================
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        // Table data
        String[] columnNames = { "ID", "SUBJECT", "TOTAL", "ATTENDED", "ABSENT", "STATUS", "GRADE" };
        Object[][] data = {
                {"BIT1073", "Software Engineering", "14", "9", "5", "Normal", "A"},
                {"BIT1103", "Operating Systems",     "14", "9", "5", "Normal", "B"},
                {"BSE2013", "Data Communication",    "14", "9", "5", "Normal", "C"},
                {"BIT2074", "Introduction to Multimedia", "14", "9", "5", "Normal", "A"},
                {"BMUC2122","English for Employment","14", "9", "5", "Normal", "B"},
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setGridColor(Color.LIGHT_GRAY);

        // --------------------- CUSTOM HEADER RENDERER ---------------------
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setFont(new Font("SansSerif", Font.BOLD, 14));
                label.setBackground(new Color(0xBF211E));
                label.setForeground(Color.WHITE);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setOpaque(true);
                return label;
            }
        };

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setDefaultRenderer(headerRenderer);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Center-align table cell contents
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // ===================== BOTTOM PANEL (GPA/CGPA TABLE) =====================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        bottomPanel.setBackground(Color.WHITE);

        // GPA/CGPA panel in table format (2 rows x 2 columns)
        JPanel gpaPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        gpaPanel.setBackground(Color.WHITE);

        // GPA Label and Value
        JLabel gpaLabel = new JLabel("GPA");
        gpaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gpaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gpaLabel.setOpaque(true);
        gpaLabel.setBackground(new Color(0xBF211E));
        gpaLabel.setForeground(Color.WHITE);
        gpaLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel gpaValue = new JLabel("3.40");
        gpaValue.setHorizontalAlignment(SwingConstants.CENTER);
        gpaValue.setFont(new Font("SansSerif", Font.BOLD, 14));
        gpaValue.setOpaque(true);
        gpaValue.setBackground(new Color(0x4CAF50));
        gpaValue.setForeground(Color.WHITE);
        gpaValue.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // CGPA Label and Value
        JLabel cgpaLabel = new JLabel("CGPA");
        cgpaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cgpaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        cgpaLabel.setOpaque(true);
        cgpaLabel.setBackground(new Color(0xBF211E));
        cgpaLabel.setForeground(Color.WHITE);
        cgpaLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel cgpaValue = new JLabel("3.40");
        cgpaValue.setHorizontalAlignment(SwingConstants.CENTER);
        cgpaValue.setFont(new Font("SansSerif", Font.BOLD, 14));
        cgpaValue.setOpaque(true);
        cgpaValue.setBackground(new Color(0x4CAF50));
        cgpaValue.setForeground(Color.WHITE);
        cgpaValue.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        gpaPanel.add(gpaLabel);
        gpaPanel.add(gpaValue);
        gpaPanel.add(cgpaLabel);
        gpaPanel.add(cgpaValue);

        bottomPanel.add(gpaPanel);
        centerPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // Show frame
        setVisible(true);
    }

    /**
     * Helper method to create a bold JLabel for the sidebar.
     */
    private JLabel createSidebarLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    /**
     * Creates a panel for a semester that toggles a list of subject names as plain text.
     */
    private JPanel createSemesterPanel(String semesterTitle, String[] subjects) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Semester clickable label
        JLabel semesterLabel = new JLabel(semesterTitle);
        semesterLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        semesterLabel.setForeground(Color.WHITE);
        semesterLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 0));
        semesterLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel that will hold the subject labels; initially hidden
        JPanel subjectListPanel = new JPanel();
        subjectListPanel.setLayout(new BoxLayout(subjectListPanel, BoxLayout.Y_AXIS));
        subjectListPanel.setBackground(Color.BLACK);
        subjectListPanel.setVisible(false);

        // Add each subject as a bold label with an indent
        for (String subject : subjects) {
            JLabel subjectLabel = new JLabel(subject);
            subjectLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            subjectLabel.setForeground(Color.WHITE);
            subjectLabel.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 0));
            subjectLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            subjectListPanel.add(subjectLabel);
        }

        // Toggle the subject list when the semester label is clicked
        semesterLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                subjectListPanel.setVisible(!subjectListPanel.isVisible());
                panel.revalidate();
                panel.repaint();
            }
        });

        panel.add(semesterLabel);
        panel.add(subjectListPanel);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentDashboard::new);
    }
}
