package com.terkea.controller;

import com.terkea.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreateClientController {

    @FXML
    private TextField server;

    @FXML
    private TextField nickname;

    @FXML
    void joinServer(ActionEvent event) {
        try {
            App.changeStage("Chat", event, 500, 800, "sChat - Better safe than sorry");
            ClientController newClient = new ClientController();
            newClient.loadUser(server.getText(), nickname.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
