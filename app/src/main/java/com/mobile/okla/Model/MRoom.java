package com.mobile.okla.Model;

public class MRoom {

    private String keyroom;
    private String namaroom;
    private String passwordroom;
    private String chatterakhir;
    private int belumdibaca;
    private String admin;
    private String anggota1;
    private String anggota2;

    public MRoom() {
        //empty constructor
    }

    public MRoom(String keyroom, String namaroom, String passwordroom, String chatterakhir, int belumdibaca, String admin, String anggota1, String anggota2) {
        this.keyroom = keyroom;
        this.namaroom = namaroom;
        this.passwordroom = passwordroom;
        this.chatterakhir = chatterakhir;
        this.belumdibaca = belumdibaca;
        this.admin = admin;
        this.anggota1 = anggota1;
        this.anggota2 = anggota2;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAnggota1() {
        return anggota1;
    }

    public void setAnggota1(String anggota1) {
        this.anggota1 = anggota1;
    }

    public String getAnggota2() {
        return anggota2;
    }

    public void setAnggota2(String anggota2) {
        this.anggota2 = anggota2;
    }

    public String getChatterakhir() {
        return chatterakhir;
    }

    public void setChatterakhir(String chatterakhir) {
        this.chatterakhir = chatterakhir;
    }

    public int getBelumdibaca() {
        return belumdibaca;
    }

    public void setBelumdibaca(int belumdibaca) {
        this.belumdibaca = belumdibaca;
    }

    public String getKeyroom() {
        return keyroom;
    }

    public void setKeyroom(String keyroom) {
        this.keyroom = keyroom;
    }

    public String getNamaroom() {
        return namaroom;
    }

    public void setNamaroom(String namaroom) {
        this.namaroom = namaroom;
    }

    public String getPasswordroom() {
        return passwordroom;
    }

    public void setPasswordroom(String passwordroom) {
        this.passwordroom = passwordroom;
    }
}
