package com.poly.bookmanager.model;

public class Cart {
    int id;
    String masachItem;
    String nameItem;
    String amountItem;
    String priceItem;

    public Cart(String masachItem, String nameItem, String amountItem, String priceItem) {
        this.nameItem = nameItem;
        this.masachItem = masachItem;
        this.amountItem = amountItem;
        this.priceItem = priceItem;
    }

    public String getMasachItem() {
        return masachItem;
    }

    public void setMasachItem(String masachItem) {
        this.masachItem = masachItem;
    }

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getAmountItem() {
        return amountItem;
    }

    public void setAmountItem(String amountItem) {
        this.amountItem = amountItem;
    }

    public String getPriceItem() {
        return priceItem;
    }

    public void setPriceItem(String priceItem) {
        this.priceItem = priceItem;
    }
}
