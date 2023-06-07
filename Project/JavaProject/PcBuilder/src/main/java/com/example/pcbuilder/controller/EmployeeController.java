package com.example.pcbuilder.controller;

import com.example.pcbuilder.MainClass;
import com.example.pcbuilder.domain.*;
import com.example.pcbuilder.service.Service;
import com.example.pcbuilder.utils.events.*;
import com.example.pcbuilder.utils.observer.Observable;
import com.example.pcbuilder.utils.observer.Observer;
import com.google.common.base.Strings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class EmployeeController implements Observer {

    Service service;

    Employee currentEmployee;

    int ok;

    Order workingOrder;

    Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    private TableColumn<Order, Float> budgetTableColumn;

    @FXML
    private TableColumn<Order, String> buildNameTableColumn;

    @FXML
    private TableColumn<Order, String> descriptionTableColumn;



    @FXML
    private TableView<Order> ordersPendingTableView;

    @FXML
    private Button selectButton;


    @FXML
    private ComboBox<Storage> StorageComboBox;

    @FXML
    private TextField budgetLeftTextField;

    @FXML
    private TextField budgetTextField;

    @FXML
    private TextField buildNameTextField;

    @FXML
    private AnchorPane buildPane;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<Cpu> cpuComboBox;

    @FXML
    private ComboBox<CpuCooler> cpuCoolerComboBox;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private TextArea reasonTextField;

    @FXML
    private Label employeeNameLabel;

    @FXML
    private Button finsihBtn;

    PC workingPC;

    @FXML
    private Button logOutBtn;


    @FXML
    private AnchorPane mainPane;

//    @FXML
//    private AnchorPane main_form;

    @FXML
    private ComboBox<Memory> memoryComboBox;

    @FXML
    private ComboBox<MotherBoard> motherBoardComboBox;

    @FXML
    private Button ordersBtn;

    @FXML
    private ComboBox<PowerSupply> powerSupplyComboBox;

    @FXML
    private ComboBox<Tower> towerComboBox;

    @FXML
    private ComboBox<VideoCard> videoCardComboBox;

    @FXML
    private Button workOnBuildBtn;

    ObservableList<Order> listaOrdersTableView = FXCollections.observableArrayList();
    ObservableList<Cpu> listaCpuCombo = FXCollections.observableArrayList();
    ObservableList<CpuCooler> listaCpuCoolerCombo = FXCollections.observableArrayList();
    ObservableList<Memory> listaMemoryCombo = FXCollections.observableArrayList();
    ObservableList<MotherBoard> listaMotherBoardCombo = FXCollections.observableArrayList();
    ObservableList<PowerSupply> listaPowerSupplyCombo = FXCollections.observableArrayList();
    ObservableList<Storage> listaStorageCombo = FXCollections.observableArrayList();
    ObservableList<Tower> listaTowerCombo = FXCollections.observableArrayList();
    ObservableList<VideoCard> listaVideoCardCombo = FXCollections.observableArrayList();

    private final Map<Class<? extends Event>, Observer> listeners;


    public EmployeeController() {
        @SuppressWarnings("rawtypes")

        Map<Class<? extends Event>, Observer> temp  = new HashMap<>();

        temp.put(PlacedOrderEvent.class, new PlacedOrderObserver());
        temp.put(InProgressOrderEvent.class, new InProgressOrderObserver());
        temp.put(FinishedPcEvent.class, new FinishedPcObserver());
        temp.put(CancelOrderEvent.class, new CancelOrderObserver());

        listeners = Collections.unmodifiableMap(temp);
    }
    public void setService(Service service){
        this.service = service;
        //@TODO notify observers
        initModel();
        this.service.addObserver(this);
        employeeNameLabel.setText(currentEmployee.getFirstName());
        mainPane.setVisible(Boolean.TRUE);
        buildPane.setVisible(Boolean.FALSE);
        this.ok = 0;
    }

    public void setEmployee(Employee employee){
        this.currentEmployee = employee;
    }

    private void setTable(){
        budgetTableColumn.setCellValueFactory(new PropertyValueFactory<Order, Float>("budget"));
        buildNameTableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("buildName"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("description"));

        ordersPendingTableView.setItems(listaOrdersTableView);
    }

    private void updatePendingList(){
        System.out.println("Init Model + getting all pending");
        List<Order> allPending = service.getAllPendingOrders();
        listaOrdersTableView.setAll(allPending);
    }

    private void initModel(){
        updatePendingList();

        System.out.println("Setam tabel...");
        setTable();
    }

    @FXML
    void onSelectBtnPressed(ActionEvent actionEvent) throws IOException{
        Order order = ordersPendingTableView.getSelectionModel().getSelectedItem();
        if(order != null){
            order.setOrderStatus(OrderStatus.InProgress);
            service.updateOrderPendingToProgress(order);
            this.workingOrder = order;
            buildPane.setVisible(Boolean.TRUE);
            mainPane.setVisible(Boolean.FALSE);
            budgetTextField.setText(String.valueOf(order.getBudget()));
            budgetLeftTextField.setText(String.valueOf(order.getBudget()));
            descriptionTextField.setText(order.getDescription());
            buildNameTextField.setText(order.getBuildName());
            this.ok = 1;
            this.workingPC = new PC();
            this.workingPC.setId_client(this.workingOrder.getId_client());
            this.workingPC.setBuildName(this.workingOrder.getBuildName());
            initComboBoxes();
        }
        else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("You must select an order to continue!");
            alert.show();
        }
    }

    private void initComboLists(){
        listaCpuCombo.setAll(this.service.makeUniqueCpu());
        listaCpuCoolerCombo.setAll(this.service.makeUniqueCpuCooler());
        listaMemoryCombo.setAll(this.service.makeUniqueMemory());
        listaMotherBoardCombo.setAll(this.service.makeUniqueMotherBoard());
        listaPowerSupplyCombo.setAll(this.service.makeUniquePowerSupply());
        listaStorageCombo.setAll(this.service.makeUniqueStorage());
        listaTowerCombo.setAll(this.service.makeUniqueTower());
        listaVideoCardCombo.setAll(this.service.makeUniqueVideoCard());
    }

    private void initComboBoxes(){

        cpuComboBox.getItems().clear();
        cpuCoolerComboBox.getItems().clear();
        memoryComboBox.getItems().clear();
        motherBoardComboBox.getItems().clear();
        powerSupplyComboBox.getItems().clear();
        StorageComboBox.getItems().clear();
        towerComboBox.getItems().clear();
        videoCardComboBox.getItems().clear();

        initComboLists();
        //CPU
        cpuComboBox.setItems(listaCpuCombo);
        cpuComboBox.setCellFactory(param -> {
            return new ListCell<Cpu>() {

                @Override
                public void updateItem(Cpu item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {
                        setText(item.getName());

                        if(item.getImageUrl() != null) {
                            // Add the Tooltip with the image of the current item
                            Image image = new Image(item.getImageUrl());
                            Tooltip tt = new Tooltip();
                            tt.setGraphic(new ImageView(image));

                            setTooltip(tt);
                        }
                    } else {
                        setText(null);
                        setTooltip(null);
                    }
                }
            };
        });

        //CpuCooler
        cpuCoolerComboBox.setItems(listaCpuCoolerCombo);
        cpuCoolerComboBox.setCellFactory(param -> {
            return new ListCell<CpuCooler>() {

                @Override
                public void updateItem(CpuCooler item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {
                        setText(item.getName());

                        if(item.getImageUrl() != null) {
                            // Add the Tooltip with the image of the current item
                            Image image = new Image(item.getImageUrl());
                            Tooltip tt = new Tooltip();
                            tt.setGraphic(new ImageView(image));

                            setTooltip(tt);
                        }
                    } else {
                        setText(null);
                        setTooltip(null);
                    }
                }
            };
        });

        //Memory
        memoryComboBox.setItems(listaMemoryCombo);
        memoryComboBox.setCellFactory(param -> {
            return new ListCell<Memory>() {

                @Override
                public void updateItem(Memory item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {
                        setText(item.getName());

                        if(item.getImageUrl() != null) {
                            // Add the Tooltip with the image of the current item
                            Image image = new Image(item.getImageUrl());
                            Tooltip tt = new Tooltip();
                            tt.setGraphic(new ImageView(image));

                            setTooltip(tt);
                        }
                    } else {
                        setText(null);
                        setTooltip(null);
                    }
                }
            };
        });

        //MotherBoard
        motherBoardComboBox.setItems(listaMotherBoardCombo);
        motherBoardComboBox.setCellFactory(param -> {
            return new ListCell<MotherBoard>() {

                @Override
                public void updateItem(MotherBoard item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {
                        setText(item.getName());

                        if(item.getImageUrl() != null) {
                            // Add the Tooltip with the image of the current item
                            Image image = new Image(item.getImageUrl());
                            Tooltip tt = new Tooltip();
                            tt.setGraphic(new ImageView(image));

                            setTooltip(tt);
                        }
                    } else {
                        setText(null);
                        setTooltip(null);
                    }
                }
            };
        });

        //Power Supply

        powerSupplyComboBox.setItems(listaPowerSupplyCombo);
        powerSupplyComboBox.setCellFactory(param -> {
            return new ListCell<PowerSupply>() {

                @Override
                public void updateItem(PowerSupply item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {
                        setText(item.getName());

                        if(item.getImageUrl() != null) {
                            // Add the Tooltip with the image of the current item
                            Image image = new Image(item.getImageUrl());
                            Tooltip tt = new Tooltip();
                            tt.setGraphic(new ImageView(image));

                            setTooltip(tt);
                        }
                    } else {
                        setText(null);
                        setTooltip(null);
                    }
                }
            };
        });

        //Storage

        StorageComboBox.setItems(listaStorageCombo);
        StorageComboBox.setCellFactory(param -> {
            return new ListCell<Storage>() {

                @Override
                public void updateItem(Storage item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {
                        setText(item.getName());

                        if(item.getImageUrl() != null) {
                            // Add the Tooltip with the image of the current item
                            Image image = new Image(item.getImageUrl());
                            Tooltip tt = new Tooltip();
                            tt.setGraphic(new ImageView(image));

                            setTooltip(tt);
                        }
                    } else {
                        setText(null);
                        setTooltip(null);
                    }
                }
            };
        });

        //tower

        towerComboBox.setItems(listaTowerCombo);
        towerComboBox.setCellFactory(param -> {
            return new ListCell<Tower>() {

                @Override
                public void updateItem(Tower item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {
                        setText(item.getName());

                        if(item.getImageUrl() != null) {
                            // Add the Tooltip with the image of the current item
                            Image image = new Image(item.getImageUrl());
                            Tooltip tt = new Tooltip();
                            tt.setGraphic(new ImageView(image));

                            setTooltip(tt);
                        }
                    } else {
                        setText(null);
                        setTooltip(null);
                    }
                }
            };
        });



        //Video Card
        videoCardComboBox.setItems(listaVideoCardCombo);
        videoCardComboBox.setCellFactory(param -> {
            return new ListCell<VideoCard>() {

                @Override
                public void updateItem(VideoCard item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {
                        setText(item.getName());

                        if(item.getImageUrl() != null) {
                            // Add the Tooltip with the image of the current item
                            Image image = new Image(item.getImageUrl());
                            Tooltip tt = new Tooltip();
                            tt.setGraphic(new ImageView(image));

                            setTooltip(tt);
                        }
                    } else {
                        setText(null);
                        setTooltip(null);
                    }
                }
            };
        });


    }

    @FXML
    void onLogOutBtnPressed(ActionEvent actionEvent) throws IOException{
        System.out.println("Closing employee window!");
        if(this.ok == 1){
            this.workingOrder.setOrderStatus(OrderStatus.Pending);
            service.updateOrderPendingToProgress(this.workingOrder);
        }
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void changeComboCpu(ActionEvent actionEvent){
        this.workingPC.setCpu(cpuComboBox.getValue());
        float currentBudgetLeft = this.workingOrder.getBudget() - this.workingPC.getTotalPrice();
        this.budgetLeftTextField.setText(String.valueOf(currentBudgetLeft));
    }

    @FXML
    void changeComboCpuCooler(ActionEvent actionEvent){
        this.workingPC.setCpuCooler(cpuCoolerComboBox.getValue());
        float currentBudgetLeft = this.workingOrder.getBudget() - this.workingPC.getTotalPrice();
        this.budgetLeftTextField.setText(String.valueOf(currentBudgetLeft));
    }

    @FXML
    void changeComboMemory(ActionEvent actionEvent){
        this.workingPC.setMemory(memoryComboBox.getValue());
        float currentBudgetLeft = this.workingOrder.getBudget() - this.workingPC.getTotalPrice();
        this.budgetLeftTextField.setText(String.valueOf(currentBudgetLeft));
    }

    @FXML
    void changeComboMotherBoard(ActionEvent actionEvent){
        this.workingPC.setMotherBoard(motherBoardComboBox.getValue());
        float currentBudgetLeft = this.workingOrder.getBudget() - this.workingPC.getTotalPrice();
        this.budgetLeftTextField.setText(String.valueOf(currentBudgetLeft));
    }

    @FXML
    void changeComboPowerSupply(ActionEvent actionEvent){
        this.workingPC.setPowerSupply(powerSupplyComboBox.getValue());
        float currentBudgetLeft = this.workingOrder.getBudget() - this.workingPC.getTotalPrice();
        this.budgetLeftTextField.setText(String.valueOf(currentBudgetLeft));
    }

    @FXML
    void changeComboStorage(ActionEvent actionEvent){
        this.workingPC.setStorage(StorageComboBox.getValue());
        float currentBudgetLeft = this.workingOrder.getBudget() - this.workingPC.getTotalPrice();
        this.budgetLeftTextField.setText(String.valueOf(currentBudgetLeft));
    }

    @FXML
    void changeComboTower(ActionEvent actionEvent){
        this.workingPC.setTower(towerComboBox.getValue());
        float currentBudgetLeft = this.workingOrder.getBudget() - this.workingPC.getTotalPrice();
        this.budgetLeftTextField.setText(String.valueOf(currentBudgetLeft));
    }

    @FXML
    void changeComboVideoCard(ActionEvent actionEvent){
        this.workingPC.setVideoCard(videoCardComboBox.getValue());
        float currentBudgetLeft = this.workingOrder.getBudget() - this.workingPC.getTotalPrice();
        this.budgetLeftTextField.setText(String.valueOf(currentBudgetLeft));
    }

    private Boolean checkFinishedOrder(){
        if(this.cpuComboBox.getSelectionModel().getSelectedItem() == null)
            return Boolean.FALSE;
        if(this.cpuCoolerComboBox.getSelectionModel().getSelectedItem() == null)
            return Boolean.FALSE;
        if(this.memoryComboBox.getSelectionModel().getSelectedItem() == null)
            return Boolean.FALSE;
        if(this.motherBoardComboBox.getSelectionModel().getSelectedItem() == null)
            return Boolean.FALSE;
        if(this.powerSupplyComboBox.getSelectionModel().getSelectedItem() == null)
            return Boolean.FALSE;
        if(this.StorageComboBox.getSelectionModel().getSelectedItem() == null)
            return Boolean.FALSE;
        if(this.towerComboBox.getSelectionModel().getSelectedItem() == null)
            return Boolean.FALSE;
        if(this.videoCardComboBox.getSelectionModel().getSelectedItem() == null)
            return Boolean.FALSE;
        if(Float.parseFloat(budgetLeftTextField.getText()) < -100){
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Total build budget must be the client budget + maxim 100$!");
            alert.show();
            return Boolean.FALSE;
        }
        Cpu cpu = cpuComboBox.getSelectionModel().getSelectedItem();
        MotherBoard motherBoard = motherBoardComboBox.getSelectionModel().getSelectedItem();
        Memory memory = memoryComboBox.getSelectionModel().getSelectedItem();
        if(!motherBoard.getCpuSocket().equals(cpu.getSocket())){
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Incompatible motherboard with cpu");
            alert.show();
            return Boolean.FALSE;
        }
        if(!motherBoard.getMemoryType().equals(memory.getType())){
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Incompatible motherboard with RAM");
            alert.show();
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    @FXML
    void onFinishBtnPressed(ActionEvent actionEvent) throws IOException{
        //@TODO see how to finish an order
        if(checkFinishedOrder() == Boolean.TRUE){
            this.workingPC.setStatus("Pending");
            this.workingPC.setId_client(this.workingOrder.getId_client());
            this.service.savePC(this.workingPC);
            this.workingOrder.setOrderStatus(OrderStatus.Finished);
            this.mainPane.setVisible(Boolean.TRUE);
            this.buildPane.setVisible(Boolean.FALSE);
            this.ok = 0;
            initModel();
        }
    }

    @FXML
    void onCancelBtnPressed(ActionEvent actionEvent) throws IOException{
        if(Strings.isNullOrEmpty(reasonTextField.toString())){
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("You must provide the reason!");
            alert.show();
        }
        else{
            this.ok = 0;
            mainPane.setVisible(Boolean.TRUE);
            buildPane.setVisible(Boolean.FALSE);
            this.service.cancelOrder(this.workingOrder, reasonTextField.toString());
        }
    }



    private class PlacedOrderObserver implements Observer<PlacedOrderEvent>{

        @Override
        public void update(PlacedOrderEvent placedOrderEvent) {
            updatePendingList();
            setTable();
        }
    }

    private class FinishedPcObserver implements Observer<FinishedPcEvent>{

        @Override
        public void update(FinishedPcEvent finishedPcEvent) {
            updatePendingList();
            setTable();
        }
    }

    private class CancelOrderObserver implements Observer<CancelOrderEvent>{

        @Override
        public void update(CancelOrderEvent cancelOrderEvent) {
            if(Strings.isNullOrEmpty(cancelOrderEvent.getReason())){
                if(cancelOrderEvent.getBuildName().equals(workingOrder.getBuildName()) && workingOrder.getOrderStatus() == OrderStatus.InProgress) {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("The customer canceled the order, clearing battle field!");
                    alert.show();
                    mainPane.setVisible(Boolean.TRUE);
                    buildPane.setVisible(Boolean.FALSE);
                    ok = 0;
                }
                updatePendingList();
                setTable();

            }
            updatePendingList();
            setTable();
        }
    }



    private class InProgressOrderObserver implements Observer<InProgressOrderEvent>{

        @Override
        public void update(InProgressOrderEvent inProgressOrderEvent) {
            //@TODO ce se intampla cand apare new order
            initModel();
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
