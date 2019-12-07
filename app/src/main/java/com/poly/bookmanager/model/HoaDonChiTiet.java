package com.poly.bookmanager.model;

public class HoaDonChiTiet {
    int idHDCT;
    String idHoaDon;
    String nameHDCT;
    String amountNow;
    String priceNow;
    String dateNow;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(String idHoaDon, String nameHDCT, String amountNow, String priceNow, String dateNow) {
        this.idHoaDon = idHoaDon;
        this.nameHDCT = nameHDCT;
        this.amountNow = amountNow;
        this.priceNow = priceNow;
        this.dateNow = dateNow;
    }

    public String getDateNow() {
        return dateNow;
    }

    public void setDateNow(String dateNow) {
        this.dateNow = dateNow;
    }

    public int getIdHDCT() {
        return idHDCT;
    }

    public void setIdHDCT(int idHDCT) {
        this.idHDCT = idHDCT;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getNameHDCT() {
        return nameHDCT;
    }

    public void setNameHDCT(String nameHDCT) {
        this.nameHDCT = nameHDCT;
    }

    public String getAmountNow() {
        return amountNow;
    }

    public void setAmountNow(String amountNow) {
        this.amountNow = amountNow;
    }

    public String getPriceNow() {
        return priceNow;
    }

    public void setPriceNow(String priceNow) {
        this.priceNow = priceNow;
    }
}
