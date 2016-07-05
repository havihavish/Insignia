package com.example.android.insignia2k16.chat.model;

/**
 * Created by surya on 04-07-2016.
 */
public class Users {

    String userName;
    String email;

    public Users() {
    }

    public Users(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
