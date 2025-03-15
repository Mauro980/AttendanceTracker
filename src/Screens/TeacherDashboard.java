package Screens;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class TeacherDashboard extends JFrame {

    public TeacherDashboard() {
        // Basic frame setup
        setTitle("Teacher Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===================== TOP PANEL (RED) =====================
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0xC8151D)); // Red color
        topPanel.setPreferredSize(new Dimension(900, 60));

        // Left side: "TEACHER" label
        JLabel titleLabel = new JLabel("TEACHER");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Right side: user info panel
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        userInfoPanel.setOpaque(false); // Transparent so it inherits topPanel's background
        JLabel userNameLabel = new JLabel("Hi, Dr Ephraim P Chinyangwa");
        userNameLabel.setForeground(Color.WHITE);
        userNameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        userInfoPanel.add(userNameLabel);
        topPanel.add(userInfoPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ===================== LEFT SIDEBAR (BLACK) =====================
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setPreferredSize(new Dimension(180, 0));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Sidebar labels
        leftPanel.add(createSidebarLabel("Teacher Portal"));
        leftPanel.add(createSidebarLabel("LMS - Class Online"));
        leftPanel.add(Box.createVerticalStrut(10)); // Spacing

        // Add semester entries as needed
        for (int i = 1; i <= 5; i++) {
            leftPanel.add(createSidebarLabel("Semester 2020/21"));
        }

        add(leftPanel, BorderLayout.WEST);

        // ===================== CENTER PANEL (WHITE) =====================
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        // Main title above the red subtitle
        JLabel mainTitleLabel = new JLabel("Student Attendance and Grades");
        mainTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        mainTitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(mainTitleLabel, BorderLayout.NORTH);

        // Subtitle with red background
        JLabel subTitleLabel = new JLabel("FUNDAMENTAL OF COMPUTATIONAL THINKING : PYTHON", SwingConstants.CENTER);
        subTitleLabel.setOpaque(true);
        subTitleLabel.setBackground(new Color(0xC8151D)); // Red
        subTitleLabel.setForeground(Color.WHITE);
        subTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        subTitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel subTitlePanel = new JPanel(new BorderLayout());
        subTitlePanel.setBackground(Color.WHITE);
        subTitlePanel.add(subTitleLabel, BorderLayout.NORTH);

        /* ===================== TABLE SETUP ===================== */
        String[] columnNames = {
                "No.",
                "STUDENT NAME",
                "STUDENT ID",
                "PROGRAM",
                "TOTAL",
                "ATTENDED",
                "ABSENT",
                "GRADE"
        };

        Object[][] data = {
                {"1", "Ben Carter", "2025030001", "BIT", "21", "21", "0", "A"},
                {"2", "Dan Harris", "2025040002", "BIT", "21", "16", "5", "B"},
                {"3", "Eli Johnson", "2025020003", "BIT", "21", "19", "2", "A"},
                {"4", "Gus Martin", "2022030045", "BIT", "21", "11", "10", "C"},
                {"5", "Ian Morris", "2022040060", "BCSSE", "21", "19", "2", "A"},
                {"6", "Leo Parker", "2022040055", "BCSSE", "21", "17", "4", "B"},
                {"7", "Max Roberts", "2022040058", "BCSSE", "21", "20", "1", "A"},
                {"8", "Susan Turner", "2022040059", "BCSSE", "21", "15", "6", "B"},
                {"9", "Tom Wright", "2022040063", "BCSSE", "21", "16", "5", "C"},
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
        // Create a custom header renderer that makes text bold with a black background
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setFont(new Font("SansSerif", Font.BOLD, 14));
                label.setBackground(new Color(0xC8151D));
                label.setForeground(Color.WHITE);
                label.setOpaque(true);
                return label;
            }
        };

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setDefaultRenderer(headerRenderer);

        // Optionally, if you want to enforce header renderer for each column
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
        subTitlePanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(subTitlePanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Show the frame
        setVisible(true);
    }

    /**
     * Helper method to create a JLabel with consistent sidebar styling.
     */
    private JLabel createSidebarLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 0));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TeacherDashboard::new);
    }
}
