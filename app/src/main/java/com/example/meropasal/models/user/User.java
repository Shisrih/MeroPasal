package com.example.meropasal.models.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {


    //Request
    private String _id;
    private String firstname;
    private String lastname;
    private String location;
    private String phone;
    private String email;
    private String type;
    private String password;
    private List<ShippingAddress> shippingAddress;

    //Response
    private boolean success;
    private String message;

    @SerializedName("token")
    private String token;

    //For Profile Response
    public User(boolean success, String message, String firstname, String lastname, String location, String phone, String email, String type){
      this.success = success;
      this.message = message;
        this.firstname = firstname;
        this.lastname = lastname;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.type = type;
    }

    //For getting shipping Address
    public User(boolean success, String message, List<ShippingAddress> shippingAddress){
        this.success = success;
        this.message = message;
        this.shippingAddress = shippingAddress;
    }

    public User(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public User(String firstname, String lastname, String location, String phone, String email, String type){
        this.firstname = firstname;
        this.lastname = lastname;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.type = type;
    }

    //For Registration Request
    public User( String firstname, String lastname, String location, String phone, String email, String type, String password){

        this.firstname = firstname;
        this.lastname = lastname;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.type = type;
        this.password = password;

    }

    //For Auth Response
    public User(boolean success, String message, String token, String _id){
        this.success = success;
        this.message = message;
        this.token = token;
        this._id = _id;
    }

    //For Login Request
    public User (String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getType() {
        return type;
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

    public List<ShippingAddress> getShippingAddress() {
        return shippingAddress;
    }
}

