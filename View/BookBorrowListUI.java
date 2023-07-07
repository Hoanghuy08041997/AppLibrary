package View;

import Controller.ManagementLibrary;
import Model.BookBorrow;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookBorrowListUI extends JPanel {
    
    public BookBorrowListUI(ArrayList<BookBorrow> books) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        String[] columnNames;
        if (ManagementLibrary.logged.get(0).getLevelUser() == 1){
            columnNames = new String[]{"ID", "Name", "Author", "Type" ,"Price","Date lend","Status return"};
        } else {
            columnNames = new String[]{"ID", "Name", "Author", "Type" ,"Price","Date lend","Status return","Id Customer" };
        }

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        if (ManagementLibrary.logged.get(0).getLevelUser() == 1){
            for (BookBorrow bb : books) {
                Object[] rowData = {bb.getId(), bb.getName(), bb.getAuthor(),bb.getType(),
                                     bb.getPrice(),bb.getDateBorrow(),bb.getStatus()};
                tableModel.addRow(rowData);
            }
        }

        if (ManagementLibrary.logged.get(0).getLevelUser() == 2) {
            for (BookBorrow b1 : books) {
                Object[] rowData = {b1.getId(), b1.getName(), b1.getAuthor(), b1.getType(),
                                    b1.getPrice(), b1.getDateBorrow(), b1.getStatus(), b1.getIdCustomer()};
                tableModel.addRow(rowData);
            }
        }



        // Tạo JTable với DefaultTableModel
        JTable bookTable = new JTable(tableModel);

        // Đặt kích thước ưu tiên cho các cột
        bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        bookTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Name
        bookTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Author
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(80); //Type
        bookTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Price 
        bookTable.getColumnModel().getColumn(5).setPreferredWidth(80); //Date lend
        bookTable.getColumnModel().getColumn(6).setPreferredWidth(80); //Status return
        if (ManagementLibrary.logged.get(0).getLevelUser() == 2){
            bookTable.getColumnModel().getColumn(7).setPreferredWidth(80); //idCustomer
        }      
        JScrollPane scrollPane = new JScrollPane(bookTable);

        JPanel buttonPanel = new JPanel();

        JButton returnButton = new JButton("Return Book");
        returnButton.addActionListener((ActionEvent e) -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow != -1) {
                int accountId = (int) bookTable.getValueAt(selectedRow, 7);
                int bookId = (int) bookTable.getValueAt(selectedRow, 0);
                SearchBookUI.returnBook(bookId,accountId);
            }
        });
        buttonPanel.add(returnButton);

        // Tạo nút Search BorrowBook
        JButton searchButton = new JButton("Search BorrowBook");
        searchButton.addActionListener((ActionEvent e)-> {
            JOptionPane.showMessageDialog(null, "Return to Search Customer to do it easy." );
        });
        buttonPanel.add(searchButton);

        

        scrollPane.setPreferredSize(new Dimension(800, 550));

        add(scrollPane);
        add(buttonPanel);
    }
}