package View;

import Controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ManagerMenuUI extends JFrame {
    private JSplitPane splitPane;
    private JPanel functionPanel;
    private JPanel contentPanel;
    private JButton listBooksButton;
    private JButton listCustomersButton;
    private JButton searchBookButton;
    private JButton searchCustomerButton;
    private JButton addAccountButton;
    private JButton removeAccountButton;
    private JButton listLendBookButton;
    private JButton exitButton;

    private final AccountManagementUI accountManagementUI;
    private final CreateAccountUI createAccountUI;
    private final SearchCustomerUI searchCustomerUI;
    private final SearchBookUI searchBookUI;

    public ManagerMenuUI() {
        setTitle("Manager Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            
        setPreferredSize(new Dimension(1000, 750));
        run();
        accountManagementUI = new AccountManagementUI();
        createAccountUI = new CreateAccountUI();
        searchCustomerUI = new SearchCustomerUI();
        searchBookUI = new SearchBookUI(0,"search");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MethodController.exit();
            }
        });
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(5);

        functionPanel = new JPanel();
        functionPanel.setLayout(new GridLayout(8, 1));
        splitPane.setLeftComponent(functionPanel);

        contentPanel = new JPanel(new GridBagLayout());

        // Nút "List all books"
        listBooksButton = new JButton("1. List all books");
        listBooksButton.addActionListener((ActionEvent e) -> {
            contentPanel.removeAll();
            contentPanel.add(new BookListUI(ManagementLibrary.book));
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        listBooksButton.setToolTipText("List all of books in library.");
        functionPanel.add(listBooksButton);

        // Nút "List all customers"
        listCustomersButton = new JButton("2. List all customers");
        listCustomersButton.addActionListener((ActionEvent e) -> {       
            contentPanel.removeAll();
            contentPanel.add(new CustomerListUI(ManagementLibrary.customer));
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        listCustomersButton.setToolTipText("List all customers in the library.");        
        functionPanel.add(listCustomersButton);

        searchBookButton = new JButton("3. Search book");
        searchBookButton.addActionListener((ActionEvent e) -> {
            contentPanel.removeAll();
            contentPanel.add(searchBookUI);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        searchBookButton.setToolTipText("Search book for Lend - Return - Edit - Remove or Add new Book");
        functionPanel.add(searchBookButton);

        // Nút "Search customer"
        searchCustomerButton = new JButton("4. Search customer");
        searchCustomerButton.addActionListener((ActionEvent e) -> {          
            contentPanel.removeAll();
            contentPanel.add(searchCustomerUI);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        searchCustomerButton.setToolTipText("Search customer for Lend - Return - Remove Account");
        functionPanel.add(searchCustomerButton);

        // Nút "Add account"
        addAccountButton = new JButton("5. Add account");
        addAccountButton.addActionListener((ActionEvent e) -> {
            contentPanel.removeAll();
            contentPanel.add(createAccountUI);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        addAccountButton.setToolTipText("Add new Account Customer");
        functionPanel.add(addAccountButton);

        // Nút "Remove account"
        removeAccountButton = new JButton("6. Remove account");
        removeAccountButton.addActionListener((ActionEvent e) -> {
            contentPanel.removeAll();
            contentPanel.add(accountManagementUI);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        removeAccountButton.setToolTipText("Remove Account Customer");
        functionPanel.add(removeAccountButton);

        // Nút "Remove account"
        listLendBookButton = new JButton("7. List Lend Book");
        listLendBookButton.addActionListener((ActionEvent e) -> {
            contentPanel.removeAll();
            contentPanel.add(new BookBorrowListUI(ManagementLibrary.bookBorrow));
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        listLendBookButton.setToolTipText("List Lend Book of Customer");
        functionPanel.add(listLendBookButton);
        
        
        // Nút "Exit"
        exitButton = new JButton("8. Exit");
        exitButton.addActionListener((ActionEvent e) -> {
            MethodController.exit();
        });
        exitButton.setToolTipText("Exit program");
        functionPanel.add(exitButton);

        splitPane.setRightComponent(new JScrollPane(contentPanel));

        JPanel loginInfoPanel = new JPanel();
        loginInfoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton loginInfoButton = new JButton("Your Account");
        loginInfoButton.addActionListener((ActionEvent e) -> {
            JPopupMenu popupMenu = new JPopupMenu();

            JMenuItem showNameAccount = new JMenuItem("Login as: " + ManagementLibrary.logged.get(0).getName());
            showNameAccount.setEnabled(false);
            showNameAccount.addActionListener((ActionEvent event) -> {
            });
            showNameAccount.setToolTipText("Your account logged.");
            popupMenu.add(showNameAccount);

            // Sự lựa chọn "Show info"
            JMenuItem showInfoItem = new JMenuItem("Show info");
            showInfoItem.addActionListener((ActionEvent event) -> {
                LoginInfoUI loginInfoUI = new LoginInfoUI();
                loginInfoUI.showInfo();
            });
            showInfoItem.setToolTipText("Show your Account information.");
            popupMenu.add(showInfoItem);

            // Sự lựa chọn "Log out"
            JMenuItem logoutItem = new JMenuItem("Log out");
            logoutItem.addActionListener((ActionEvent event) -> {
                LoginInfoUI loginInfoUI = new LoginInfoUI();
                loginInfoUI.logout();
                JOptionPane.showMessageDialog(this, "Logged out successfully!");
            });
            logoutItem.setToolTipText("Log out your Account.");
            popupMenu.add(logoutItem);

            popupMenu.show(loginInfoButton, 0, loginInfoButton.getHeight());
        });
        loginInfoPanel.add(loginInfoButton);
        mainPanel.add(loginInfoPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        // Create a new JPanel for the version label
        JPanel versionPanel = new JPanel();
        versionPanel.setLayout(new BorderLayout());

        JLabel versionLabel = new JLabel("Version 1.0 ");
        versionLabel.setToolTipText("Program Version.");
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        versionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        versionPanel.add(versionLabel, BorderLayout.EAST);

        mainPanel.add(versionPanel, BorderLayout.SOUTH);
        // Add the splitPane to the center of the mainPanel
        mainPanel.add(splitPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public final void run() {
        initializeUI();
        setVisible(true);
    }
}
