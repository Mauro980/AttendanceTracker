package Screens;
import Classes.*;
import DatabaseConnection.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MarkAttendanceScreen {
    private JFrame frame;
    private JComboBox<String> courseDropdown;
    private JButton createSessionButton;
    private JButton finishSessionButton;
    private JTable studentTable;
    private Teacher loggedInTeacher;
    private String currentAttendanceId;

    public MarkAttendanceScreen() {
        loggedInTeacher = Brigde.loggedTeacher;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Attendance Session Management");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        // Top panel with controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Course selection
        controlPanel.add(new JLabel("Select Course:"));
        courseDropdown = new JComboBox<>();
        loadTeacherCourses();
        controlPanel.add(courseDropdown);

        // Session buttons
        createSessionButton = new JButton("Create New Session");
        createSessionButton.addActionListener(this::createAttendanceSession);
        controlPanel.add(createSessionButton);

        finishSessionButton = new JButton("Finish Session");
        finishSessionButton.setEnabled(false);
        finishSessionButton.addActionListener(this::finishAttendanceSession);
        controlPanel.add(finishSessionButton);

        // Student table
        studentTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(studentTable);
        initializeStudentTable();

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

//        loadAllStudents();
        frame.setVisible(true);
    }

    private void initializeStudentTable() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Student ID", "Name", "Email", "Phone"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable.setModel(model);
    }

    private void loadTeacherCourses() {
        List<Course> courses = CourseController.getAllCourses();
        List<TeacherCourse> teacherCourses = TeacherCourseController.getCoursesByTeacher(loggedInTeacher.getUserID());

        for (Course course : courses) {
            for (TeacherCourse tc : teacherCourses) {
                if (tc.getCourseID().equals(course.getId())) {
                    courseDropdown.addItem(course.getId());
                }
            }
        }
    }

    private void loadAllStudents() {
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        model.setRowCount(0);

        List<Student> students = StudentController.getAllStudents();
        for (Student student : students) {
            model.addRow(new Object[]{
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getContact()
            });
        }
    }

    private void createAttendanceSession(ActionEvent e) {
        String selectedCourse = (String) courseDropdown.getSelectedItem();
        if (selectedCourse == null) {
            JOptionPane.showMessageDialog(frame, "Please select a course first!");
            return;
        }

        try {
            // Generate shorter timestamp format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            currentAttendanceId = loggedInTeacher.getUserID() + "_" + LocalDateTime.now().format(formatter);

            // Create and save attendance record first
            Attendance attendance = new Attendance(
                    currentAttendanceId,
                    selectedCourse,
                    String.valueOf(loggedInTeacher.getUserID()),
                    LocalDateTime.now()  // Store as LocalDateTime object
            );

            if (AttendanceController.addAttendance(attendance)) {
                // Only proceed if attendance was successfully created
                DefaultTableModel newModel = createAttendanceModel();
                studentTable.setModel(newModel);
                finishSessionButton.setEnabled(true);
                createSessionButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to create attendance session!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error creating session: " + ex.getMessage());
        }
    }

    private DefaultTableModel createAttendanceModel() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Student ID", "Name", "Present"}, 0
        ) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 2 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        List<Student> students = StudentController.getStudentsByCourse((String) courseDropdown.getSelectedItem());
        for (Student student : students) {
            model.addRow(new Object[]{
                    student.getId(),
                    student.getName(),  // Make sure Student class has getName()
                    false
            });
        }
        return model;
    }


    // Kept finishAttendanceSession method using bulk add of attendance records
    private void finishAttendanceSession(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to finish this attendance session?",
                "Confirm Finish Session",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
                List<AttendanceStudent> records = new ArrayList<>();

                for (int row = 0; row < model.getRowCount(); row++) {
                    records.add(new AttendanceStudent(
                            currentAttendanceId,
                            (String) model.getValueAt(row, 0),
                            (Boolean) model.getValueAt(row, 2)
                    ));
                }

                if (AttendanceStudentController.bulkAddAttendance(records)) {
                    JOptionPane.showMessageDialog(frame, "Attendance saved successfully!");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error saving some attendance records!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error saving attendance: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MarkAttendanceScreen());
    }
}
