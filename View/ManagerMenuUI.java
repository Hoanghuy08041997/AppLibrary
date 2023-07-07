package View;

import Controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
        functionPanel.add(listBooksButton);

        // Nút "List all customers"
        listCustomersButton = new JButton("2. List all customers");
        listCustomersButton.addActionListener((ActionEvent e) -> {
            contentPanel.removeAll();
            contentPanel.add(new CustomerListUI(ManagementLibrary.customer));
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        functionPanel.add(listCustomersButton);

        searchBookButton = new JButton("3. Search book");
        searchBookButton.addActionListener((ActionEvent e) -> {
            contentPanel.removeAll();
            contentPanel.add(searchBookUI);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        functionPanel.add(searchBookButton);

        // Nút "Search customer"
        searchCustomerButton = new JButton("4. Search customer");
        searchCustomerButton.addActionListener((ActionEvent e) -> {          
            contentPanel.removeAll();
            contentPanel.add(searchCustomerUI);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        functionPanel.add(searchCustomerButton);

        // Nút "Add account"
        addAccountButton = new JButton("5. Add account");
        addAccountButton.addActionListener((ActionEvent e) -> {
            contentPanel.removeAll();
            contentPanel.add(createAccountUI);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        functionPanel.add(addAccountButton);

        // Nút "Remove account"
        removeAccountButton = new JButton("6. Remove account");
        removeAccountButton.addActionListener((ActionEvent e) -> {
            contentPanel.removeAll();
            contentPanel.add(accountManagementUI);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        functionPanel.add(removeAccountButton);

        // Nút "Remove account"
        listLendBookButton = new JButton("7. List Lend Book");
        listLendBookButton.addActionListener((ActionEvent e) -> {
            contentPanel.removeAll();
            contentPanel.add(new BookBorrowListUI(ManagementLibrary.bookBorrow));
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        functionPanel.add(listLendBookButton);
        
        
        // Nút "Exit"
        exitButton = new JButton("8. Exit");
        exitButton.addActionListener((ActionEvent e) -> {
            MethodController.exit();
        });
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
            popupMenu.add(showNameAccount);

            // Sự lựa chọn "Show info"
            JMenuItem showInfoItem = new JMenuItem("Show info");
            showInfoItem.addActionListener((ActionEvent event) -> {
                LoginInfoUI loginInfoUI = new LoginInfoUI();
                loginInfoUI.showInfo();
            });
            popupMenu.add(showInfoItem);

            // Sự lựa chọn "Log out"
            JMenuItem logoutItem = new JMenuItem("Log out");
            logoutItem.addActionListener((ActionEvent event) -> {
                LoginInfoUI loginInfoUI = new LoginInfoUI();
                loginInfoUI.logout();
                JOptionPane.showMessageDialog(this, "Logged out successfully!");
            });
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
