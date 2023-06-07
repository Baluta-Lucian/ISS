package com.example.pcbuilder.controller;

import com.example.pcbuilder.MainClass;
import com.example.pcbuilder.domain.Client;
import com.example.pcbuilder.domain.Employee;
import com.example.pcbuilder.repository.ClientDbRepository;
import com.example.pcbuilder.repository.EmployeeDbRepository;
import com.example.pcbuilder.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginController {

    Alert alert = new Alert(Alert.AlertType.NONE);


    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button signinButton;

    @FXML
    private TextField usernameTextField;

    Service service;

    public void setService(Service service){
        this.service = service;
    }

    @FXML
    void onLogginBtnPressed(ActionEvent actionEvent) throws IOException{
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if(username == null || password == null){
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("username or password must not be null!");
            alert.show();
        }
        else{
            int ok = 0;
            Employee employee = service.findOneEmployee(username);
            if(employee == null)
                ok = 1;
            else {
                if(employee.getPassword().equals(password)){
                    //@TODO add controller for employee
                    System.out.println("Trying to open employee window");
                    FXMLLoader fxmlLoaderEmployee = new FXMLLoader(MainClass.class.getResource("employee-upgraded-view.fxml"));
                    Scene scene = new Scene(fxmlLoaderEmployee.load());
                    EmployeeController employeeController = fxmlLoaderEmployee.getController();
                    employeeController.setEmployee(employee);
                    employeeController.setService(service);
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setTitle("Employee!");
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Invalid password!");
                    alert.show();
                }
            }
            if(ok == 1){
                Client client = service.findOneClient(username);
                if(client == null){
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("No Employee or Client with this username!");
                    alert.show();
                }
                else{
                    if(client.getPassword().equals(password)){
                        //@TODO add controller for Client
                        System.out.println("Print  client:");
                        System.out.println(client);
                        System.out.println("Trying to open client window");
                        FXMLLoader fxmlLoaderClient = new FXMLLoader(MainClass.class.getResource("customer-view-updated.fxml"));
                        Scene scene = new Scene(fxmlLoaderClient.load());
                        ClientController clientController = fxmlLoaderClient.getController();
                        clientController.setClient(client);
                        clientController.setService(service);
                        Stage stage = new Stage();
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setTitle("Client!");
                        stage.setScene(scene);
                        stage.show();
                    }
                    else {
                        alert.setAlertType(Alert.AlertType.WARNING);
                        alert.setContentText("Invalid password!");
                        alert.show();
                    }
                }
            }


        }


    }

    @FXML
    void onSignupBtnPressed(ActionEvent actionEvent) throws IOException{
        System.out.println("Trying to open sign-up window");
//        FXMLLoader fxmlLoaderSignUp = new FXMLLoader(MainClass.class.getResource("sign-up-view.fxml"));
        FXMLLoader fxmlLoaderSignUp = new FXMLLoader(MainClass.class.getResource("sign-up-view-upgraded.fxml"));
        Scene scene = new Scene(fxmlLoaderSignUp.load());
        Stage stage = new Stage();
        SignUpController signUpController = fxmlLoaderSignUp.getController();
        signUpController.setService(service);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Sign-Up!");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //block multiple instances
        stage.show();
    }

}
