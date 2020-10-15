package library.entities;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Bug2AutomatedTest {
    //This is just a quick test to check the output of library.calculateoverduefine

    @Test
    public void checkForBug(){
        //Arrange

        //We first need a book, a library and a loan (with associated patron) to manage all of this
        Library lib = new Library(null,null,null); //The helpers are used as factories to create, we dont need them here
        Book book = new Book("Jane Doe","How To Read","HTR-001",1, IBook.BookState.ON_LOAN); //the is on loan since its associated with a loan here

        Patron pat = new Patron("Smith", "John", "John.Smith@email.com.au", 123456789, 1, 0, IPatron.PatronState.CAN_BORROW, new HashMap<Integer, ILoan>());
        Loan loan = new Loan(book,pat,Calendar.getInstance().getDueDate(-8), ILoan.LoanState.OVER_DUE,1);//make a loan that was due 8 days ago, theoretically producing an $8 fine

        //Act
        double result = lib.calculateOverDueFine(loan);

        //Assert
        double expected = 8.0f;
        assertEquals(expected,result);

    }
}
