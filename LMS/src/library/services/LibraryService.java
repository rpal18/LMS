package library.services;

import library.model.Book;
import library.model.Student;
import library.util.ActionResult;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.*;

// this class is responsible to handle the services like returning books , borrowing books etc.
public class LibraryService {
    private final  BookService bookService ;

    private static final int finePerDay = 20 ;

    private Map<String , BorrowRecord > borrowRecordMap = new HashMap<>();

    public LibraryService(BookService bookService) {
        this.bookService = bookService;
    }

    //inner class
    private static class BorrowRecord {
        private final Student borrower ;
        private final LocalDate dateOfIssue;
        private final LocalDate dueDate ;

        public BorrowRecord(Student borrower, LocalDate dateOfIssue, LocalDate dueDate) {
            this.borrower = borrower;
            this.dateOfIssue = dateOfIssue;
            this.dueDate = dueDate;
        }

        public LocalDate getDueDate() {
            return dueDate;
        }
    }

    //borrow through library (also tracking the record of everything )

    public ActionResult<Book> borrow(String isbn , Student s){
        if(borrowRecordMap.containsKey(isbn)){
            return new ActionResult<>(false , "book has already borrowed :" , null);

        }

        ActionResult<Book> result = bookService.borrowBook(isbn);
        if(result.isSuccess()){
           borrowRecordMap.put(isbn , new BorrowRecord(s,LocalDate.now() , LocalDate.now().plusDays(5)));
        }
     return result ;
    }

    //return book

    public ActionResult<Book> returnBook(String isbn){

        if(!borrowRecordMap.containsKey(isbn)){
            return new ActionResult<>(false , " no such book found : " , null);
        }
        borrowRecordMap.remove(isbn);
        return bookService.returnBook(isbn) ;

    }

    //check overdue books
    public List<String> getOverDueBooks(){
        List<String> overDueBooks = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for(Map.Entry<String  , BorrowRecord> entry : borrowRecordMap.entrySet()){
           if(today.isAfter(entry.getValue().dueDate)){
               overDueBooks.add(entry.getKey() + " Borrowed by : " + entry.getValue().borrower.getUserName());
           }
        }
        return Collections.unmodifiableList(overDueBooks);
    }


    // get who borrowed the specific books
    public String getBorrowerName ( String isbn) {
        BorrowRecord record  = borrowRecordMap.get(isbn);
        if(record == null){
            return null;
        }else{
            return record.borrower.getUserName();
        }
    }

    // imposing fine
    // if student is not able to return book in five days then he will be able to pay the fine for 20 rs per day .
    //need to calculate due days first.(today - dueDate)

    public ActionResult<Integer> imposeFine ( String isbn) {
        LocalDate today = LocalDate.now();
        BorrowRecord record = borrowRecordMap.get(isbn);

        //defensive checking (to avoid null pointer exception )

        if(record == null ){
            return new ActionResult<>(false , " no book found to impose fine " , 0);
        }
        LocalDate dueDate = record.getDueDate();
        if (today.isAfter(dueDate)) {
            int period = (int)ChronoUnit.DAYS.between(dueDate , today) ;
            return new ActionResult<>(true, " fine imposed succesfully ", finePerDay * period);
        } else {
            return new ActionResult<>(false, " book is not eligible for impose fine", 0);
        }
    }

}
