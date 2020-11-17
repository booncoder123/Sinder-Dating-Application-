package com.example.login_register.Model;

import java.util.HashMap;

public class Users {
//    private String email;
    private String fName;
    private String imageURL;
    private int phone;
    private String userid;
    private HashMap<String, String> friends;

    public Users() {

    }


    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

//    public String getImageURL() {
//        return imageURL;
//    }
//
//    public void setImageURL(String imageURL) {
//        this.imageURL = imageURL;
//    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userID) {
        this.userid = userID;
    }
}








