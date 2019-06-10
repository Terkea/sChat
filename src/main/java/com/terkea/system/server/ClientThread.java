package com.terkea.system.server;

import com.terkea.model.Client;
import com.terkea.model.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread implements Runnable {

    private Socket socket;
    private Server server;
    private String clientName;
    private ArrayList<Client> otherConnections = new ArrayList<>();

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
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

            while (!socket.isClosed()) {
                try {
                    if (in.available() > 0) {
                        String input = in.readUTF();
                        if (Message.fromJSON(input).getUserName().equals("REGISTER")){
                            Message oldConnection = null;
                            if (otherConnections.size() >= 1){
                                for (int i = 0; i<otherConnections.size(); i++){
                                oldConnection.setUserName("SERVER");
                                oldConnection.setMessage(Client.toJSON(otherConnections.get(i)));

                                DataOutputStream thisClient = new DataOutputStream(socket.getOutputStream());
                                thisClient.writeUTF(Message.toJSON(oldConnection));
                                }
                            }

                            Message specialMessage = Message.fromJSON(input);
                            specialMessage.setUserName("SERVER");

                            Client test = Client.fromJSON(specialMessage.getMessage());
                            test.setIp(socket.getInetAddress().toString());
                            test.setListening_port(String.valueOf(socket.getPort()));
                            specialMessage.setMessage(Client.toJSON(test));

                            input = Message.toJSON(specialMessage);

                        }
                        for (ClientThread thatClient : server.getClients()){
                            DataOutputStream outputParticularClient = new DataOutputStream(thatClient.getSocket().getOutputStream());
                            outputParticularClient.writeUTF(input);
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
