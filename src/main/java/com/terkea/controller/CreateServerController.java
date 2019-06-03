package com.terkea.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.terkea.system.Server;
import com.terkea.system.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.ServerSocket;


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
    void createAndJoin() {
        Server attempt = new Server();
        attempt.start();

    }
}
