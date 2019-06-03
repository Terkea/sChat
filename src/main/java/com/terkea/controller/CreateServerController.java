package com.terkea.controller;


import com.terkea.App;
import com.terkea.system.Server;
import com.terkea.system.Utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;


public class CreateServerController {

    @FXML
    private TextField server;

    @FXML
    private TextField nickname;

    @FXML
    private void initialize(){
        server.setText(Utilities.getMyIpAddress());
    }

    @FXML
    void createAndJoin(ActionEvent event) {
        Server attempt = new Server();
        attempt.start();
        try {
            App.changeStage("Chat", event, 500, 800, "sChat");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
