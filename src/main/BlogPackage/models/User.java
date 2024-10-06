package main.BlogPackage.models;

public class User {
    private String id;
    private String email;
    private String name;
    private String password;

//    public User(){}
    // constructor
    public User(String id, String email, String name, String password){
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    //getters
    public String getEmail(){
        return  email;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    // setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name){
        this.name = name;
    }
}

