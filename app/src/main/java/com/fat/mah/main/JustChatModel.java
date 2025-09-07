package com.fat.mah.main;

public class JustChatModel {
    private String text;
    private String sender;
    private String reciever;
    private String photoUrl;

    public JustChatModel() {
    }

    public JustChatModel(String text, String sender,String reciever, String photoUrl) {
        this.text = text;
        this.sender = sender;
        this.reciever=reciever;
        this.photoUrl = photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public String getReciever() {
        return reciever;
    }


    public void setSender(String name) {
        this.sender = name;
    }

    public void setReciever(String name) {
        this.reciever = name;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


}
