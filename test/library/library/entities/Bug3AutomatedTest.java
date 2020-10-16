package library.entities;

import library.returnbook.ReturnBookControl;
import library.returnbook.ReturnBookUI;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Bug3AutomatedTest {
    //This is just a quick test to check the output of a patron returning a book

    @Test
    public void checkForBug(){
        //Arrange

        //We first need a book, a library and a loan (with associated patron) to manage all of this
        Library lib = new Library(null,null,null); //The helpers are used as factories to create, we dont need them here
        Book book = new Book("Jane Doe","How To Read","HTR-001",1, IBook.BookState.ON_LOAN); //the is on loan since its associated with a loan here

        Patron pat = new Patron("Smith", "John", "John.Smith@email.com.au", 123456789, 1, 0, IPatron.PatronState.CAN_BORROW, new HashMap<Integer, ILoan>());
        Loan loan = new Loan(book,pat,Calendar.getInstance().getDueDate(-1), ILoan.LoanState.OVER_DUE,1);//make a loan that was due yesterday

        //add the book to the catalog
        lib.catalog.put(book.getId(),book);
        //add the loan to the curren loans
        lib.currentLoans.put(book.getId(), loan);

        //we also now need a control class and a UI
        ReturnBookControl rbc = new ReturnBookControl(lib);
        ReturnBookUI rbi = new ReturnBookUI(rbc);

        //Act
        rbc.bookScanned(book.getId()); //scan the loaned book present in the test, this sets of the chain that returns the book
        double result = pat.getFinesPayable(); //shoudld be 0 since the control class shouldn't be incurring a fine and further in where it's calculated and incurred
        //has already been tested

        //Assert
        double expected = 0f; //1 dollar for 1 day overdue
        assertEquals(expected,result);

    }
}
