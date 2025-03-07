import Screens.LoginScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World Lets go!");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginScreen login = new LoginScreen();
                login.show();
            }
        });
    }
}