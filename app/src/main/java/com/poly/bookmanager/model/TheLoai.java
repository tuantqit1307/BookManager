package com.poly.bookmanager.model;

public class TheLoai {
    int idtheloai;
    String tentheloai;

    public TheLoai() {
    }

    public TheLoai(String tentheloai) {
        this.tentheloai = tentheloai;
    }

    public TheLoai(int idtheloai, String tentheloai){
        this.idtheloai = idtheloai;
        this.tentheloai = tentheloai;
    }

    public int getIdtheloai() {
        return idtheloai;
    }

    public void setIdtheloai(int idtheloai) {
        this.idtheloai = idtheloai;
    }

    public String getTentheloai() {
        return tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }

}
