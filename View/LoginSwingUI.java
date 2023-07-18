<<<<<<< HEAD
package View;

import Controller.MethodController;
import Controller.ValidateForSwing;
import Model.Account;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class LoginSwingUI extends JFrame {
    private final JTextField usernameTextField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton createAccountButton;
    private final JButton exitButton;
    private final JButton helpButton;

    public LoginSwingUI() {
        
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MethodController.exit();
                JOptionPane.showMessageDialog(null, "Saved!");
            }
        });
        
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username: ");
        usernameTextField = new JTextField();
        usernameTextField.setToolTipText("Your username");

        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField();
        passwordField.setToolTipText("Password must be at least 8 characters long and contain at least one digit, one letter, and one special character.");

        loginButton = new JButton("Login Account");
        loginButton.setToolTipText("Login your Account");
        createAccountButton = new JButton("Create Account");
        createAccountButton.setToolTipText("Create new Account");
        exitButton = new JButton("Exit");
        exitButton.setToolTipText("Exit program");
        helpButton = new JButton("Help");

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameTextField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        mainPanel.add(createAccountButton);
        mainPanel.add(exitButton);
        mainPanel.add(helpButton);

        add(mainPanel);

        loginButton.addActionListener((ActionEvent e) -> {
            String us = usernameTextField.getText();
            String pd = new String(passwordField.getPassword());          
                Account s = new Account(us, pd);
                int level = Account.checkLevelAccount(s);
                switch (level) {
                    case 0:
                        JOptionPane.showMessageDialog(null, "WRONG USERNAME OR PASSWORD. PLEASE DOUBLE CHECK OR CREATE ACCOUNT.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 1:
                        Account.returnAccount(s);
                        JOptionPane.showMessageDialog(null, "WELCOME CUSTOMER");
                        CustomerMenuUI customerMenu = new CustomerMenuUI();
                        customerMenu.setVisible(true);
                        dispose();
                        break;
                    case 2:
                        Account.returnAccount(s);
                        JOptionPane.showMessageDialog(null, "WELCOME MANAGER");
                        ManagerMenuUI managerMenu = new ManagerMenuUI();
                        managerMenu.setVisible(true);
                        dispose();
                        break;
                }          
        });

        createAccountButton.addActionListener((ActionEvent e) -> {
            CreateAccountUI createAccountUI = new CreateAccountUI();
            createAccountButton.setToolTipText("Create new Account");
            String us = usernameTextField.getText();
            String pd = new String(passwordField.getPassword());
            boolean validUsername = ValidateForSwing.isValidUsername(us);
            boolean validPassword = ValidateForSwing.checkPassword(passwordField);
            if (validUsername){
                if (validPassword) {
                   createAccountUI.createAccountForCall(us,pd);
                } else JOptionPane.showMessageDialog(null, "Invalid password. Password must be at least 8 characters long and contain at least one digit, one letter, and one special character.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        exitButton.addActionListener((ActionEvent e) -> {
            MethodController.exit();
        });
            
        helpButton.addActionListener((ActionEvent e) -> {
            JPanel help = new JPanel();
            String message = "<html><font color='red' size='+1'>Help</font>"
                            + "<br>Login your account to use Application."
                            + "<br>If you are a manager and don't have an account, you should contact your HM to get one."
                            + "<br>If you are a customer, you can either create a new account or log in."
                            + "<br>The accounts with level 2 are managers."
                            + "<br>New accounts are always level 1, which is for customers."
                            + "<br>When using the app, if you're unsure about a specific feature, you can hover your mouse over it for hints.</html>";
            JOptionPane.showMessageDialog(help, message, "Help", JOptionPane.INFORMATION_MESSAGE);
        });
        
    }

    public static void main(String[] args) {
        MethodController.loadData();
        SwingUtilities.invokeLater(() -> {          
            LoginSwingUI loginSwingUI = new LoginSwingUI();
            loginSwingUI.setVisible(true);
        });
    }    
}
=======
package View;

import Controller.MethodController;
import Controller.ValidateForSwing;
import Model.Account;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class LoginSwingUI extends JFrame {
    private final JTextField usernameTextField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton createAccountButton;
    private final JButton exitButton;
    private final JButton helpButton;

    public LoginSwingUI() {
        
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MethodController.exit();
                JOptionPane.showMessageDialog(null, "Saved!");
            }
        });
        
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username: ");
        usernameTextField = new JTextField();
        usernameTextField.setToolTipText("Your username");

        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField();
        passwordField.setToolTipText("Password must be at least 8 characters long and contain at least one digit, one letter, and one special character.");

        loginButton = new JButton("Login Account");
        loginButton.setToolTipText("Login your Account");
        createAccountButton = new JButton("Create Account");
        createAccountButton.setToolTipText("Create new Account");
        exitButton = new JButton("Exit");
        exitButton.setToolTipText("Exit program");
        helpButton = new JButton("Help");

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameTextField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        mainPanel.add(createAccountButton);
        mainPanel.add(exitButton);
        mainPanel.add(helpButton);

        add(mainPanel);

        loginButton.addActionListener((ActionEvent e) -> {
            String us = usernameTextField.getText();
            String pd = new String(passwordField.getPassword());          
                Account s = new Account(us, pd);
                int level = Account.checkLevelAccount(s);
                switch (level) {
                    case 0:
                        JOptionPane.showMessageDialog(null, "WRONG USERNAME OR PASSWORD. PLEASE DOUBLE CHECK OR CREATE ACCOUNT.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 1:
                        Account.returnAccount(s);
                        JOptionPane.showMessageDialog(null, "WELCOME CUSTOMER");
                        CustomerMenuUI customerMenu = new CustomerMenuUI();
                        customerMenu.setVisible(true);
                        dispose();
                        break;
                    case 2:
                        Account.returnAccount(s);
                        JOptionPane.showMessageDialog(null, "WELCOME MANAGER");
                        ManagerMenuUI managerMenu = new ManagerMenuUI();
                        managerMenu.setVisible(true);
                        dispose();
                        break;
                }          
        });

        createAccountButton.addActionListener((ActionEvent e) -> {
            CreateAccountUI createAccountUI = new CreateAccountUI();
            createAccountButton.setToolTipText("Create new Account");
            String us = usernameTextField.getText();
            String pd = new String(passwordField.getPassword());
            boolean validUsername = ValidateForSwing.isValidUsername(us);
            boolean validPassword = ValidateForSwing.checkPassword(passwordField);
            if (validUsername){
                if (validPassword) {
                   createAccountUI.createAccountForCall(us,pd);
                } else JOptionPane.showMessageDialog(null, "Invalid password. Password must be at least 8 characters long and contain at least one digit, one letter, and one special character.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        exitButton.addActionListener((ActionEvent e) -> {
            MethodController.exit();
        });
            
        helpButton.addActionListener((ActionEvent e) -> {
            JPanel help = new JPanel();
            String message = "<html><font color='red' size='+1'>Help</font>"
                            + "<br>Login your account to use Application."
                            + "<br>If you are a manager and don't have an account, you should contact your HM to get one."
                            + "<br>If you are a customer, you can either create a new account or log in."
                            + "<br>The accounts with level 2 are managers."
                            + "<br>New accounts are always level 1, which is for customers."
                            + "<br>When using the app, if you're unsure about a specific feature, you can hover your mouse over it for hints.</html>";
            JOptionPane.showMessageDialog(help, message, "Help", JOptionPane.INFORMATION_MESSAGE);
        });
        
    }

    public static void main(String[] args) {
        MethodController.loadData();
        SwingUtilities.invokeLater(() -> {          
            LoginSwingUI loginSwingUI = new LoginSwingUI();
            loginSwingUI.setVisible(true);
        });
    }    
}
>>>>>>> c28e40b05d0e464600f6564548192bbceca3d7ee
