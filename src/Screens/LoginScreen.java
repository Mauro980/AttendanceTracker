package Screens;

import javax.swing.*;

public class LoginScreen {
    private JFrame window;

    public  LoginScreen(){
        window = new JFrame();
        window.setTitle("Login Page");
        window.setSize(800,500);
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        window.setLocationRelativeTo(null);
    }
    public void show(){
        window.setVisible(true);
    }
}
