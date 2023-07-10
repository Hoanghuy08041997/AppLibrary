package View;

import Controller.ManagementLibrary;
import Controller.MethodController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class CustomerMenuUI extends JFrame {
    private JSplitPane splitPane;
    private JPanel functionPanel;
    private JPanel contentPanel;
    private JButton listBooksButton;
    private JButton searchBookButton;
    private JButton lendBookButton;
    private JButton listLendingBooksButton;
    private JButton exitButton;
    private final LoginInfoUI loginInfoUI;

    public CustomerMenuUI() {
        setTitle("Customer Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 750));
        loginInfoUI = new LoginInfoUI();
        initializeUI();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MethodController.exit();
            }
        });
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

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
                loginInfoUI.showInfo();
            });
            showInfoItem.setToolTipText("Show Your Account Infomation.");
            popupMenu.add(showInfoItem);

            // Sự lựa chọn "Log out"
            JMenuItem logoutItem = new JMenuItem("Log out");
            logoutItem.addActionListener((ActionEvent event) -> {
                loginInfoUI.logout();
                JOptionPane.showMessageDialog(this, "Logged out successfully!");
            });
            loginInfoButton.setToolTipText("Log out your Account.");
            popupMenu.add(logoutItem);

            popupMenu.show(loginInfoButton, 0, loginInfoButton.getHeight());
        });

        // Add the loginInfoButton to the panel
        loginInfoPanel.add(loginInfoButton);
        mainPanel.add(loginInfoPanel, BorderLayout.NORTH);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(5);

        functionPanel = new JPanel();
        functionPanel.setLayout(new GridLayout(8, 1));
        splitPane.setLeftComponent(functionPanel);

        contentPanel = new JPanel(new GridBagLayout());

        // List books button
        listBooksButton = new JButton("1. List all books");
        listBooksButton.addActionListener((ActionEvent e) -> {
            contentPanel.removeAll();
            contentPanel.add(new BookListUI(ManagementLibrary.book));
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        listBooksButton.setToolTipText("List all of books in library.");
        functionPanel.add(listBooksButton);

        // Search book button
        searchBookButton = new JButton("2. Search Book");
        searchBookButton.addActionListener((ActionEvent e) -> {
            contentPanel.removeAll();
            contentPanel.add(new SearchBookUI(ManagementLibrary.logged.get(0).getId(),"search"));
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        searchBookButton.setToolTipText("Search Book for Lend - Return.");
        functionPanel.add(searchBookButton);

        // List lending books button
        listLendingBooksButton = new JButton("3. List Lending Book");
        listLendingBooksButton.addActionListener((ActionEvent e) -> {
            contentPanel.removeAll();
            contentPanel.add(new BookBorrowListUI(ManagementLibrary.bookBorrow));
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        listLendingBooksButton.setToolTipText("List your lending books.");
        functionPanel.add(listLendingBooksButton);

        // Lend book button
        lendBookButton = new JButton("Coming Soon...");
        lendBookButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Coming soon...");
        });
        lendBookButton.setToolTipText("Next feature will develop.");
        functionPanel.add(lendBookButton);
        // Exit button
        exitButton = new JButton("5. Exit");
        exitButton.addActionListener((ActionEvent e) -> {
            MethodController.exit();
        });
        exitButton.setToolTipText("Exit program.");
        functionPanel.add(exitButton);

        splitPane.setRightComponent(new JScrollPane(contentPanel));

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
        setResizable(false);
    }
}
