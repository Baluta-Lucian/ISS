package com.example.pcbuilder.domain;

import java.util.Objects;

public class CpuCooler {

    private int id;

    private String name;

    private String socket;

    private String coolingType;

    private int rpm;

    private String cfm;

    private float db;

    private String color;

    private Boolean rgb;

    private float price;

    private String imageUrl;

    public CpuCooler(String name, String socket, String coolingType, int rpm, String cfm, float db, String color, Boolean rgb, float price, String imageUrl) {
        this.name = name;
        this.socket = socket;
        this.coolingType = coolingType;
        this.rpm = rpm;
        this.cfm = cfm;
        this.db = db;
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

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getCoolingType() {
        return coolingType;
    }

    public void setCoolingType(String coolingType) {
        this.coolingType = coolingType;
    }

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public String getCfm() {
        return cfm;
    }

    public void setCfm(String cfm) {
        this.cfm = cfm;
    }

    public float getDb() {
        return db;
    }

    public void setDb(float db) {
        this.db = db;
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
        CpuCooler cpuCooler = (CpuCooler) o;
        return name.equals(cpuCooler.name);
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
