package library.model;

public class Book {
    private  String title ;
    private  String ISBN ;

    private  String author;
    private boolean isAvailable = true;

  public  Book(String bookName , String isbn , String author ) {
      this.title = bookName;
      this.ISBN = isbn;
      this.author =author ;

  }

  public void setAvailable(boolean available){
      this.isAvailable = available;
  }

  //getters
    public String getTitle(){
      return title;
    }


    public String getISBN(){
      return ISBN;
    }

    public String getAuthor(){
      return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }



    @Override
    public String toString(){
      return  "Book{title='" + title + "', isbn='" + ISBN + "', author='" + author + "', available=" + isAvailable + "}";
    }

}
