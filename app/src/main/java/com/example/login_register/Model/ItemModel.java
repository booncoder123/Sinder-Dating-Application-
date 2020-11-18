package com.example.login_register.Model;

public class ItemModel {
    private int image;
    private String name, usia, kota;

    public ItemModel(int image, String name, String usia, String kota) {
        this.image = image;
        this.name = name;
        this.usia = usia;
        this.kota = kota;
    }

    public ItemModel() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsia() {
        return usia;
    }

    public void setUsia(String usia) {
        this.usia = usia;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }
}
