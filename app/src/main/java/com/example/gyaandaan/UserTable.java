package com.example.gyaandaan;

public class UserTable {
    private String fullname, tablename;

    private UserTable (String full_name, String tabl_ename){
        this.fullname = full_name;
        this.tablename = tabl_ename;

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
}