package Screens;

import Classes.Course;
import Classes.Enrollment;
import Classes.Student;
import DatabaseConnection.CourseController;
import DatabaseConnection.EnrollmentController;
import DatabaseConnection.StudentController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentScreen extends JFrame {
    private JComboBox<Student> studentComboBox;
    private JComboBox<Course> courseComboBox;
    private JTextField searchField;
    private JButton enrollButton, deleteButton;
    private JTable enrollmentTable;
    private DefaultTableModel tableModel;
    private List<Student> studentsList;
    private List<Course> coursesList;

    public EnrollmentScreen() {
        studentsList = new ArrayList<>();
        coursesList = new ArrayList<>();

        setTitle("Student Course Enrollment");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel for search, student & course selection
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));

        searchField = new JTextField();
        studentComboBox = new JComboBox<>();
        courseComboBox = new JComboBox<>();
        enrollButton = new JButton("Enroll");
        deleteButton = new JButton("Delete Enrollment");

        inputPanel.add(new JLabel("Search Student:"));
        inputPanel.add(searchField);
        inputPanel.add(new JLabel("Select Student:"));
        inputPanel.add(studentComboBox);
        inputPanel.add(new JLabel("Select Course:"));
        inputPanel.add(courseComboBox);
        inputPanel.add(enrollButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table to display enrollments
        tableModel = new DefaultTableModel();
        enrollmentTable = new JTable(tableModel);
        tableModel.addColumn("Enrollment ID");
        tableModel.addColumn("Student ID");
        tableModel.addColumn("Student Name");
        tableModel.addColumn("Course ID");
        tableModel.addColumn("Course Name");
        tableModel.addColumn("Enrollment Date");

        add(new JScrollPane(enrollmentTable), BorderLayout.CENTER);

        // Load students and courses into lists
        loadStudents();
        loadCourses();
        updateStudentComboBox(studentsList);
        updateCourseComboBox(coursesList);
        loadEnrollments();

        // Search field filter logic
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String query = searchField.getText().toLowerCase();
                List<Student> filteredStudents = new ArrayList<>();
                for (Student student : studentsList) {
                    if (student.getName().toLowerCase().contains(query)) {
                        filteredStudents.add(student);
                    }
                }
                updateStudentComboBox(filteredStudents);
            }
        });

        // Button actions
        enrollButton.addActionListener(e -> enrollStudent());
        deleteButton.addActionListener(e -> deleteEnrollment());

        setVisible(true);
    }

    private void loadStudents() {
        studentsList.clear();
        studentsList = StudentController.getAllStudents();
    }

    private void loadCourses() {
        coursesList.clear();
        coursesList = CourseController.getAllCourses();
    }

    private void updateStudentComboBox(List<Student> studentData) {
        studentComboBox.removeAllItems();
        for (Student student : studentData) {
            studentComboBox.addItem(student);

        }
    }

    private void updateCourseComboBox(List<Course> courseData) {
        courseComboBox.removeAllItems();
        for (Course course : courseData) {
            courseComboBox.addItem(course);
        }
    }

    private void loadEnrollments() {
        tableModel.setRowCount(0);
        List<Enrollment> enrollments = EnrollmentController.getAllEnrollments();
        for (Enrollment e : enrollments) {
            tableModel.addRow(new Object[]{e.getEnrollmentId(), e.getStudentId(), getStudentName(e.getStudentId()), e.getCourseId(), getCourseName(e.getCourseId()), e.getEnrollmentDate()});
        }
    }

    private String getStudentName(String studentId) {
        for (Student s : studentsList) {
            if (s.getId().equals(studentId)) {
                return s.getName();
            }
        }
        return "Unknown";
    }

    private String getCourseName(String courseId) {
        for (Course c : coursesList) {
            if (c.getId().equals(courseId)) {
                return c.getName();
            }
        }
        return "Unknown";
    }

    private void enrollStudent() {
        if (studentComboBox.getSelectedItem() == null || courseComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a student and a course!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student selectedStudent = (Student) studentComboBox.getSelectedItem();
        Course selectedCourse = (Course) courseComboBox.getSelectedItem();
        String enrollmentId = "E" + System.currentTimeMillis();
        String enrollmentDate = java.time.LocalDate.now().toString();

        Enrollment enrollment = new Enrollment(enrollmentId, selectedStudent.getId(), selectedCourse.getId(), enrollmentDate);
        EnrollmentController.enrollStudent(enrollment);
        JOptionPane.showMessageDialog(this, "Student enrolled successfully!");
        loadEnrollments();
    }

    private void deleteEnrollment() {
        int selectedRow = enrollmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an enrollment to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String enrollmentId = tableModel.getValueAt(selectedRow, 0).toString();
        EnrollmentController.deleteEnrollment(enrollmentId);
        JOptionPane.showMessageDialog(this, "Enrollment deleted successfully!");
        loadEnrollments();
    }

    public static void main(String[] args) {
        new EnrollmentScreen();
    }
}
