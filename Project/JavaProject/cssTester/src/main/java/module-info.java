module com.example.csstester {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;



    opens com.example.csstester to javafx.fxml;
    exports com.example.csstester;
}