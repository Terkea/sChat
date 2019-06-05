package com.terkea.system.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server implements Runnable{

    private static int PORT = 4444;
    private final int serverPort;
    private ServerSocket serverSocket = null;
    private String server = "SERVER > ";


    @Override
    public void run(){
        Server server = new Server(PORT);
        server.startServer();

    }

    public Server(int portNumber){
        this.serverPort = portNumber;
    }


    private void startServer(){
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println(server + "ON");
            System.out.println(server + "Waiting for connections...");



//            ACCEPT ALL CONNECTIONS
            while (true){
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println(server + "New connection: " + socket.getRemoteSocketAddress());

                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    new Thread(() -> {
                        while(!socket.isClosed()){
                            try {
                                String input = in.readUTF();
//                                System.out.println("SERVER > " + input);
                                out.writeUTF(input);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(server + "Accept failed");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
