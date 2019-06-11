package com.terkea.system.server;

import com.terkea.model.Client;
import com.terkea.model.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ClientThread implements Runnable {

    private Socket socket;
    private Server server;
    private String clientName;
    private ArrayList<Client> otherConnections;

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
    public String toString() {
        return "ClientThread{" +
                "socket=" + socket +
                ", server=" + server +
                '}';
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

                        if (Message.fromJSON(input).getType().equals("REGISTER")) {
                            input = registerHandler(input);
                        }else if (Message.fromJSON(input).getType().equals("UNREGISTER")) {
                             unregisterHandler(input);
                        }

                        outputHandler(input);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unregisterHandler(String input) {

    }

    /**
     * Once a client connects he sends a registration message
     * This method sets the ip and the listening port for that client object
     * @param input Message
     * @return Message
     */
    public String registerHandler(String input) {
        Message specialMessage = Message.fromJSON(input);
        specialMessage.setType("REGISTER");

        Client test = Client.fromJSON(specialMessage.getMessage());
        test.setIp(this.socket.getInetAddress().toString());
        test.setListening_port(String.valueOf(this.socket.getPort()));
        specialMessage.setMessage(Client.toJSON(test));

        return Message.toJSON(specialMessage);
    }

    /**
     * Sends the received message to all connected clients,
     * If one client disconnects then it will be removed from the client list which can be found in Server Class
     * @param input Message
     * @throws IOException
     */
    public void outputHandler(String input) throws IOException {
        ClientThread faultyClient = null;
        for (ClientThread thatClient : server.getClients()) {
            DataOutputStream outputParticularClient = new DataOutputStream(thatClient.getSocket().getOutputStream());
            try{
                outputParticularClient.writeUTF(input);
            }catch (SocketException se){
                System.out.println("this is the faulty client: " + thatClient.toString());
                faultyClient = thatClient;
            }
        }
        if (faultyClient != null){
            server.getClients().remove(faultyClient);
            System.out.println("this is the faulty client: " + faultyClient.toString());
            System.out.println("Client removed from list");
        }
    }
}
