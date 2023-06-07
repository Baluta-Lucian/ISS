package com.example.pcbuilder.controller;

import com.example.pcbuilder.domain.Client;
import com.example.pcbuilder.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField addressTextField;

    Alert alert = new Alert(Alert.AlertType.NONE);

//    alert.setAlertType(Alert.AlertType.WARNING);
//    alert.setContentText("No Employee or Client with this username!");
//    alert.show();

    @FXML
    private TextField cnpTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Button signupButton;

    Service service;

    @FXML
    private TextField usernameTextField;

    public void setService(Service service){
        this.service = service;
    }

    private Boolean checkClient(){
        if(usernameTextField.getText() == null || passwordTextField.getText() == null || firstNameTextField.getText() == null || lastNameTextField.getText() == null || phoneTextField.getText() == null || cnpTextField.getText() == null || addressTextField.getText() == null)
            return false;
        if(service.findOneClient(usernameTextField.getText()) != null)
            return false;
        if(service.findOneEmployee(usernameTextField.getText()) != null)
            return false;
        if(service.findOneClientByCnp(cnpTextField.getText()) != null)
            return false;
        return true;

    }


    @FXML
    void onBackToLoginBtnPressed(MouseEvent actionEvent) throws IOException{
        System.out.println("Closing sign up window!");
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onSigninBtnPressed(ActionEvent actionEvent) throws IOException{
        if(checkClient() == Boolean.TRUE){
            Client client = new Client(firstNameTextField.getText(), lastNameTextField.getText(), addressTextField.getText(), cnpTextField.getText(), phoneTextField.getText(), usernameTextField.getText(), passwordTextField.getText());
            service.addClient(client);
            System.out.println("Client sign up successfully!");
            Node source = (Node)  actionEvent.getSource();
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        }
        else{
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid information provided or Client already exists!");
            alert.show();
        }


    }


}
