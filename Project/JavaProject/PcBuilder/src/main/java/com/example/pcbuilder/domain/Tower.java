package com.example.pcbuilder.domain;

import java.util.Objects;

public class Tower {

    private int id;

    private String name;

    private int noFans;

    private int includedFans;

    private String type;

    private String color;

    private Boolean rgb;

    private float price;

    private String imageUrl;

    public Tower(String name, int noFans, int includedFans, String type, String color, Boolean rgb, float price, String imageUrl) {
        this.name = name;
        this.noFans = noFans;
        this.includedFans = includedFans;
        this.type = type;
        this.color = color;
        this.rgb = rgb;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoFans() {
        return noFans;
    }

    public void setNoFans(int noFans) {
        this.noFans = noFans;
    }

    public int getIncludedFans() {
        return includedFans;
    }

    public void setIncludedFans(int includedFans) {
        this.includedFans = includedFans;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getRgb() {
        return rgb;
    }

    public void setRgb(Boolean rgb) {
        this.rgb = rgb;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tower tower = (Tower) o;
        return name.equals(tower.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
