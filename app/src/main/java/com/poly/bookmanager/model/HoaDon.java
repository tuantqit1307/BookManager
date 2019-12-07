package com.poly.bookmanager.model;

public class HoaDon {
    String idHoaDon;
    String sumAmount;
    String sumPrice;
    String dateBuy;

    public HoaDon() {
    }

    public HoaDon(String idHoaDon, String sumAmount, String sumPrice, String dateBuy) {
        this.idHoaDon = idHoaDon;
        this.sumAmount = sumAmount;
        this.sumPrice = sumPrice;
        this.dateBuy = dateBuy;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(String sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getDateBuy() {
        return dateBuy;
    }

    public void setDateBuy(String dateBuy) {
        this.dateBuy = dateBuy;
    }
}

