package com.example.lab3;

public class Contact {
    protected int id;
    protected String fullname;
    protected String phone;
    protected boolean status;
    protected String uriImage;

    public Contact(int id, String fullname, String phone, boolean status) {
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
        this.status = status;
        this.uriImage = "/storage/emulated/0/Download/pexels-ánh-đặng-20454066.jpg";
    }

    public Contact(int id, String fullname, String phone, boolean status, String uriImage) {
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
        this.status = status;
        this.uriImage = uriImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }
}
