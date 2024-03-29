package View;

import Controller.*;
import Model.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


public class SearchBookUI extends JPanel {
    private final JTextField searchField;
    private final JComboBox<String> searchProperties;
    private JTable bookTable;
    private JPanel bookDetailsPanel;
    private JPanel bookDetailsPanelForManager;
    private JPanel createBookDetailsPanel;

    public SearchBookUI(int IdCustomer,String s) {
        
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Search properties
        String[] options = { "name", "author", "id", "type" };

        searchProperties = new JComboBox<>(options);
        searchProperties.setPreferredSize(new Dimension(100, 25));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(5, 5, 5, 5);
        add(searchProperties, constraints);

        // Search field
        searchField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1.1;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(searchField, constraints);

        // Search button
        JButton searchButton = new JButton("Search");
        searchButton.setToolTipText("Search for properties you want");
        searchButton.addActionListener((ActionEvent e) -> {
            String searchCriteria = searchField.getText();           
            String selectedProperty = (String) searchProperties.getSelectedItem();
            if ("lend".equals(s) || "search".equals(s)){
                List<Integer> searchResults = performSearchBook(selectedProperty, searchCriteria);
                updateBookList(searchResults);
            }
            if ("return".equals(s)) {
                selectedProperty = "idCustomer";
                searchCriteria = Integer.toString(IdCustomer);
                List<Integer> searchResults = performSearchBookBorrow(selectedProperty, searchCriteria);
                updateBookBorrowList(searchResults);
            }
        });
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx = 0.0;
        constraints.insets = new Insets(5, 5, 5, 5);
        add(searchButton, constraints);

        // Book table
        bookTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(bookTable);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        add(scrollPane, constraints);

        // Select button
        JButton selectButton = new JButton("Select");
        selectButton.setToolTipText("Choose the book to do something");
        selectButton.addActionListener((ActionEvent e) -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow != -1) {                
                int bookId = (int) bookTable.getValueAt(selectedRow, 0);
                showBookDetails(bookId, IdCustomer,s);
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        constraints.weighty = 0.1;
        constraints.gridwidth = 2; 
        constraints.insets = new Insets(5, 5, 5, 5);
        add(selectButton, constraints);

        // Add book button
        JButton addButton = new JButton("Add Book");
        
        if (ManagementLibrary.logged.get(0).getLevelUser() == 2) {
            addButton.setEnabled(true);
            addButton.setToolTipText("Create new book");
        } else {
            addButton.setEnabled(false);
            addButton.setToolTipText("You don't have permission");
        }

        addButton.addActionListener(((ActionEvent e) -> {
            showTableToEdit(0, "add");
        }));

        constraints.gridx = 2; 
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        constraints.weighty = 0.1;
        constraints.gridwidth = 1; 
        constraints.insets = new Insets(5, 5, 5, 5);
        add(addButton, constraints);
        
        scrollPane.setPreferredSize(new Dimension(800, 450));
        

    }

    // Method to perform search based on given criteria
    private List<Integer> performSearchBook(String searchCriteria, String searchProperty) {
        List<Integer> matchingBooks = MethodController.searchBook(searchCriteria, searchProperty);
        return matchingBooks;
    }
    
    private List<Integer> performSearchBookBorrow(String searchCriteria, String searchProperty) {
        List<Integer> matchingBooks = MethodController.searchBookBorrow (searchCriteria, searchProperty);
        return matchingBooks;
    }

    // Method to update the book list with search results as a table
    private void updateBookList(List<Integer> searchResults) {
            String [] columnNames = {"ID", "Name", "Author", "Type", "Total Book", "Price"};
            Object[][] data = new Object[searchResults.size()][6];
            for (int i = 0; i < searchResults.size(); i++) {
                int bookIndex = searchResults.get(i);
                for (Book book : ManagementLibrary.book) {
                    if ( book.getId() == bookIndex ) {
                        data[i][0] = book.getId();       // ID
                        data[i][1] = book.getName();     // Name
                        data[i][2] = book.getAuthor();   // Author
                        data[i][3] = book.getType();     // Type
                        data[i][4] = book.getNumber();   // Total Book
                        data[i][5] = book.getPrice();    // Price
                    }
                }
            }
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        bookTable.setModel(tableModel);
        
        TableColumnModel columnModel = bookTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);    // ID column width
        columnModel.getColumn(1).setPreferredWidth(300);   // Name column width
        columnModel.getColumn(2).setPreferredWidth(100);   // Author column width
        columnModel.getColumn(3).setPreferredWidth(100);   // Type column width
        columnModel.getColumn(4).setPreferredWidth(50);   // Total Book column width
        columnModel.getColumn(5).setPreferredWidth(50);   // Price column width
         
    }
    
