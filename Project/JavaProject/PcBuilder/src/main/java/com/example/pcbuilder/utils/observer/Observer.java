package com.example.pcbuilder.utils.observer;

import com.example.pcbuilder.utils.events.Event;

public interface Observer <E extends Event> {

    void update(E e);
}