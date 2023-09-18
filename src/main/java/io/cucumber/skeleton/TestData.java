package io.cucumber.skeleton;

public class TestData {
    String username;
    String password;
    String searchTerm;
    public TestData(String username, String password, String searchTerm){
        this.username=username;
        this.password=password;
        this.searchTerm=searchTerm;
    }
    public String toString(){
        return "Username: " + username + ",\nPassword: " + password + ",\nSearchTerm: " + searchTerm; 
    }
    
}
