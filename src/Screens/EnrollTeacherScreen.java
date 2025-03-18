package Screens;

import Classes.Teacher;
import Classes.Course;
import Classes.TeacherCourse;
import DatabaseConnection.TeacherController;
import DatabaseConnection.CourseController;
import DatabaseConnection.TeacherCourseController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EnrollTeacherScreen extends JFrame {
    private JComboBox<Teacher> teacherComboBox;
    private JComboBox<Course> courseComboBox;
    private JButton assignButton;
    private JTextArea assignedCoursesArea;

    public EnrollTeacherScreen() {
        setTitle("Assign Teacher to Course");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));

        // Fetch teachers and courses
        List<Teacher> teachers = TeacherController.getAllTeachers();
        List<Course> courses = CourseController.getAllCourses();

        // Convert Lists to arrays for ComboBoxes
        teacherComboBox = new JComboBox<>(teachers.toArray(new Teacher[0]));
        courseComboBox = new JComboBox<>(courses.toArray(new Course[0]));

        assignButton = new JButton("Assign");
        assignedCoursesArea = new JTextArea(10, 30);
        assignedCoursesArea.setEditable(false);

        // Adding components
        inputPanel.add(new JLabel("Select Teacher:"));
        inputPanel.add(teacherComboBox);
        inputPanel.add(new JLabel("Select Course:"));
        inputPanel.add(courseComboBox);
        inputPanel.add(assignButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(assignedCoursesArea), BorderLayout.CENTER);

        // Assign button action
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Teacher selectedTeacher = (Teacher) teacherComboBox.getSelectedItem();
                Course selectedCourse = (Course) courseComboBox.getSelectedItem();

                if (selectedTeacher != null && selectedCourse != null) {
                    TeacherCourse teacherCourse = new TeacherCourse(selectedCourse.getId(),selectedTeacher.getUserID());
                    TeacherCourseController.assignTeacherToCourse(teacherCourse);
                    JOptionPane.showMessageDialog(null, "Assigned " + selectedTeacher.getName() + " to " + selectedCourse.getName());

                    // Refresh assigned courses
                    showAssignedCourses(selectedTeacher.getUserID());
                }
            }
        });

        // Update assigned courses when selecting a teacher
        teacherComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Teacher selectedTeacher = (Teacher) teacherComboBox.getSelectedItem();
                if (selectedTeacher != null) {
                    showAssignedCourses(selectedTeacher.getUserID());
                }
            }
        });

        setVisible(true);
    }

    // Method to display assigned courses for a teacher
    private void showAssignedCourses(int teacherID) {
        List<TeacherCourse> assignedCourses = TeacherCourseController.getCoursesByTeacher(teacherID);
        StringBuilder sb = new StringBuilder("Assigned Courses:\n");
        for (TeacherCourse tc : assignedCourses) {
            sb.append("• ").append(tc.getCourseID()).append("\n");
        }
        assignedCoursesArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        new EnrollTeacherScreen();
    }
}

