package com.example.login_register.Model;

public class Users {
    private  String Email;
    private  String username;
    private String imageURL;

    public Users(String email, String username, String imageURL) {
        Email = email;
        this.username = username;
        this.imageURL = imageURL;
    }

    public String getEmail() {
        return Email;
    }

    public String getUsername() {
        return username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
