package com.poly.bookmanager.model;

public class Top10Buy {
    String maSach;
    String soLuongMua;
    String nameSach;

    public Top10Buy(String maSach, String soLuongMua, String nameSach) {
        this.maSach = maSach;
        this.soLuongMua = soLuongMua;
        this.nameSach = nameSach;
    }

    public Top10Buy(String soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public Top10Buy() {
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(String soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public String getNameSach() {
        return nameSach;
    }

    public void setNameSach(String nameSach) {
        this.nameSach = nameSach;
    }
}
