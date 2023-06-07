package com.example.pcbuilder.domain;

import java.util.Objects;

public class MotherBoard {

    private int id;

    private String name;

    private String cpuSocket;

    private String chipset;

    private String memoryType;

    private String maxMemorySize;

    private String frequencyMemory;

    private String dualChannel;

    private int noUsbPorts;

    private int m2Sockets;

    private int noSataPorts;

    private String color;

    private float price;

    private String imageUrl;

    public MotherBoard(String name, String cpuSocket, String chipset, String memoryType, String maxMemorySize, String frequencyMemory, String dualChannel, int noUsbPorts, int m2Sockets, int noSataPorts, String color, float price, String imageUrl) {
        this.name = name;
        this.cpuSocket = cpuSocket;
        this.chipset = chipset;
        this.memoryType = memoryType;
        this.maxMemorySize = maxMemorySize;
        this.frequencyMemory = frequencyMemory;
        this.dualChannel = dualChannel;
        this.noUsbPorts = noUsbPorts;
        this.m2Sockets = m2Sockets;
        this.noSataPorts = noSataPorts;
        this.color = color;
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

    public String getCpuSocket() {
        return cpuSocket;
    }

    public void setCpuSocket(String cpuSocket) {
        this.cpuSocket = cpuSocket;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    public String getMaxMemorySize() {
        return maxMemorySize;
    }

    public void setMaxMemorySize(String maxMemorySize) {
        this.maxMemorySize = maxMemorySize;
    }

    public String getFrequencyMemory() {
        return frequencyMemory;
    }

    public void setFrequencyMemory(String frequencyMemory) {
        this.frequencyMemory = frequencyMemory;
    }

    public String getDualChannel() {
        return dualChannel;
    }

    public void setDualChannel(String dualChannel) {
        this.dualChannel = dualChannel;
    }

    public int getNoUsbPorts() {
        return noUsbPorts;
    }

    public void setNoUsbPorts(int noUsbPorts) {
        this.noUsbPorts = noUsbPorts;
    }

    public int getM2Sockets() {
        return m2Sockets;
    }

    public void setM2Sockets(int m2Sockets) {
        this.m2Sockets = m2Sockets;
    }

    public int getNoSataPorts() {
        return noSataPorts;
    }

    public void setNoSataPorts(int noSataPorts) {
        this.noSataPorts = noSataPorts;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
        MotherBoard motherBoard = (MotherBoard) o;
        return name.equals(motherBoard.name);
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
