package Classes;

public class User {
        protected int userID;
        protected String name;
        protected String role;
        protected String password;

        public User(int userID, String name, String role, String password) {
            this.userID = userID;
            this.name = name;
            this.role = role;
            this.password = password;
        }



        public void logout() {
            System.out.println(name + " has logged out.");
        }
    }

