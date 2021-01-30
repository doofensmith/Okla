package com.mobile.okla.Model;

public class MUser {
    private String email;
    private String password;
    private String tipe;

    public MUser(String email, String password, String tipe) {
        this.email = email;
        this.password = password;
        this.tipe = tipe;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
