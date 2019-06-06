package com.terkea.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.terkea.App;
import com.terkea.system.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LandingPageController {

    @FXML
    private JFXButton createServer;

    @FXML
    private JFXTextArea serverName;

    @FXML
    private JFXButton joinServer;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == createServer){
            App.setRoot("CreateServer");
        }else if(event.getSource() == joinServer){
            App.setRoot("CreateClient");
        }
    }

}
