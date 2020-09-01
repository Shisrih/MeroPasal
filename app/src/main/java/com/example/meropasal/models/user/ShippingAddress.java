package com.example.meropasal.models.user;

import java.io.Serializable;

public class ShippingAddress implements Serializable {
    private String fullname;
    private long phonenumber;
    private String city;
    private String zone;
    private String address;
    private boolean selected;


    public ShippingAddress(){

    }

    public ShippingAddress(String fullname, long phonenumber, String address, String city, String zone) {
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.city = city;
        this.zone = zone;
        this.address = address;
    }


    public String getFullname() {
        return fullname;
    }

    public long getPhonenumber() {
        return phonenumber;
    }

    public String getCity() {
        return city;
    }

    public String getZone() {
        return zone;
    }

    public String getAddress() {
        return address;
    }


    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }
}