    private void updateBookBorrowList(List<Integer> searchResults){
                String [] columnNames = {"ID", "Name", "Author", "Type", "Id Customer", "Price", "Date borrow", "Status"};
                Object[][] data = new Object[searchResults.size()][8];
                for (int i = 0; i < searchResults.size(); i++) {
                    int bookIndex = searchResults.get(i);
                    for (BookBorrow book : ManagementLibrary.bookBorrow) {
                        if ( book.getId() == bookIndex ) {
                            data[i][0] = book.getId();       // ID
                            data[i][1] = book.getName();     // Name
                            data[i][2] = book.getAuthor();   // Author
                            data[i][3] = book.getType();     // Type
                            data[i][4] = book.getIdCustomer();   // Total Book
                            data[i][5] = book.getPrice();    // Price
                            data[i][6] = book.getDateBorrow(); //Date borrow                      
                            data[i][7] = book.getStatus(); //Status
                        }
                    }
                }

                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                bookTable.setModel(tableModel);

                TableColumnModel columnModel = bookTable.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(20);    // ID column width
                columnModel.getColumn(1).setPreferredWidth(300);   // Name column width
                columnModel.getColumn(2).setPreferredWidth(100);   // Author column width
                columnModel.getColumn(3).setPreferredWidth(100);   // Type column width
                columnModel.getColumn(4).setPreferredWidth(50);   // Total Book column width
                columnModel.getColumn(5).setPreferredWidth(50);   // Price column width
                columnModel.getColumn(6).setPreferredWidth(50);   // Date borrow
                columnModel.getColumn(7).setPreferredWidth(50);   // Status    
    }
    // Method to return the selected book ID
    public void lendBook(int bookId, int idCustomer) {
        int confirmed = JOptionPane.showConfirmDialog(
            this,
            "Are you want to lend this book?",
            "Are you confirm?",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmed == JOptionPane.YES_OPTION) {
            Book b1 = null;

            for (Book b : ManagementLibrary.book){
                if (b.getId() == bookId) {
                    b1 = b;
                    break;
                }
            }

            if (b1 != null && MethodController.remainingBookToBorrow("id", Integer.toString(bookId)) > 0) { // Kiểm tra xem b1 có khác null hay không
                if (ManagementLibrary.logged.get(0).getLevelUser() == 1) idCustomer = ManagementLibrary.logged.get(0).getId();
                ManagementLibrary.bookBorrow.add(new BookBorrow(bookId, b1.getName(), b1.getAuthor(),b1.getType(), b1.getNumber(), b1.getPrice(), idCustomer, LocalDate.now(),false));
                JOptionPane.showMessageDialog(this, "Lending successful.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to lend this book.");
            }
        }
    }     
    public static void returnBook(int bookId, int idCustomer) {
        int confirmed = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to return this book?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmed == JOptionPane.YES_OPTION) {
            int location = -1;
            if (ManagementLibrary.logged.get(0).getLevelUser() == 1) {
                idCustomer = ManagementLibrary.logged.get(0).getId();
            }

            for (int i = 0; i < ManagementLibrary.bookBorrow.size(); i++) {
                if (ManagementLibrary.bookBorrow.get(i).getId() == bookId && ManagementLibrary.bookBorrow.get(i).getIdCustomer() == idCustomer) {
                    location = i;
                    break;
                }
            }

            if (location != -1) { 
                ManagementLibrary.bookBorrow.remove(location);
                JOptionPane.showMessageDialog(null, "Return successful.");
            } else {
                JOptionPane.showMessageDialog(null, "You didn't borrow this book.");
            }
        }
    }
  
    //Method about Book
    public void addBook(Book b){
        int confirmed = JOptionPane.showConfirmDialog(
            this,
            "Are you want to add this book?",
            "Are you confirm?",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmed == JOptionPane.YES_OPTION) {
            if (b != null){
                ManagementLibrary.book.add(b);
                JOptionPane.showMessageDialog(this, "<html><font color='green'>✔</font> Successfully updated this book</html>");
            } else JOptionPane.showMessageDialog(this, "<html><font color='red'>❌</font> Failed to add this book. Please check again infomation book. </html>");     
        }
    }
    public boolean editBook(int bookId, Book b) {
        int confirmed = JOptionPane.showConfirmDialog(
            this,
            "Are you want to save this book?",
            "Are you confirm?",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmed == JOptionPane.YES_OPTION) {
            int location = -1;
            for (int i = 0; i < ManagementLibrary.book.size(); i++) {
                Book be = ManagementLibrary.book.get(i);
                if (be.getId() == bookId) {
                    location = i;
                    break;
                }
            }

            if (location != -1 && b != null) {
                ManagementLibrary.book.set(location, b);
                JOptionPane.showMessageDialog(this, "<html><font color='green'>✔</font> Successfully updated this book</html>");
            } else {
                JOptionPane.showMessageDialog(this, "<html><font color='red'>❌</font> Failed to update</html>");
            }
            return true;
        }
        return false;
    }
    public void deleteBook(int bookId){
        int confirmed = JOptionPane.showConfirmDialog(
            this,
            "Are you want to delete this book?",
            "Are you confirm?",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmed == JOptionPane.YES_OPTION) {
            int location = 0;           
            for (int i = 0; i < ManagementLibrary.book.size();i++){
                if (ManagementLibrary.book.get(i).getId() == bookId) {
                    location = i;
                    break;
                }
            }

            if (location != 0) { 
                ManagementLibrary.book.remove(location);
                JOptionPane.showMessageDialog(this, "Delete successful.");           
            } else {
                JOptionPane.showMessageDialog(this, "Don't have this book.");
            }
        }
    }
        
    //Jpanel After select
    private JPanel createBookDetailsPanel(int idBook, int idCus) {
        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new BorderLayout());

        // Tạo bảng dữ liệu Excel cho thông tin sách
        String[] columnNames = {"ID", "Name", "Author", "Type", "Remaining", "Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Book book : ManagementLibrary.book) {
            if (book.getId() == idBook) {
                Object[] rowData = {book.getId(), book.getName(), book.getAuthor(), book.getType(), MethodController.remainingBookToBorrow("id", Integer.toString(idBook)), book.getPrice()};
                tableModel.addRow(rowData);
            }
        }

        JTable bookTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(bookTable);
        bookPanel.add(scrollPane, BorderLayout.CENTER);

        // Tạo panel mới để chứa các nút "Lend Book" và "Return Book"
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Thêm nút "Lend Book"
        JButton lendButton = new JButton("Lend Book");
        lendButton.addActionListener((ActionEvent e) -> {
            lendBook(idBook, idCus);
        });
        buttonPanel.add(lendButton);

        // Thêm nút "Return Book"
        JButton returnButton = new JButton("Return Book");
        returnButton.addActionListener((ActionEvent e) -> {
            returnBook(idBook, idCus);
        });
        buttonPanel.add(returnButton);

        // Thêm panel chứa các nút vào bookPanel
        bookPanel.add(buttonPanel, BorderLayout.SOUTH);

        return bookPanel;
    }
    private JPanel createBookDetailsPanelForManager(int idBook, int idCus, String what) {
        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new BorderLayout());     
        String[] columnNames;
        switch (what) {
            case "lend":
                columnNames = new String[]{"ID", "Name", "Author", "Type", "Total Book", "Price"};
                break;
            case "return":
                columnNames = new String[]{"ID", "Name", "Author", "Type", "Id Customer", "Price", "Date Borrow", "Status"};
                break;
            case "search" :
                columnNames = new String[]{"ID", "Name", "Author", "Type", "Total Book", "Price"};
                break;
            default:
                return bookPanel; // Return an empty panel if 'what' is neither "lend" nor "return"
        }

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        tableModel.setRowCount(0);

        if ("lend".equals(what) || "search".equals(what)){
            for (Book book : ManagementLibrary.book) {
                if (book.getId() == idBook) {
                    Object[] rowData = {book.getId(), book.getName(), book.getAuthor(), book.getType(), MethodController.remainingBookToBorrow("id", Integer.toString(idBook)), book.getPrice()};
                    tableModel.addRow(rowData);
                }
            }
        }

        if ("return".equals(what)) {
            for (BookBorrow book : ManagementLibrary.bookBorrow) {
                if (book.getId() == idBook && book.getIdCustomer() == idCus) {
                    Object[] rowData = {book.getId(), book.getName(), book.getAuthor(), book.getType(), book.getIdCustomer(), book.getPrice(), book.getDateBorrow(), book.getStatus()};
                    tableModel.addRow(rowData);
                }
            }
        }

        JTable bookTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(bookTable);
        bookPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        
        JButton lendButton = new JButton("Lend Book");
        lendButton.addActionListener((ActionEvent e) -> {
            lendBook(idBook, idCus);
        });
        buttonPanel.add(lendButton);

        // Thêm nút "Return Book"
        JButton returnButton = new JButton("Return Book");
        returnButton.addActionListener((ActionEvent e) -> {
            returnBook(idBook, idCus);
        });
        
        buttonPanel.add(returnButton);
        JButton editButton = new JButton("Edit Information Book");
        editButton.addActionListener((ActionEvent e) -> {
            showTableToEdit(idBook,"edit");
        });
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete Book");
        deleteButton.addActionListener((ActionEvent e) -> {
            deleteBook(idBook);
        });
        buttonPanel.add(deleteButton);
        
        if ("edit".equals(what) || "search".equals(what)) {
            lendButton.setEnabled(false);
            returnButton.setEnabled(false);
            lendButton.setToolTipText("This section does not support this feature.");
            returnButton.setToolTipText("This section does not support this feature.");
        } 
        if ("lend".equals(what) ) {
            returnButton.setEnabled(false);
            editButton.setEnabled(false);   
            deleteButton.setEnabled(false);
            returnButton.setToolTipText("This section does not support this feature.");
            editButton.setToolTipText("This section does not support this feature.");
            deleteButton.setToolTipText("This section does not support this feature.");
        }
        
        if ("return".equals(what) ) {
            lendButton.setEnabled(false);
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            lendButton.setToolTipText("This section does not support this feature.");
            editButton.setToolTipText("This section does not support this feature.");
            deleteButton.setToolTipText("This section does not support this feature.");
        }
        

        // Thêm panel chứa các nút vào bookPanel
        bookPanel.add(buttonPanel, BorderLayout.SOUTH);

        return bookPanel;
    }
    private JPanel createEditableBookDetailsPanel(int idBook,String s) {
        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new BorderLayout());        
        // Tạo bảng dữ liệu Excel cho thông tin sách
        String[] columnNames = {"ID", "Name", "Author", "Type", "Total Book", "Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Tìm kiếm sách với ID tương ứng và thêm vào bảng dữ liệu
        for (Book book : ManagementLibrary.book) {
            if (book.getId() == idBook && "edit".equals(s)) {
                Object[] rowData = {book.getId(), book.getName(), book.getAuthor(), book.getType(), book.getNumber(), book.getPrice()};
                tableModel.addRow(rowData);
            }             
        }
        
        if ("add".equals(s)) {
                Object[] rowData = {"", "", "", "", "", ""};
                tableModel.addRow(rowData);
            }     
        

        JTable bookTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(bookTable);
        bookPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((ActionEvent e) -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow != -1) {
                Object id = bookTable.getValueAt(selectedRow, 0);
                int bookId = Integer.parseInt(id.toString());               
                String name = bookTable.getValueAt(selectedRow, 1).toString();
                String author = bookTable.getValueAt(selectedRow, 2).toString();
                String type = bookTable.getValueAt(selectedRow, 3).toString();
                int totalBook = Integer.parseInt(bookTable.getValueAt(selectedRow, 4).toString());
                int price = Integer.parseInt(bookTable.getValueAt(selectedRow, 5).toString());

                // Kiểm tra xem ID đã tồn tại hay chưa
                if (ValidateForSwing.isDuplicateIdBook(ManagementLibrary.book,bookId,name)) {
                    JOptionPane.showMessageDialog(null, "Id is not vaild or have in database.");
                    return;
                }

                // Kiểm tra tính hợp lệ của tên sách
                if (!ValidateForSwing.isName(name)) {
                    JOptionPane.showMessageDialog(null, "Name of the book is not valid.");
                    return;
                }
                if (!ValidateForSwing.isName(author)) {
                    JOptionPane.showMessageDialog(null, "Author is not valid.");
                    return;
                }
                if (!ValidateForSwing.isName(type)) {
                    JOptionPane.showMessageDialog(null, "Type is not valid.");
                    return;
                }

                Book b = new Book(bookId, name, author, type, totalBook, price); 
                if ("edit".equals(s)) {
                    editBook(bookId, b);
                } else {
                    addBook(b);
                }
            }
        });

                   
        buttonPanel.add(saveButton);

        // Thêm panel chứa nút vào bookPanel
        bookPanel.add(buttonPanel, BorderLayout.SOUTH);
        return bookPanel;
    }
    
    //Create Panels
    private void showBookDetails(int idBook, int idCus, String s) {
        bookDetailsPanel = createBookDetailsPanel(idBook, idCus);
        bookDetailsPanelForManager = createBookDetailsPanelForManager(idBook, idCus, s);
        JDialog dialog = new JDialog();
        dialog.setTitle("Check your book");
        dialog.setModal(true); 
        dialog.setSize(800, 400);
        if (ManagementLibrary.logged.get(0).getLevelUser() == 1){
            dialog.getContentPane().add(bookDetailsPanel);
        } else dialog.getContentPane().add(bookDetailsPanelForManager);       
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.dispose();
        
    }   
    private void showTableToEdit(int idBook,String s) {
        createBookDetailsPanel = createEditableBookDetailsPanel(idBook,s);
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setSize(800, 400);
        dialog.getContentPane().add(createBookDetailsPanel);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }  
    public static JPanel createPanel(int idCustomer,String s) {
        return new SearchBookUI(idCustomer,s);
    }  
}
