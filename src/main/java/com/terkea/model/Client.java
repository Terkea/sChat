package com.terkea.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

public class Client implements Serializable {
    private String name;
    private String public_key;
    private String private_key;
    private String ip;
    private String listening_port;

    public Client() {
    }

    public Client(String name, String public_key, String private_key, String ip, String listening_port) {
        this.name = name;
        this.public_key = public_key;
        this.private_key = private_key;
        this.ip = ip;
        this.listening_port = listening_port;
    }

    public Client(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getListening_port() {
        return listening_port;
    }

    public void setListening_port(String listening_port) {
        this.listening_port = listening_port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", public_key='" + public_key + '\'' +
                ", private_key='" + private_key + '\'' +
                ", ip='" + ip + '\'' +
                ", listening_port='" + listening_port + '\'' +
                '}';
    }

    /**CONVERTS THE MESSAGE OBJECT TO JSON AND RETURNS IT AS A STRING
     * @param client
     * @return
     */
    public static String toJSON(Client client){
        ObjectMapper mapper = new ObjectMapper();
        Client test = client;

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
     * @param clientString
     * @return
     */
    public static Client fromJSON(String clientString){
        ObjectMapper mapper = new ObjectMapper();
        Client test = null;
        try {
            test = mapper.readValue(clientString, Client.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return test;
    }

}
