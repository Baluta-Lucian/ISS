package com.example.pcbuilder.domain;

public class PC {
    private String buildName;
    private Cpu cpu;
    private CpuCooler cpuCooler;
    private Memory memory;
    private MotherBoard motherBoard;
    private PowerSupply powerSupply;
    private Storage storage;
    private Tower tower;
    private VideoCard videoCard;
    private int id_client;
    private float totalPrice;

    private int id;

    private String status;


    public PC(String buildName, Cpu cpu, CpuCooler cpuCooler, Memory memory, MotherBoard motherBoard, PowerSupply powerSupply, Storage storage, Tower tower, VideoCard videoCard, int id_client, float totalPrice, String status) {
        this.buildName = buildName;
        this.cpu = cpu;
        this.cpuCooler = cpuCooler;
        this.memory = memory;
        this.motherBoard = motherBoard;
        this.powerSupply = powerSupply;
        this.storage = storage;
        this.tower = tower;
        this.videoCard = videoCard;
        this.id_client = id_client;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public PC() {
        this.totalPrice = 0;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        if(this.cpu != null)
            this.totalPrice -= this.cpu.getPrice();
        this.totalPrice += cpu.getPrice();

        this.cpu = cpu;
    }

    public CpuCooler getCpuCooler() {
        return cpuCooler;
    }

    public void setCpuCooler(CpuCooler cpuCooler) {
        if(this.cpuCooler != null)
            this.totalPrice -= this.cpuCooler.getPrice();
        this.totalPrice += cpuCooler.getPrice();

        this.cpuCooler = cpuCooler;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        if(this.memory != null)
            this.totalPrice -= this.memory.getPrice();
        this.totalPrice += memory.getPrice();

        this.memory = memory;
    }

    public MotherBoard getMotherBoard() {
        return motherBoard;
    }

    public void setMotherBoard(MotherBoard motherBoard) {
        if(this.motherBoard != null)
            this.totalPrice -= this.motherBoard.getPrice();
        this.totalPrice += motherBoard.getPrice();

        this.motherBoard = motherBoard;
    }

    public PowerSupply getPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(PowerSupply powerSupply) {
        if(this.powerSupply != null)
            this.totalPrice -= this.powerSupply.getPrice();
        this.totalPrice += powerSupply.getPrice();

        this.powerSupply = powerSupply;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        if(this.storage != null)
            this.totalPrice -= this.storage.getPrice();
        this.totalPrice += storage.getPrice();

        this.storage = storage;
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        if(this.tower != null)
            this.totalPrice -= this.tower.getPrice();
        this.totalPrice += tower.getPrice();

        this.tower = tower;
    }

    public VideoCard getVideoCard() {
        return videoCard;
    }

    public void setVideoCard(VideoCard videoCard) {
        if(this.videoCard != null)
            this.totalPrice -= this.videoCard.getPrice();
        this.totalPrice += videoCard.getPrice();

        this.videoCard = videoCard;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public float getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


