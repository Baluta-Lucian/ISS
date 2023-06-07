package com.example.pcbuilder.controller;

import com.example.pcbuilder.domain.*;
import com.example.pcbuilder.service.Service;
import com.example.pcbuilder.utils.events.*;
import com.example.pcbuilder.utils.observer.Observer;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.*;

import com.google.common.base.Strings;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ClientController implements Observer {

    @FXML
    private TableColumn<Order, Float> budgetTableColumn;

    @FXML
    private TextField budgetTextField;


    Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private TableColumn<PC, CpuCooler> cpuCoolerPcColumn;

    @FXML
    private TableColumn<PC, String> buildNamePcColumn;
    @FXML
    private TableColumn<PC, Cpu> cpuPcColumn;

    @FXML
    private TableColumn<PC, Memory> memoryPcColumn;

    @FXML
    private TableColumn<PC, MotherBoard> motherBoardPcColumn;

    @FXML
    private TableColumn<PC, Float> totalPricePcColumn;

    @FXML
    private TableColumn<PC, Tower> towerPcColumn;

    @FXML
    private TableColumn<PC, VideoCard> videoCardPcColumn;

    @FXML
    private TableColumn<PC, Storage> storagePcColumn;

    @FXML
    private TableColumn<PC, PowerSupply> powerSupplyPcColumn;

    @FXML
    private TableView<PC> pcTableView;

    @FXML
    private AnchorPane slider;

    @FXML
    private TableColumn<Order, String> buildNameTableColumn;

    @FXML
    private TextField buildNameTextField;

    @FXML
    private JFXButton buildsHistoryBtn;
    @FXML
    private JFXButton buildsHistoryBtn2;

    @FXML
    private JFXButton logOutBtn1;
    @FXML
    private JFXButton logOutBtn2;

    @FXML
    private Pane buildsHistoryPane;

    @FXML
    private TableView<Order> buildsHistoryTableView;

    @FXML
    private JFXButton customBuildBtn;
    @FXML
    private JFXButton customBuildBtn2;

    @FXML
    private JFXButton cancelOrderBtn;

    @FXML
    private Pane customBuildPane;

    @FXML
    private Label totalCostLabel;


    @FXML
    private TextArea descriptionTextField;

    @FXML
    private JFXButton placeOrderBtn;

    @FXML
    private TableColumn<Order, OrderStatus> statusTableColumn;

    Service service;

    Client currentClient;




    ObservableList<Order> listaBuildsHistoryTableView = FXCollections.observableArrayList();
    ObservableList<PC> listaPcTableView = FXCollections.observableArrayList();


    private final Map<Class<? extends Event>, Observer> listeners;
    public void setService(Service service){
        this.service = service;
        //@TODO notify observers
        this.service.addObserver(this);
        initModel();

        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });

    }

    public ClientController() {
        @SuppressWarnings("rawtypes")

        Map<Class<? extends Event>, Observer> temp  = new HashMap<>();

        temp.put(PlacedOrderEvent.class, new PlacedOrderObserver());
        temp.put(InProgressOrderEvent.class, new InProgressOrderObserver());
        temp.put(FinishedPcEvent.class, new FinishedPcObserver());
        temp.put(CancelOrderEvent.class, new CancelOrderObserver());

        listeners = Collections.unmodifiableMap(temp);


    }

    private void setBuildsHistoryTable(){
        budgetTableColumn.setCellValueFactory(new PropertyValueFactory<Order, Float>("budget"));
        buildNameTableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("buildName"));
        statusTableColumn.setCellValueFactory(new PropertyValueFactory<Order, OrderStatus>("orderStatus"));

        buildsHistoryTableView.setItems(listaBuildsHistoryTableView);
    }


    private void updateListPc(){
        System.out.println("Init model + getting all history pc");
        List<PC> pcList = service.getAllPc(this.currentClient.getId());
        int totalBuildsPrices = 0;
        for(PC pc : pcList){
            if(pc.getStatus().equals("Accepted"))
              totalBuildsPrices += pc.getTotalPrice();
        }
        this.totalCostLabel.setText(String.valueOf(totalBuildsPrices));
        listaPcTableView.setAll(pcList);
    }

    private void updateList(){
        System.out.println("Init model + getting all history orders");
        List<Order> ordersHistory = service.getAllClientOrders(this.currentClient);
        listaBuildsHistoryTableView.setAll(ordersHistory);
        int labelValue = 0;
        for(Order order : ordersHistory){
            if(order.getOrderStatus().equals(OrderStatus.Accepted))
                labelValue +=(int) order.getBudget();
        }

        totalCostLabel.setText(String.valueOf(labelValue));
    }

    private void setPcTableView(){
        buildNamePcColumn.setCellValueFactory(new PropertyValueFactory<PC, String>("buildName"));
        cpuPcColumn.setCellValueFactory(new PropertyValueFactory<PC, Cpu>("cpu"));
        cpuCoolerPcColumn.setCellValueFactory(new PropertyValueFactory<PC, CpuCooler>("cpuCooler"));
        memoryPcColumn.setCellValueFactory(new PropertyValueFactory<PC, Memory>("memory"));
        motherBoardPcColumn.setCellValueFactory(new PropertyValueFactory<PC, MotherBoard>("motherBoard"));
        powerSupplyPcColumn.setCellValueFactory(new PropertyValueFactory<PC, PowerSupply>("powerSupply"));
        storagePcColumn.setCellValueFactory(new PropertyValueFactory<PC, Storage>("storage"));
        towerPcColumn.setCellValueFactory(new PropertyValueFactory<PC, Tower>("tower"));
        videoCardPcColumn.setCellValueFactory(new PropertyValueFactory<PC, VideoCard>("videoCard"));
        totalPricePcColumn.setCellValueFactory(new PropertyValueFactory<PC, Float>("totalPrice"));

        pcTableView.setItems(listaPcTableView);
    }

    private void initModel(){
        System.out.println("Init model + getting all history orders");
        List<Order> ordersHistory = service.getAllClientOrders(this.currentClient);
        listaBuildsHistoryTableView.setAll(ordersHistory);

        System.out.println("Hide la BuildsHistoryPane");
        System.out.println("Show la CustomBuildPane");
        buildsHistoryPane.setVisible(Boolean.FALSE);
        customBuildPane.setVisible(Boolean.TRUE);

//        System.out.println("Setam tabel...");
//        setBuildsHistoryTable();
    }


    private Boolean checkPlacedOrder(){
        if(Strings.isNullOrEmpty(budgetTextField.getText())) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Budget must not be empty");
            alert.show();
            return Boolean.FALSE;
        }

        if(Float.parseFloat(budgetTextField.getText()) < 999)
        {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Budget must be more than 999$!");
            alert.show();
            return Boolean.FALSE;
        }
        if(Strings.isNullOrEmpty(buildNameTextField.getText())) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Build name must not be empty!");
            alert.show();
            return Boolean.FALSE;
        }
        if(service.existsOrder(buildNameTextField.getText())) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Build name already exists! All builds are unique!");
            alert.show();
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }



    public void setClient(Client client){
        this.currentClient = client;
    }

    @FXML
    void onBuildsHistoryBtnPressed(ActionEvent actionEvent) throws IOException{
        updateList();
        updateListPc();
        buildsHistoryPane.setVisible(Boolean.TRUE);
        customBuildPane.setVisible(Boolean.FALSE);
        setBuildsHistoryTable();
        setPcTableView();
    }

    @FXML
    void onCustomBuildBtnPressed(ActionEvent actionEvent) throws IOException{
        buildsHistoryPane.setVisible(Boolean.FALSE);
        customBuildPane.setVisible(Boolean.TRUE);
    }

    @FXML
    void onLogOutBtnPressed(ActionEvent actionEvent) throws IOException{
        System.out.println("Closing client window!");
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onPlaceOrderBtnPressed(ActionEvent actionEvent) throws IOException{
        if(checkPlacedOrder()){
            Order placedOrder = new Order(Float.parseFloat(budgetTextField.getText()), descriptionTextField.getText(), buildNameTextField.getText(), OrderStatus.Pending, currentClient.getId());
            budgetTextField.clear();
            descriptionTextField.clear();
            buildNameTextField.clear();
            service.saveOrder(placedOrder);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Order placed! You can see your status in builds history tab!");
            alert.show();
        }
    }

    @FXML
    void onCancelBtnPressed(ActionEvent actionEvent) throws IOException{
        if(pcTableView.getSelectionModel().getSelectedItem() != null){
            System.out.println("Sunt in cancel pc");
            PC pcTest = pcTableView.getSelectionModel().getSelectedItem();
            for(PC pc1 : listaPcTableView){
                if (pc1.getBuildName().equals(pcTest.getBuildName())){
                    if(pc1.getStatus().equals("Pending")){
                        this.service.cancelOrderClient(pcTest.getBuildName());
                        updateList();
                        updateListPc();
                        setBuildsHistoryTable();
                        setPcTableView();
                    }
                }
            }
        }
        else if(buildsHistoryTableView.getSelectionModel().getSelectedItem() != null && buildsHistoryTableView.getSelectionModel().getSelectedItem().getOrderStatus() != OrderStatus.Accepted){
            System.out.println("Sunt in cancel order");
            Order order = buildsHistoryTableView.getSelectionModel().getSelectedItem();
            this.service.cancelOrderClient(order.getBuildName());
            updateList();
            updateListPc();
            setBuildsHistoryTable();
            setPcTableView();
        }
    }

    @FXML
    void onAcceptBtnPressed(ActionEvent actionEvent) throws IOException{
        if(pcTableView.getSelectionModel().getSelectedItem() != null) {
            PC pcTest = pcTableView.getSelectionModel().getSelectedItem();
            for(PC pc : listaPcTableView){
                if (pcTest.getBuildName().equals(pc.getBuildName())) {
                    if(pc.getStatus().equals("Pending")) {
                        this.service.acceptOrder(pc.getBuildName());
                        updateListPc();
                        updateList();
                        setBuildsHistoryTable();
                        setPcTableView();
                    }
                }
            }
        }
        else if(buildsHistoryTableView.getSelectionModel().getSelectedItem() != null) {
            if (buildsHistoryTableView.getSelectionModel().getSelectedItem().getOrderStatus() == OrderStatus.Finished) {
                Order order = buildsHistoryTableView.getSelectionModel().getSelectedItem();
                this.service.acceptOrder(order.getBuildName());
                updateListPc();
                updateList();
                setPcTableView();
                setBuildsHistoryTable();
            }
        }
    }

    private class PlacedOrderObserver implements Observer<PlacedOrderEvent>{

        @Override
        public void update(PlacedOrderEvent placedOrderEvent) {
            System.out.println("Observer client(update PlacedOrderEvent)!");
            if(placedOrderEvent.getData().getId_client() == currentClient.getId()) {
                updateList();

            }
        }
    }

    private class InProgressOrderObserver implements Observer<InProgressOrderEvent>{

        @Override
        public void update(InProgressOrderEvent inProgressOrderEvent) {
            System.out.println("Observer client(update InProgressOrderEvent)!");
            if(inProgressOrderEvent.getData().getId_client() == currentClient.getId() && inProgressOrderEvent.getData().getOrderStatus().equals(OrderStatus.InProgress)){
                updateList();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("An employee started to work on your order!");
                alert.show();
            }
            else if(inProgressOrderEvent.getData().getId_client() == currentClient.getId()){
                updateList();
            }
        }
    }

    private class FinishedPcObserver implements Observer<FinishedPcEvent>{

        @Override
        public void update(FinishedPcEvent finishedPcEvent) {
            System.out.println("Observer client(update FinishedPcEvent)!");
            if(finishedPcEvent.getData().getId_client() == currentClient.getId())
            {
                updateList();
                setBuildsHistoryTable();
                updateListPc();
                setPcTableView();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("An employee just finished your project: " + finishedPcEvent.getData().getBuildName() + "!");
                alert.show();
            }
        }
    }

    private class CancelOrderObserver implements Observer<CancelOrderEvent>{

        @Override
        public void update(CancelOrderEvent cancelOrderEvent) {
            System.out.println("Observer client(update cancelOrder)!");
            for(Order order : listaBuildsHistoryTableView) {
                if (order.getBuildName().equals(cancelOrderEvent.getBuildName())) {
                    if (!Strings.isNullOrEmpty(cancelOrderEvent.getReason())) {
                        updateList();
                        setBuildsHistoryTable();
                        updateListPc();
                        setPcTableView();
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setContentText("An employee canceled your order because: " + cancelOrderEvent.getReason());
                        alert.show();
                    }
                }
            }
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public void update(Event event) {
        if(listeners.containsKey(event.getClass())){
            listeners.get(event.getClass()).update(event);
        }
        else{
            throw new IllegalArgumentException("Event not supported");
        }
    }

}
