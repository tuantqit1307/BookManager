package com.poly.bookmanager.model;

public class Sach {
    String idSach;
    String theLoai;
    String tieuDe;
    String tacGia;
    String nxb;
    String giasach;
    int soluong;
    byte[] hinhanh;

    public Sach() {
    }

    public Sach(String idSach, String theLoai, String tieude, String tacgia, String nxb, String giasach, int soluong, byte[] hinhanh) {
        this.idSach = idSach;
        this.theLoai = theLoai;
        this.tieuDe = tieude;
        this.tacGia = tacgia;
        this.nxb = nxb;
        this.giasach = giasach;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
    }

    public Sach(String idSach, String tieuDe, String tacGia, String nxb, String giasach, int soluong) {
        this.idSach = idSach;
        this.tieuDe = tieuDe;
        this.tacGia = tacGia;
        this.nxb = nxb;
        this.giasach = giasach;
        this.soluong = soluong;
    }

    public Sach(int soluong) {
        this.soluong = soluong;
    }

    public String getIdSach() {
        return idSach;
    }

    public void setIdSach(String idSach) {
        this.idSach = idSach;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public String getGiasach() {
        return giasach;
    }

    public void setGiasach(String giasach) {
        this.giasach = giasach;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }
}
