package com.terkea.controller;


import com.terkea.App;
import com.terkea.system.server.Server;
import com.terkea.system.Utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;


public class CreateServerController {
    private static final int portNumber = 4444;

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
        Server attempt = new Server(portNumber);
        Thread createServer = new Thread(attempt);
        createServer.start();
        try {
            App.changeStage("Chat", event, 500, 800, "sChat - Better safe than sorry");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
