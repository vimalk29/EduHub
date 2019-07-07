package com.eduhub.company.model;

public class MessagePOJO {
    private String message;
    private String date;
    private String time;
    private String senderId;
    private String imageurl;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public MessagePOJO(String message, String time, String date, String senderId){
        this.message = message;
        this.date = date;
        this.time = time;
        this.senderId = senderId;
    }

    public MessagePOJO(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
