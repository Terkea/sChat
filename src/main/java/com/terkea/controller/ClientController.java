package com.terkea.controller;

import com.terkea.model.Message;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.net.Socket;

import static javafx.geometry.Pos.BOTTOM_CENTER;
import static javafx.geometry.Pos.CENTER_RIGHT;


public class ClientController {

    @FXML
    private TextArea typeMessage;

    @FXML
    private VBox displayChat = new VBox();

    @FXML
    private VBox connectedUsers = new VBox();

    @FXML
    private ScrollPane chatScrollPane;


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

    private void setStyleForOtherClientText(Label label){
        String clientTextStyle = "-fx-font-size: 14;" +
                "-fx-background-color:  #e7e6ec;" +
                "-fx-background-radius: 0 5 15 10;";
        label.setStyle(clientTextStyle);
        label.setMinWidth(chatScrollPane.getWidth()-5);
        label.setStyle(clientTextStyle);
        label.setWrapText(true);
    }

    private void setStyleForMyClientText(Label label){
        String clientTextStyle = "-fx-font-size: 14;" +
                "-fx-background-color:   #79BED9;" +
                "-fx-background-radius: 5 0 10 15;";
        label.setStyle(clientTextStyle);
        label.setMinWidth(chatScrollPane.getWidth()-5);
        label.setStyle(clientTextStyle);
        label.setWrapText(true);
        label.setPadding(new Insets(10, 10, 10, 10));
        label.setTextFill(Color.WHITE);
    }

    private void setStyleForOtherClientName(Label label){
        String clientNameStyle = "-fx-font-size: 12;";
        label.setMinWidth(chatScrollPane.getWidth()-5);
        label.setStyle(clientNameStyle);
        label.setAlignment(CENTER_RIGHT);

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
                    setStyleForOtherClientName(user);
                    Label message = new Label(chat.get(chat.size()-1).getMessage());
                    setStyleForMyClientText(message);
                    
                    displayChat.setMargin(message, new Insets(0, 0,10,0));

                    displayChat.getChildren().add(user);
                    displayChat.getChildren().add(message);
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