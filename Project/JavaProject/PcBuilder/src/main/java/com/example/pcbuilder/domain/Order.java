package com.example.pcbuilder.domain;

public class Order {

    private int id;

    private float budget;

    private String description;

    private String buildName;

    private OrderStatus orderStatus;

    private int id_client;

    private String orderStatusString;

    public Order(float budget, String description, String buildName, OrderStatus orderStatus, int id_client) {
        this.budget = budget;
        this.description = description;
        this.buildName = buildName;
        this.orderStatus = orderStatus;
        this.id_client = id_client;
        this.orderStatusString = this.orderStatus.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getOrderStatusString() {
        return orderStatusString;
    }

    public void setOrderStatusString(String orderStatusString) {
        this.orderStatusString = orderStatusString;
    }
}
