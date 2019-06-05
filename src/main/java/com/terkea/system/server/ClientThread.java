package com.terkea.system.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread implements Runnable {

    private Socket socket;
    private Server server;
    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public ClientThread(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("HI FROM SERVER");
            while (!socket.isClosed()) {
                try {
                    if (in.available() > 0) {
                        String input = in.readUTF();
                        // UNCOMMENT TO READ ON SERVER
                        // System.out.println("SERVER > " + input);
                        for (ClientThread thatClient : server.getClients()){
                            out.writeUTF(input + "GOT FROM SERVER");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}