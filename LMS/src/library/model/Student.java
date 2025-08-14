package library.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends User{

    private int rollno ;
    private char section;

    private int standard;
    private int noOfBookIsued ;
    private List<Book> booksIssued  = new ArrayList<>();


    public Student(String userName, String userId) {
        super(userName, userId);

    }

    public Student(String userName, String userId, int rollno, char section, int standard, int noOfBookIsued) {
        super(userName, userId);
        this.rollno = rollno;
        this.section = section;
        this.standard = standard;
        this.noOfBookIsued = noOfBookIsued;
    }

    //getters

    public String getName(){
        return super.getUserName();
    }

    public int getRollno() {
        return rollno;
    }

    public char getSection() {
        return section;
    }

    public int getStandard() {
        return standard;
    }

    public int getNoOfBookIsued() {
        return noOfBookIsued;
    }



    public List<Book> getIssuedBook(){
        return booksIssued;
    }


    //utility methods
    //there can be two utility methods for now
    //1) add book

    public void addBook(Book book){
        booksIssued.add(book);
        noOfBookIsued++;
    }

    //2) remove book

    public void returnBook(Book book){
        booksIssued.remove(book);
        if(noOfBookIsued > 0){
            noOfBookIsued--;
        }
    }
}
