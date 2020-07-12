package com.example.meropasal.models;

public class User {


    //Request
    private String _id;
    private String firstname;
    private String lastname;
    private String location;
    private String phone;
    private String email;
    private String password;

    //Response
    private boolean success;
    private String message;
    private String token;

    public User(String firstname, String lastname, String location, String phone, String email, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public User(String id, String firstname, String lastname, String location, String phone, String email, String password){
        this._id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.password = password;

    }

    public User(boolean success, String message, String token){
        this.success = success;
        this.message = message;
        this.token = token;
    }
    public User (String email, String password){
        this.email = email;
        this.password = password;
    }



    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String get_id() {
        return _id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}

