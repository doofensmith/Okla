package com.mobile.okla.Model;

public class MRoom {

    private String keyroom;
    private String namaroom;
    private String passwordroom;
    private String chatterakhir;
    private int belumdibaca;

    public MRoom() {
        //empty constructor
    }

    public MRoom(String keyroom, String namaroom, String passwordroom, String chatterakhir, int belumdibaca) {
        this.keyroom = keyroom;
        this.namaroom = namaroom;
        this.passwordroom = passwordroom;
        this.chatterakhir = chatterakhir;
        this.belumdibaca = belumdibaca;
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
