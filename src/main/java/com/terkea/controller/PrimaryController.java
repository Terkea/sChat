package com.terkea.controller;

import java.io.IOException;

import com.terkea.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary(ActionEvent event) throws IOException {
        App.changeStage("secondary", event, 400, 600, "TEST");
    }
}
