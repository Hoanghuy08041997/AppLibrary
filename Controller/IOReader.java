package Controller;

import Model.Account;
import Model.Book;
import Model.BookBorrow;
import Model.Customer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class IOReader {

    //IO Account
    public static boolean saveFileAccount(ArrayList<Account> stdList, String path) {
        try {
            try (OutputStream outputStream = new FileOutputStream(path);
                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                 BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
                for (Account t : stdList) {
                    String line = t.getUsername() + "," + t.getPassword() + "," + t.getLevel() + "," + t.getId();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
    public static ArrayList<Account> readFileAccount(String path) {
        ArrayList<Account> accountList = new ArrayList<>();

        try (InputStream inputStream = IOReader.class.getResourceAsStream(path);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");
                
                if (fields.length == 4) { // Check the number of fields  
                    boolean hasNull = false;
                    for (String field : fields) {
                        if (field.trim().isEmpty()) {
                            hasNull = true;
                            break;
                        }
                    }

                    if (!hasNull) {
                        String username = fields[0].trim();
                        String password = fields[1].trim();
                        int level = Integer.parseInt(fields[2].trim());
                        int id = Integer.parseInt(fields[3].trim());
                        Account account = new Account(username, password, level, id);
                        accountList.add(account);
                    }
                } else {
                    System.out.println("Invalid number of fields: " + line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi dùng file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return accountList;
    }

    //IO Customer
    public static boolean saveFileCustomer(ArrayList<Customer> stdList, String path) {
        try {
            try (OutputStream outputStream = new FileOutputStream(path);
                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                 BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
                for (Customer t : stdList) {
                    String line = t.getId() + "," + t.getName() + "," + t.getEmail() + "," + t.getPhone() + "," + t.getBirthday() + "," + t.getLevelUser();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
    public static ArrayList<Customer> readFileCustomer(String path) {
        ArrayList<Customer> customerList = new ArrayList<>();

        try (InputStream inputStream = IOReader.class.getResourceAsStream(path);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length == 6) { // Check the number of fields
                    boolean hasNull = false;
                    for (String field : fields) {
                        if (field.trim().isEmpty()) {
                            hasNull = true;
                            break;
                        }
                    }

                    if (!hasNull) {
                        int id = Integer.parseInt(fields[0].trim());
                        String name = fields[1].trim();
                        String email = fields[2].trim();
                        String phone = fields[3].trim();
                        LocalDate date = Validate.parseDate(fields[4].trim());
                        int level = Integer.parseInt(fields[5].trim());
                        customerList.add(new Customer(id, name, email, phone, date, level));
                    } else {
                        System.out.println("Invalid number of fields: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return customerList;
    }

    //IO Book
    public static ArrayList<Book> readFileBook(String path) {
        ArrayList<Book> bookList = new ArrayList<>();

        try (InputStream inputStream = IOReader.class.getResourceAsStream(path);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length == 6) { // Check the number of fields
                    boolean hasNull = false;
                    for (String field : fields) {
                        if (field.trim().isEmpty()) {
                            hasNull = true;
                            break;
                        }
                    }

                    if (!hasNull) {
                        try {
                            int id = Integer.parseInt(fields[0].trim());
                            String name = fields[1].trim();
                            String author = fields[2].trim();
                            String type = fields[3].trim();
                            int number = Integer.parseInt(fields[4].trim());
                            int price = Integer.parseInt(fields[5].trim());
                            bookList.add(new Book(id, name, author, type, number, price));
                        } catch (NumberFormatException e) {
                            System.out.println("Error parsing numeric values: " + line);
                        }
                    } else {
                        System.out.println("Invalid number of fields or empty fields: " + line);
                    }
                } else {
                    System.out.println("Invalid number of fields: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return bookList;
    }
    public static boolean saveFileBook(ArrayList<Book> stdList, String path) {
        try {
            try (OutputStream outputStream = new FileOutputStream(path);
                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                 BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
                for (Book t : stdList) {
                    String line = t.getId() + "," + t.getName() + "," + t.getAuthor() + "," + t.getType() + "," + t.getNumber() + "," + t.getPrice();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    //IO BorrowBook
    public static boolean saveFileBookBorrow(ArrayList<BookBorrow> stdList, String path) {
    try {
        try (OutputStream outputStream = new FileOutputStream(path);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
            for (BookBorrow t : stdList) {
                String line = t.getId() + "," + t.getName() + "," + t.getAuthor() + "," + t.getType() + "," + t.getNumber() + "," + t.getPrice() + "," + t.getIdCustomer() + "," + t.getDateBorrow() + "," + t.getStatus();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        }
        return true;
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
    return false;
}
    public static ArrayList<BookBorrow> readFileBookBorrow(String path) {
        ArrayList<BookBorrow> bb = new ArrayList<>();

        try (InputStream inputStream = IOReader.class.getResourceAsStream(path);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length == 9) { // Check the number of fields
                    try {
                        int id = Integer.parseInt(fields[0].trim());
                        String name = fields[1].trim();
                        String author = fields[2].trim();
                        String type = fields[3].trim();
                        int number = Integer.parseInt(fields[4].trim());
                        int price = Integer.parseInt(fields[5].trim());
                        int idc = Integer.parseInt(fields[6].trim());
                        LocalDate date = Validate.parseDate(fields[7].trim());
                        boolean status = Boolean.parseBoolean(fields[8].trim());
                        bb.add(new BookBorrow(id, name, author, type, number, price, idc, date, status));
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing numeric values: " + line);
                    } catch (DateTimeParseException e) {
                        System.out.println("Error parsing date: " + line);
                    }
                } else {
                    System.out.println("Invalid number of fields: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return bb;
    }
}