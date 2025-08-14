package library.services;


//Key responsibilities for BookService:
//
//        Search for books (by title, author, ISBN)
//
//        Borrow a book (if available)
//
//        Return a book (and update availability)
//
//        Track due dates for borrowed books (say , due date to borrow a book is 5 days ) , I think ,it is the work of librarian and should be done by librarian
//         that's why i want to do it in library services class .

//
//        Check overdue books(in library service class )
//


import library.model.Book;
import library.util.ActionResult;

import java.util.*;

public class BookService {

    //need to store books somewhere so that we can find it by searching
    private final Map<String, Book> books = new HashMap<>(); // (ISBN -> Book)

    //adding books
    public ActionResult<Book> addBook(Book book) {
        if (book == null) {
            return new ActionResult<>(false, " book cannot be null", null);
        }

        if (books.containsKey(book.getISBN())) {
            return new ActionResult<>(false, " book with this isbn already exists", books.get(book.getISBN()));
        }

        books.put(book.getISBN(), book);
        return new ActionResult<>(true, "book added successfuly  : ", book);
    }

    //removing books with isbn

    public ActionResult<Book> removeBook(String isbn) {
        Book removed = books.remove(isbn);

        if (removed == null) {
            return new ActionResult<>(false, "cannot remove null ", null);
        }

        return new ActionResult<>(true, " book has been removed ", removed);
    }

    //borrow a book

    public ActionResult<Book> borrowBook(String isbn) {
        Book book = books.get(isbn);
        if (book == null) {
            return new ActionResult<>(false, " can not borrow null book", null);

        }
        if (!book.isAvailable()) {
            return new ActionResult<>(false, "book has already borrowed ", book);

        }

        book.setAvailable(false);

        return new ActionResult<>(true, "successfully borrowed ", book);
    }

    //returning a book

    public ActionResult<Book> returnBook(String isbn) {
        Book book = books.get(isbn);
        if (book == null) {
            return new ActionResult<>(false, " book cannot be null", null);

        }
        if (book.isAvailable()) {
            return new ActionResult<>(false, " book was not borrowed ", book);
        }


        book.setAvailable(true);

        return new ActionResult<>(true , "book has succesfull returned" , book);
    }
 // get all the books(read only )
// it should be unmodifiable list
    public List<Book> allBook(){
        return Collections.unmodifiableList(new ArrayList<>(books.values()));
    }

    //get only available book

    public List<Book> allAvailableBook(){
        List<Book> available  = new ArrayList<>();
        for(Book b : books.values()){
            if(b.isAvailable()){
                available.add(b);
            }
        }
        return Collections.unmodifiableList(available);
    }

    //search books(I know that there would not be any usecase of it as we have used ActionResult class to store state of object)
    //by title(to search by title we need to store the book mapped with title )

    //in this class we can search by isbn only because each book is mapped with isbn.

    public Book searchByIsbn(String isbn){
        return books.get(isbn);
    }



}
