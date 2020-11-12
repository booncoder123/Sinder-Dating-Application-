package com.example.login_register;

import com.google.firebase.firestore.SnapshotMetadata;

public class Contacts {
    public String fName, sex;

    public Contacts() {

    }

    public Contacts(String name, String sex) {
        this.fName = name;
        this.sex = sex;
//        this.imageURL = imageURL;
    }

    public String getName() {
        return fName;
    }

    public String getStatus() {
        return sex;
    }
}


