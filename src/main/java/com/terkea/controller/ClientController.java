package com.terkea.controller;

import com.terkea.model.Message;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class ClientController {
    @FXML
    private VBox testVBOX = new VBox();

    @FXML
    private TextArea typeMessage;

    @FXML
    private ScrollPane connectedUsersPane;

    private static String userName;
    private static final int portNumber = 4444;
    public String host;
    private static Socket socket;
    private DataOutputStream out;
    public static ObservableList<Message> chat = FXCollections.observableArrayList();

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
    public void initialize(){
        chat.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                Platform.runLater(()->{
                    System.out.println("LAST MESSAGE: " + chat.get(chat.size()-1).getUserName() + " > " +chat.get(chat.size()-1).getMessage() );
                    Label test = new Label(chat.get(chat.size()-1).getMessage());
                    testVBOX.getChildren().add(test);
                });
            }
        });
    }


    @FXML
    public void loadUser(String host, String userName){


        setUserName(userName);
        setHost(host);
        System.err.println(getUserName());
        System.err.println("HOST ADDRESS: " + getHost() + ":" +portNumber + "\n");
        System.out.println(getUserName() + " > Trying to connect to the server...");

        new Thread(() -> {
            createClient();
        }).start();

        System.out.println(getUserName() + " > Connected");



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
                            Message inputMessage = Message.fromJSON(input);
//                            System.out.println(inputMessage.getUserName() + " > " + inputMessage.getMessage());
                            chat.add(inputMessage);
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
            Message newMessage = new Message(getUserName(), typeMessage.getText());
            out.writeUTF(Message.toJSON(newMessage));
            typeMessage.setText("");
        }
    }

}