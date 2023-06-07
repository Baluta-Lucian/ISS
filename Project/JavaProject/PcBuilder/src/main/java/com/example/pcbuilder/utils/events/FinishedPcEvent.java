package com.example.pcbuilder.utils.events;

import com.example.pcbuilder.domain.PC;

public class FinishedPcEvent implements Event{
    PC data;
    int id_client;

    public FinishedPcEvent(PC data, int id_client) {
        this.data = data;
        this.id_client = id_client;
    }

    public PC getData() {
        return data;
    }

    public void setData(PC data) {
        this.data = data;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }
}
