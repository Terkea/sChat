package com.terkea.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class LandingPageController {

    @FXML
    private AnchorPane welcomePane;

    @FXML
    private AnchorPane createServerPane;

    @FXML
    private AnchorPane joinServerPane;

    @FXML
    private JFXButton createServer;

    @FXML
    private JFXButton joinServer;

    @FXML
    private void handleButtonAction(ActionEvent event){
        if (event.getSource() == createServer){
            createServerPane.toFront();
        }else if(event.getSource() == joinServer){
            joinServerPane.toFront();
        }
    }

}
