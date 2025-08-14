package library.model;

public abstract class User {

    private String userName;
    private String userID ;

    public User(String userName  , String userId){
        this.userName = userName;
        this.userID = userID;
    }


    //getters

    public String getUserID(){
        return this.userID;
    }
    public String getUserName(){
        return this.userName;
    }

    //abstract method should be borrowbook and return book .
}
