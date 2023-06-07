package com.example.pcbuilder.domain;

import java.util.Objects;

public class VideoCard {

    private int id;

    private String name;

    private String slot;

    private String chipset;

    private String maxResolution;

    private String size;

    private String type;

    private String frequency;

    private String color;

    private Boolean rgb;

    private float price;

    private String imageUrl;

    public VideoCard(String name, String slot, String chipset, String maxResolution, String size, String type, String frequency, String color, Boolean rgb, float price, String imageUrl) {
        this.name = name;
        this.slot = slot;
        this.chipset = chipset;
        this.maxResolution = maxResolution;
        this.size = size;
        this.type = type;
        this.frequency = frequency;
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

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getMaxResolution() {
        return maxResolution;
    }

    public void setMaxResolution(String maxResolution) {
        this.maxResolution = maxResolution;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
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
        VideoCard videoCard= (VideoCard) o;
        return name.equals(videoCard.name);
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
