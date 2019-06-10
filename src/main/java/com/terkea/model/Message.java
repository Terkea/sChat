package com.terkea.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

public class Message implements Serializable {
    private String userName;
    private String message;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message(String userName, String message, String type) {
        this.userName = userName;
        this.message = message;
        this.type = type;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    /**CONVERTS THE MESSAGE OBJECT TO JSON AND RETURNS IT AS A STRING
     * @param message
     * @return
     */
    public static String toJSON(Message message){
        ObjectMapper mapper = new ObjectMapper();
        Message test = message;

        String json = null;
        try {
            json = mapper.writeValueAsString(test);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * GETS THE OBJECT FROM A JSON STRING
     * @param message
     * @return
     */
    public static Message fromJSON(String message){
        ObjectMapper mapper = new ObjectMapper();
        Message test = null;
        try {
            test = mapper.readValue(message, Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return test;
    }
}
