package com.example.meropasal.models.user;

public class VerificationResponse {

    private boolean success;
    private String message;
    private String sid;


    public VerificationResponse(boolean success, String message){
    this.success = success;
    this.message = message;
    }

    public VerificationResponse(boolean success, String message, String sid){
        this.success = success;
        this.message = message;
        this.sid = sid;

    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getSId() {
        return sid;
    }
}
