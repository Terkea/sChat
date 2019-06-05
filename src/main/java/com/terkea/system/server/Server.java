package com.terkea.system.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable{

    private static int PORT = 4444;
    private final int serverPort;
    private ServerSocket serverSocket = null;
    private List<ClientThread> clients; // or "protected static List<ClientThread> clients;"


    @Override
    public void run(){
        Server server = new Server(PORT);
        server.startServer();

    }

    public List<ClientThread> getClients(){
        return clients;
    }

    public Server(int portNumber){
        this.serverPort = portNumber;
    }


    private void startServer(){
        clients = new ArrayList<ClientThread>();
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("SERVER ON");
            System.out.println("SERVER > Waiting for connections...");


//            ACCEPT ALL CONNECTIONS
            while (true){
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("SERVER > New connection: " + socket.getRemoteSocketAddress());
                    ClientThread client = new ClientThread(this, socket);
                    Thread thread = new Thread(client);
                    thread.start();
                    clients.add(client);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("SERVER > Accept failed");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
