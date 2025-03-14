package Classes;

public class Student {

        private int studentID;
        private String className;

        public Student(int userID, String name, String password, int studentID, String className) {

            this.studentID = studentID;
            this.className = className;
        }

        public void viewAttendance() {
            System.out.println(  " is viewing attendance.");
        }


    }

