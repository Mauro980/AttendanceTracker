package Screens;
import Classes.*;
import DatabaseConnection.AttendanceController;
import DatabaseConnection.CourseController;
import DatabaseConnection.StudentController;
import DatabaseConnection.AttendanceStudentController;
import DatabaseConnection.TeacherCourseController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MarkAttendanceScreen {
    private JFrame frame;
    private JComboBox<String> courseDropdown;
    private JButton createAttendanceButton;
    private JPanel studentPanel;
    private Teacher loggedInTeacher ;

    public MarkAttendanceScreen() {
        loggedInTeacher = Brigde.loggedTeacher;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Mark Attendance");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));

        // Display logged-in teacher
        JLabel teacherLabel = new JLabel("Teacher: " + loggedInTeacher.getName());
        topPanel.add(teacherLabel);

        // Course Dropdown (Courses assigned to logged-in teacher)
        courseDropdown = new JComboBox<>();
        loadTeacherCourses();
        topPanel.add(new JLabel("Select Course:"));
        topPanel.add(courseDropdown);

        frame.add(topPanel, BorderLayout.NORTH);

        // Create Attendance Button
        createAttendanceButton = new JButton("Create Attendance Session");
        createAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAttendanceSession();
            }
        });
        frame.add(createAttendanceButton, BorderLayout.SOUTH);

        // Student Panel (Where students will be displayed)
        studentPanel = new JPanel();
        studentPanel.setLayout(new GridLayout(0, 2));
        frame.add(new JScrollPane(studentPanel), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void loadTeacherCourses() {
        List<Course> courses = CourseController.getAllCourses();


        List<TeacherCourse> coursesfromT = TeacherCourseController.getCoursesByTeacher(loggedInTeacher.getUserID());
        List<Course> coursesfromTeacher = new ArrayList<>();


        for(Course i:courses){
            for (TeacherCourse j: coursesfromT){
                if(j.getCourseID().equals(i.getId())){
                    coursesfromTeacher.add(i);
                }
            }
        }
        for (Course course : coursesfromTeacher) {
            courseDropdown.addItem(course.getId());
        }

    }

    private void createAttendanceSession() {
        String selectedCourse = (String) courseDropdown.getSelectedItem();
        if (selectedCourse == null) {
            JOptionPane.showMessageDialog(frame, "Please select a course.");
            return;
        }

        // Generate Attendance ID (Teacher ID + Date)
        String date = LocalDate.now().toString();
        String attendanceId = loggedInTeacher.getUserID() + "-" + date;

        // Create attendance record in memory
        Attendance attendance = new Attendance(attendanceId, selectedCourse, String.valueOf(loggedInTeacher.getUserID()), date);

        // Load students for attendance
        loadStudentsForAttendance(attendance);
    }

    private void loadStudentsForAttendance(Attendance attendance) {
        studentPanel.removeAll();
        List<Student> students = StudentController.getStudentsByCourse(attendance.getCourseId());

        for (Student student : students) {
            JCheckBox attendanceCheckbox = new JCheckBox(student.getName());
            attendanceCheckbox.setSelected(false);
            studentPanel.add(attendanceCheckbox);

            attendanceCheckbox.addActionListener(e -> {
                boolean isPresent = attendanceCheckbox.isSelected();
                AttendanceStudentController.addAttendance(new AttendanceStudent(attendance.getAttendanceId(), student.getId(), isPresent));
            });
        }
        studentPanel.revalidate();
        studentPanel.repaint();
    }

    public static void main(String[] args) {
        new MarkAttendanceScreen();
    }
}
