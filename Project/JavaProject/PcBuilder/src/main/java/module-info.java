module com.example.pcbuilder {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.common;
    requires com.jfoenix;


    opens com.example.pcbuilder to javafx.fxml;
    opens com.example.pcbuilder.service to javafx.fxml;
    opens com.example.pcbuilder.repository to javafx.fxml;
    opens com.example.pcbuilder.domain to javafx.fxml;
    opens com.example.pcbuilder.controller to javafx.fxml;
    opens com.example.pcbuilder.utils.events to javafx.fxml;
    opens com.example.pcbuilder.utils.observer to javafx.fxml;

    exports com.example.pcbuilder;
    exports com.example.pcbuilder.utils.events;
    exports com.example.pcbuilder.utils.observer;
    exports com.example.pcbuilder.service;
    exports com.example.pcbuilder.repository;
    exports com.example.pcbuilder.domain;
    exports com.example.pcbuilder.controller;
}