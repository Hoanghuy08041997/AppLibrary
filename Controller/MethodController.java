package Controller;

import static Controller.ManagementLibrary.*;
import Model.Book;
import Model.BookBorrow;
import Model.Customer;
import java.util.ArrayList;
import java.util.List;

public class MethodController {

    //Method Backup Data
    public static void loadData() {
        account.addAll(IOReader.readFileAccount("/ListAccounts.txt"));
        customer.addAll(IOReader.readFileCustomer("/ListCustomer.txt"));
        book.addAll(IOReader.readFileBook("/ListBook.txt"));
        bookBorrow.addAll(IOReader.readFileBookBorrow("/ListBookBorrow.txt"));
    }  
    public static void saveData(){
        IOReader.saveFileAccount(ManagementLibrary.account, "/ListAccounts.txt");
        IOReader.saveFileCustomer(ManagementLibrary.customer, "/ListCustomer.txt"); 
        IOReader.saveFileBookBorrow(ManagementLibrary.bookBorrow, "/ListBookBorrow.txt");
        IOReader.saveFileBook(ManagementLibrary.book, "/ListBook.txt");
        
    }  
    public static void exit() {
        IOReader.saveFileAccount(ManagementLibrary.account, "./src/ListAccounts.txt");
        IOReader.saveFileCustomer(ManagementLibrary.customer, "./src/ListCustomer.txt");
        IOReader.saveFileBookBorrow(ManagementLibrary.bookBorrow, "./src/ListBookBorrow.txt");
        IOReader.saveFileBook(ManagementLibrary.book, "./src/ListBook.txt");
        System.exit(0);
    }
    //Veriry!
    
    public static List<Integer> searchAccount(String searchCriteria, String s) {
        List<Integer> matchingAccounts = new ArrayList<>();
        SearchPredicate<Customer> searchCriteriaByProperties = new SearchPredicate<>(searchCriteria, s);
        for (Customer customer : ManagementLibrary.customer) {
            if (searchCriteriaByProperties.test(customer)) {
                matchingAccounts.add(customer.getId());
            }
        }
        return matchingAccounts;
    }  
    public static int remainingBookToBorrow(String searchCriteria, String s) {
        int dem =0;
        SearchPredicate<BookBorrow> searchCriteriaByProperties = new SearchPredicate<>(searchCriteria, s);
        for (BookBorrow book : ManagementLibrary.bookBorrow) {
            if (searchCriteriaByProperties.test(book)) {
                dem++;
            }
        }  
        int remaining = 0;
        for (Book book : ManagementLibrary.book) {
            if (Integer.toString(book.getId()).equals(s)) {
                remaining = book.getNumber() - dem;
                break;
            }
        }   
        return remaining;
    }
    public static List<Integer> searchBook(String searchCriteria, String s) {
        List<Integer> matchingAccounts = new ArrayList<>();
        SearchPredicate<Book> searchCriteriaByProperties = new SearchPredicate<>(searchCriteria, s);
        for (Book b : ManagementLibrary.book) {
            if (searchCriteriaByProperties.test(b)) {
                matchingAccounts.add(b.getId());
            }
        }
        return matchingAccounts;
    }
    
    public static List<Integer> searchBookBorrow(String searchCriteria, String s) {
        List<Integer> matchingAccounts = new ArrayList<>();
        SearchPredicate<BookBorrow> searchCriteriaByProperties = new SearchPredicate<>(searchCriteria, s);
        for (BookBorrow b : ManagementLibrary.bookBorrow) {
            if (searchCriteriaByProperties.test(b)) {
                matchingAccounts.add(b.getId());
            }
        }
        return matchingAccounts;
    }

}
