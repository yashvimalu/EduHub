package com.example.gyaandaan;

public class MemberDetails {
    private String name,email_id,pwd,phone;


    private MemberDetails(String name, String email_id,String password, String phone) {
        this.name = name;
        this.email_id = email_id;
        this.pwd = password;
        this.phone = phone;
    }
    public MemberDetails(String s, String fullname, String email, String password, String phoneNumber){

    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}