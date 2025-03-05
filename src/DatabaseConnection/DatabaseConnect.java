package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public  class DatabaseConnect {
    static Statement statement;
    public static void DbConnect(){
        try {
            Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","");
        /*here javafx is the name of the database and root is the username of
         your xampp server and the field which is blank is for password.
         Because I haven't set the password. I have left it blank*/
            statement = conn.createStatement();
            System.out.print("Database Connected");
        } catch (Exception e) {
            System.out.print("Database Not Connected");
        }
    }
}
