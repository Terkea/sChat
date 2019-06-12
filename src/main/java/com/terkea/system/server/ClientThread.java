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
    private Client client;
    private ArrayList<Client> otherConnections;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

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
                ", clientName='" + clientName + '\'' +
                ", client=" + client +
                '}';
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());

            while (!socket.isClosed()) {
                try {
                    if (in.available() > 0) {
                        String input = in.readUTF();

                        if (Message.fromJSON(input).getType().equals("REGISTER")) {
                            registerHandler(input);
                        } else if (Message.fromJSON(input).getType().equals("UNREGISTER")) {
                            unregisterHandler(input);
                        } else {
                            outputHandler(input);
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

    public void unregisterHandler(String input) throws IOException {
        Message unregisterMessage = Message.fromJSON(input);
        Client theRequestComesFrom = Client.fromJSON(unregisterMessage.getMessage());
        ClientThread disconnectedClient = null;

        for (ClientThread thatClient : server.getClients()) {
            if (String.valueOf(thatClient.getSocket().getPort()).equals(theRequestComesFrom.getListening_port()) &&
                    thatClient.getSocket().getInetAddress().toString().equals(theRequestComesFrom.getIp())) {
                unregisterHandler(thatClient);
            }
        }
//        unregisterHandler(disconnectedClient);
    }

    public void unregisterHandler(ClientThread faultyClient) throws IOException {

        if (faultyClient != null) {
            try {
                Message disconnect = new Message("SERVER", Client.toJSON(faultyClient.getClient()), "UNREGISTER");

                for (ClientThread thatClient : server.getClients()) {
                    DataOutputStream outputParticularClient = new DataOutputStream(thatClient.getSocket().getOutputStream());
                    outputParticularClient.writeUTF(Message.toJSON(disconnect));
                }

                faultyClient.getSocket().getOutputStream().close();
                faultyClient.getSocket().getInputStream().close();
                faultyClient.getSocket().close();
            } catch (SocketException se) {

            }
            server.getClients().remove(faultyClient);
        }
    }


    /**
     * Once a client connects he sends a registration message
     * This method sets the ip and the listening port for that client object
     * Then the
     *
     * @param input Message
     * @return Message
     */
    public void registerHandler(String input) {
        Message specialMessage = Message.fromJSON(input);
        specialMessage.setType("REGISTER");

        Client test = Client.fromJSON(specialMessage.getMessage());
        test.setIp(this.socket.getInetAddress().toString());
        test.setListening_port(String.valueOf(this.socket.getPort()));

        for (ClientThread thatClient : server.getClients()) {
            if (String.valueOf(thatClient.getSocket().getPort()).equals(test.getListening_port()) &&
                    thatClient.getSocket().getInetAddress().toString().equals(test.getIp())) {
                //SERVER SIDE
                specialMessage.setMessage(Client.toJSON(test));
                thatClient.setClient(test);
            }
        }
        specialMessage.setMessage(Client.toJSON(test));

        try {
//            outputHandler(Message.toJSON(specialMessage));
            for (ClientThread thatClient : server.getClients()) {
                Message otherConnections = new Message(thatClient.getClient().getName(), Client.toJSON(thatClient.getClient()), "REGISTER");
                outputHandler(Message.toJSON(otherConnections));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the received message to all connected clients,
     * If one client disconnects the socket and i/o streams will be closed and
     * then it will be removed from the client list which can be found in Server Class
     *
     * @param input Message
     * @throws IOException
     */
    public void outputHandler(String input) throws IOException {
        ClientThread faultyClient = null;
        for (ClientThread thatClient : server.getClients()) {
            DataOutputStream outputParticularClient = new DataOutputStream(thatClient.getSocket().getOutputStream());
            try {
                outputParticularClient.writeUTF(input);
            } catch (SocketException se) {
                faultyClient = thatClient;
            }
        }
        if (faultyClient != null){
            unregisterHandler(faultyClient);
        }
    }
}
