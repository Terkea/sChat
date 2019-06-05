package com.terkea.controller;

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
    public TextArea muieDragnea;

    @FXML
    private ScrollPane connectedUsersPane;

    private static final int portNumber = 4444;
    private String host;
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

    public void loadUser(String host, String userName) throws IOException {
        setHost(host);
        setUserName(userName);

        System.out.println(getUserName() + " > Trying to connect to the server...");
        new Thread(() -> {
            createClient();
        }).start();

        System.out.println(getUserName() + " > Success");
    }

    private void createClient(){
        try {
            socket = new Socket("localhost", portNumber);


            DataInputStream in = new DataInputStream(socket.getInputStream());
//            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            new Thread(()->{
                while(!socket.isClosed()){
                    try {
                        if (in.available() > 0){
                            String input = in.readUTF();
                            System.out.println(getUserName() + " > " + input);
//                            muieDragnea.appendText(input);
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