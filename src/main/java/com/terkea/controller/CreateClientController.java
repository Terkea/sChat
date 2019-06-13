package com.terkea.controller;

import com.terkea.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class CreateClientController {

    @FXML
    private TextField server;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField nickname;

    private boolean checkIfServerIsOnline(String hostName) {
        boolean result;
        try {
            Socket test = new Socket(hostName, 4444);
            test.close();
            result = true;
        }
        catch(Exception e) {
            result = false;
        }
        return(result);
    }

    @FXML
    void joinServer(ActionEvent event) {
        errorLabel.setText("");
        if (checkIfServerIsOnline(server.getText())) {
            ClientController newClient = new ClientController();
            newClient.loadUser(server.getText(), nickname.getText());
            try {
                App.changeStage("Chat", event, 500, 800, "sChat - Better safe than sorry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Oh snap! Couldn't find the server");
        }
    }

}
