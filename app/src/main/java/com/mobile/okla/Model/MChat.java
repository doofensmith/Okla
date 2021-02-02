package com.mobile.okla.Model;

public class MChat {

    private String keychat;
    private String namapengirim;
    private String pesan;
    private String waktu;

    public MChat() {

    }

    public MChat(String keychat, String namapengirim, String pesan, String waktu) {
        this.keychat = keychat;
        this.namapengirim = namapengirim;
        this.pesan = pesan;
        this.waktu = waktu;
    }

    public String getKeychat() {
        return keychat;
    }

    public void setKeychat(String keychat) {
        this.keychat = keychat;
    }

    public String getNamapengirim() {
        return namapengirim;
    }

    public void setNamapengirim(String namapengirim) {
        this.namapengirim = namapengirim;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
