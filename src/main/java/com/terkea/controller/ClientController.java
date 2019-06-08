package com.terkea.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;

public class ClientController {

    private String userName;

    @FXML
    private ScrollPane allMessages;

    @FXML
    private TextArea typeMessage;

    @FXML
    public TextArea muieDragnea = new TextArea();

    @FXML
    private ScrollPane connectedUsersPane;

    private static final int portNumber = 4444;
    public String host;
    private static Socket socket;
    private DataOutputStream out;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @FXML
    public void loadUser(String host, String userName) throws IOException {
        setHost(host);
        setUserName(userName);
        System.err.println("HOST ADDRESS: " + getHost() + ":" +portNumber + "\n");
        System.out.println(getUserName() + " > Trying to connect to the server...");
        new Thread(() -> {
            createClient();
        }).start();

        System.out.println(getUserName() + " > Success");

    }


    @FXML
    public void createClient(){
        try {
            socket = new Socket(getHost(), portNumber);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            new Thread(()->{
                while(!socket.isClosed()){
                    try {
                        if (in.available() > 0){
                            String input = in.readUTF();
                            System.out.println(getUserName() + " > " + input);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void sendMessage() throws IOException {
        out = new DataOutputStream(socket.getOutputStream());

        if (!typeMessage.getText().trim().equals("")){
            out.writeUTF(typeMessage.getText());
            typeMessage.setText("");
        }
    }

}