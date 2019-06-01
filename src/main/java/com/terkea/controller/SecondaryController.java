package com.terkea.controller;

import java.io.IOException;

import com.terkea.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class SecondaryController {

    @FXML
    private void switchToPrimary(ActionEvent event) throws IOException {
        App.changeStage("primary", event, 800, 600, "TEST");
    }
}