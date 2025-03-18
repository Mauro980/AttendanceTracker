package Classes;

public class Student {

        private String id;
        private String name;
        private String gender;
        private String email;
        private String contact;

        public Student(String id, String name, String gender,String email,String contact) {
            this.id = id;
           this.name = name;
           this.gender = gender;
           this.email = email;
           this.contact = contact;
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    @Override
    public String toString() {
        return name;  // ✅ This ensures names appear in JComboBox
    }
}

