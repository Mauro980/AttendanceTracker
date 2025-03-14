package Classes;

public class User {
        protected int userID;
        protected String name;
        protected String email;
        protected String role;
        protected String password;

        public User(int userID, String name, String email, String role, String password) {
            this.userID = userID;
            this.name = name;
            this.email = email;
            this.role = role;
            this.password = password;
        }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

