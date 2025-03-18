package Classes;

public class Course {
    private String id;
    private String name;

    public Course(String id, String name, int semester) {
        this.id = id;
        this.name = name;
        this.semester = semester;
    }

    public Course() {
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

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
    @Override
    public String toString() {
        return name;  // ✅ This ensures names appear in JComboBox
    }
    private int semester;
}

