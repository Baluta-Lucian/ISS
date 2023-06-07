package com.example.pcbuilder;

import com.example.pcbuilder.controller.LoginController;
import com.example.pcbuilder.domain.Order;
import com.example.pcbuilder.domain.OrderStatus;
import com.example.pcbuilder.repository.*;
import com.example.pcbuilder.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainClass extends Application {

                                                                private String url = "jdbc:postgresql://localhost:5432/pcbuilder";
                                                                private String username = "postgres";
                                                                private String password = "CripiFace2";



    @Override
    public void start(Stage stage) throws Exception {

        System.out.println(OrderStatus.Pending.toString());


        ClientDbRepository clientDbRepository = new ClientDbRepository(url, username, password);
        EmployeeDbRepository employeeDbRepository = new EmployeeDbRepository(url, username, password);
        OrderDbRepository orderDbRepository = new OrderDbRepository(url, username, password);

        //Domain repos
        //@TODO repo for PC
        CpuDbRepository cpuDbRepository = new CpuDbRepository(url, username, password);
        CpuCoolerDbRepository cpuCoolerDbRepository = new CpuCoolerDbRepository(url, username, password);
        MemoryDbRepository memoryDbRepository = new MemoryDbRepository(url, username, password);
        MotherBoardDbRepository motherBoardDbRepository = new MotherBoardDbRepository(url, username, password);
        PowerSupplyDbRepository powerSupplyDbRepository = new PowerSupplyDbRepository(url, username, password);
        StorageDbRepository storageDbRepository = new StorageDbRepository(url, username, password);
        TowerDbRepository towerDbRepository = new TowerDbRepository(url, username, password);
        VideoCardDbRepository videoCardDbRepository = new VideoCardDbRepository(url, username, password);
        PcDbRepository pcDbRepository = new PcDbRepository(url, username, password, cpuDbRepository, cpuCoolerDbRepository, memoryDbRepository, motherBoardDbRepository, powerSupplyDbRepository, storageDbRepository, towerDbRepository, videoCardDbRepository);
        //@TODO service for PC
        Service service = new Service(clientDbRepository, employeeDbRepository, orderDbRepository, cpuDbRepository, cpuCoolerDbRepository, memoryDbRepository, motherBoardDbRepository, powerSupplyDbRepository, storageDbRepository, towerDbRepository, videoCardDbRepository, pcDbRepository);

        System.out.println(MainClass.class.getResource("login-view-updated.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource("login-view-updated.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();

    }
}
