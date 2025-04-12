package Screens;

import Classes.*;
import DatabaseConnection.AttendanceController;
import DatabaseConnection.CourseController;
import DatabaseConnection.StudentController;
import DatabaseConnection.TeacherCourseController;

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
    private String currentAttendanceId; // common session ID

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

        loadAllStudents();
        frame.setVisible(true);
    }

    private void initializeStudentTable() {
        // Initial table model with non-editable student details.
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
            // Generate a common session attendance ID using teacher's ID and current timestamp.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            currentAttendanceId = loggedInTeacher.getUserID() + "_" + LocalDateTime.now().format(formatter);

            // Create and save the session attendance record.
            Attendance sessionAttendance = new Attendance(
                    currentAttendanceId,
                    selectedCourse,
                    loggedInTeacher.getUserID(),
                    LocalDateTime.now()
            );

            if (AttendanceController.addAttendance(sessionAttendance)) {
                // Proceed only if the session attendance record is created.
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
        // New table model with an editable "Present" checkbox column.
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
                    student.getName(),
                    false
            });
        }
        return model;
    }

    // Updated finishAttendanceSession method: For each student marked "Present",
    // create an Attendance object using the fields: attendanceId, courseId, teacherId, date.
    // A unique attendanceId is generated for each student by appending the student ID.
    private void finishAttendanceSession(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to finish this attendance session?",
                "Confirm Finish Session",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Ensure any active cell editing is stopped so that the checkbox values are committed.
                if (studentTable.getCellEditor() != null) {
                    studentTable.getCellEditor().stopCellEditing();
                }

                DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
                boolean allSaved = true;
                // Loop through each student row.
                for (int row = 0; row < model.getRowCount(); row++) {
                    Boolean present = (Boolean) model.getValueAt(row, 2);
                    // Only record attendance for students marked as present.
                    if (present != null && present) {
                        String studentId = (String) model.getValueAt(row, 0);
                        // Generate a unique attendance record ID for the student.
                        String uniqueAttendanceId = currentAttendanceId + "_" + studentId;
                        // Create an Attendance object using the required fields:
                        // attendanceId, courseId, teacherId, date.
                        Attendance studentAttendance = new Attendance(
                                uniqueAttendanceId,
                                (String) courseDropdown.getSelectedItem(),
                                loggedInTeacher.getUserID(),
                                LocalDateTime.now()
                        );
                        boolean saved = AttendanceController.addAttendance(studentAttendance);
                        if (!saved) {
                            allSaved = false;
                            break;
                        }
                    }
                }
                if (allSaved) {
                    JOptionPane.showMessageDialog(frame, "Attendance saved successfully!");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error saving one or more attendance records!");
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
