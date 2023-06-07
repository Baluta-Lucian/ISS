package com.example.pcbuilder.utils.events;

import com.example.pcbuilder.domain.Order;

public class PlacedOrderEvent implements Event{

    private Order data, oldData;

    public PlacedOrderEvent(Order data){
        this.data = data;
    }

    public PlacedOrderEvent(Order data, Order oldData){
        this.data = data;
        this.oldData = oldData;
    }

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }

    public Order getOldData() {
        return oldData;
    }

    public void setOldData(Order oldData) {
        this.oldData = oldData;
    }
}
