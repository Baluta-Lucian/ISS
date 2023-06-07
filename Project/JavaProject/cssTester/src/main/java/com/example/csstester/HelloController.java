package com.example.csstester;

import javafx.animation.FillTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.util.List;

public class HelloController {
    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button testBtn;

    @FXML
    private ComboBox<Cpu> testComboBox;

    ObservableList<Cpu> listaCombo = FXCollections.observableArrayList();


    CpuDbRepository cpuDbRepository;

    public void initModelIconTest(){

        Image imageIcon = new Image("https://s13emagst.akamaized.net/products/39550/39549755/images/res_f16673353051f21c8bf3d2f96e1e010a.jpg?width=450&height=450&hash=5389E4A5D4A7838DAC2A51DE24CF7A70");

        Tooltip buttonTip = new Tooltip();

        testBtn.setTooltip(buttonTip);

        buttonTip.setGraphic(new ImageView(imageIcon));

//        for(Cpu cpu : cpuDbRepository.makeUnique())
//            System.out.println(cpu);

        initCombo();

    }

    private void initList(){
        List<Cpu> cpus = cpuDbRepository.makeUnique();
        listaCombo.setAll(cpus);
    }

    private void initCombo(){
        initList();
        testComboBox.setItems(listaCombo);
        testComboBox.setCellFactory(param -> {
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

    }


    public void setRepos(CpuDbRepository cpuDbRepository) {
        this.cpuDbRepository = cpuDbRepository;
    }
}