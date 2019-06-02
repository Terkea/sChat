package com.terkea.controller;

import com.jfoenix.controls.JFXButton;
import com.terkea.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LandingPageController {

    @FXML
    private AnchorPane joinServerPane;

    @FXML
    private AnchorPane welcomePane;

    @FXML
    private JFXButton createServer;

    @FXML
    private JFXButton joinServer;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == createServer){
            App.setRoot("CreateServer");
        }else if(event.getSource() == joinServer){
            joinServerPane.toFront();
        }
    }

}
