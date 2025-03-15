package Screens;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MarkAttendanceScreen extends JFrame {
    private final Color PRIMARY_COLOR = new Color(0xC8151D);
    private final Color TEXT_COLOR = Color.WHITE;

    private JComboBox<String> subjectComboBox;
    private JTable attendanceTable;

    public MarkAttendanceScreen() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Mark Attendance");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel selectLabel = new JLabel("Select Subject:");
        selectLabel.setForeground(TEXT_COLOR);
        selectLabel.setFont(new Font("Arial", Font.BOLD, 14));

        subjectComboBox = new JComboBox<>(new String[]{"Mathematics", "Physics", "Computer Science"});
        subjectComboBox.setPreferredSize(new Dimension(200, 30));

        panel.add(selectLabel);
        panel.add(subjectComboBox);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Date"};
        Object[][] data = {
                {"2025-03-01"},
                {"2025-03-08"},
                {"2025-03-15"},
                {"2025-03-22"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        attendanceTable = new JTable(model);
        attendanceTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = attendanceTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String selectedDate = (String) attendanceTable.getValueAt(selectedRow, 0);
                        JOptionPane.showMessageDialog(null, "Selected Date: " + selectedDate, "Attendance Details", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(attendanceTable);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MarkAttendanceScreen::new);
    }
}
