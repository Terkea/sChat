package com.terkea.model;

public class Client {
    private String name;
    private String public_key;
    private String private_key;
    private String ip;
    private String listening_port;

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

}
