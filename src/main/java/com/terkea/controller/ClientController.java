package com.terkea.controller;

import com.terkea.model.Client;
import com.terkea.model.Message;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.Socket;

import static javafx.geometry.Pos.*;


public class ClientController {

    @FXML
    private TextArea typeMessage;

    @FXML
    private VBox displayChat = new VBox();

    @FXML
    private VBox connectedUsers = new VBox();

    @FXML
    private ScrollPane chatScrollPane;


    @FXML
    private ScrollPane usersScrollPane;


    private static String userName;
    private static Client client;
    private static final int portNumber = 4444;
    public String host;
    private static Socket socket;
    private DataOutputStream out;
    public static ObservableList<Message> chat = FXCollections.observableArrayList();
    public static ObservableList<Client> allClientsConnected = FXCollections.observableArrayList();

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

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        ClientController.client = client;
    }

    private void setStyleForOtherClient(Label message, Label name){
        String style = "-fx-font-size: 14;" +
                "-fx-background-color:  #e7e6ec;" +
                "-fx-background-radius: 0 5 15 10;";
        message.setStyle(style);
        message.setMinWidth(chatScrollPane.getWidth()-5);
        message.setWrapText(true);
        message.setPadding(new Insets(10, 10, 10, 10));

        String clientNameStyle = "-fx-font-size: 12;";
        name.setMinWidth(chatScrollPane.getWidth()-5);
        name.setStyle(clientNameStyle);
        name.setAlignment(CENTER_LEFT);
    }

    private void setStyleForMyClient(Label message, Label name){
        String style = "-fx-font-size: 14;" +
                "-fx-background-color:   #79BED9;" +
                "-fx-background-radius: 5 0 10 15;";
        message.setStyle(style);
        message.setMinWidth(chatScrollPane.getWidth()-5);
        message.setWrapText(true);
        message.setPadding(new Insets(10, 10, 10, 10));
        message.setTextFill(Color.WHITE);

        String clientNameStyle = "-fx-font-size: 12;";
        name.setMinWidth(chatScrollPane.getWidth()-5);
        name.setStyle(clientNameStyle);
        name.setAlignment(CENTER_RIGHT);
    }

    private void setStyleForUsers(Label user){
        String style = "-fx-font-size: 12;";

        user.setPadding(new Insets(10, 10, 10, 10));
        String clientNameStyle = "-fx-font-size: 12;";
        user.setMinWidth(usersScrollPane.getWidth()-5);
        user.setStyle(clientNameStyle);
        user.setAlignment(CENTER);
        user.setWrapText(true);
    }



    @FXML
    public void initialize(){
        chat.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                Platform.runLater(()->{
                    chatScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//                    System.out.println("LAST MESSAGE: " + chat.get(chat.size()-1).getUserName() + " > " +chat.get(chat.size()-1).getMessage() );
                    Label user = new Label(chat.get(chat.size()-1).getUserName());
                    Label message = new Label(chat.get(chat.size()-1).getMessage());

                    if (chat.get(chat.size()-1).getUserName().equals(getUserName())){
                        setStyleForMyClient(message, user);
                    }else{
                        setStyleForOtherClient(message, user);
                    }

                    displayChat.setMargin(message, new Insets(0, 0,10,0));

                    displayChat.getChildren().add(user);
                    displayChat.getChildren().add(message);
                });
            }
        });

        allClientsConnected.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                Platform.runLater(()->{
                    usersScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//                    System.out.println("LAST MESSAGE: " + chat.get(chat.size()-1).getUserName() + " > " +chat.get(chat.size()-1).getMessage() );
                    Label user = new Label(allClientsConnected.get(allClientsConnected.size()-1).getName());
                    setStyleForUsers(user);

                    connectedUsers.getChildren().add(user);
                });
            }
        });
    }


    @FXML
    public void loadUser(String host, String userName){
        setUserName(userName);
        setHost(host);
        setClient(new Client(getUserName()));

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
            out = new DataOutputStream(socket.getOutputStream());
            Message registerclient = new Message("REGISTER", Client.toJSON(getClient()));
            out.writeUTF(Message.toJSON(registerclient));

            new Thread(()->{

                while(!socket.isClosed()){
                    try {
                        if (in.available() > 0){
                            String input = in.readUTF();
                            Message inputMessage = Message.fromJSON(input);
                            if (inputMessage.getUserName().equals("SERVER")){
                                System.err.println(Client.fromJSON(inputMessage.getMessage()));
                                allClientsConnected.add(Client.fromJSON(inputMessage.getMessage()));
                            }else{
                                chat.add(inputMessage);
                            }
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