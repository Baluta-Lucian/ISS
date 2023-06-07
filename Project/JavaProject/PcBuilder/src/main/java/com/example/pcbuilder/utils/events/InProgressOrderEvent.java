package com.example.pcbuilder.utils.events;

import com.example.pcbuilder.domain.Order;

public class InProgressOrderEvent implements Event {

    private Order data, oldData;

    public InProgressOrderEvent(Order data) {
        this.data = data;
    }

    public InProgressOrderEvent(Order data, Order oldData) {
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
