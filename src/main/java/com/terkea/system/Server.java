package com.terkea.system;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    private static int PORT = 8888;

    @Override
    public void run(){
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Couldn't listen on port: " + PORT);
            e.printStackTrace();
        }

        System.out.println("Server started");
        System.out.println("Waiting for connections...");
        while(true){
            try {
                serverSocket.accept();
                System.out.print("Somebody connected!");
                System.out.println("Waiting for connections... \n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
