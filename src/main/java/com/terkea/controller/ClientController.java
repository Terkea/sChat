package com.terkea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private ScrollPane connectedUsersPane;

    private static final int portNumber = 4444;
    private static String stringClient = "CLIENT > ";
    private String host;
    public static Socket socket;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public static void loadUser(String userName) throws IOException {
        System.out.println(stringClient + " Trying to connect to the server...");
        new Thread(() -> {
            createClient();
        }).start();

        System.out.println(stringClient + "Success");


    }

    private static void createClient(){
        try {
            socket = new Socket("localhost", portNumber);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            new Thread(()->{
                while(!socket.isClosed()){
                    try {
                        if (in.available() > 0){
                            System.out.println( "CLIENT > " + in.readUTF());
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
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        out.writeUTF(typeMessage.getText());
        typeMessage.setText("");
    }

}