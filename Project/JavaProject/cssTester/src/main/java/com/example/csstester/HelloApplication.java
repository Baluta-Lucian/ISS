package com.example.csstester;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    double x,y = 0;
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view-updated.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sign-up-view-upgraded.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("icon-test-view.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("employee-main-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("employee-upgraded-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        HelloController helloController = fxmlLoader.getController();
//        CpuDbRepository cpuDbRepository = new CpuDbRepository();
//        helloController.setRepos(cpuDbRepository);
//        helloController.initModelIconTest();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

//        Parent root = FXMLLoader.load(HelloApplication.class.getResource("employee-upgraded-view.fxml"));
//        stage.initStyle(StageStyle.UNDECORATED);
//
//
//
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}