package com.mobile.okla.Model;

public class MUser {
    private String email;
    private String password;
    private String namapengguna;
    private String nohp;
    private String tipe;
    private String alamat;
    private String jeniskelamin;

    public MUser() {

    }

    public MUser(String email, String password, String namapengguna, String nohp, String tipe, String alamat, String jeniskelamin) {
        this.email = email;
        this.password = password;
        this.namapengguna = namapengguna;
        this.nohp = nohp;
        this.tipe = tipe;
        this.alamat = alamat;
        this.jeniskelamin = jeniskelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJeniskelamin() {
        return jeniskelamin;
    }

    public void setJeniskelamin(String jeniskelamin) {
        this.jeniskelamin = jeniskelamin;
    }

    public String getNamapengguna() {
        return namapengguna;
    }

    public void setNamapengguna(String namapengguna) {
        this.namapengguna = namapengguna;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
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
