package com.webserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Freg
 * @time 2022/8/17  16:15
 */
public class WebserverApplication {
    private ServerSocket serverSocket;
    public WebserverApplication(){
        try {
            serverSocket = new ServerSocket(8088);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start(){
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket);
                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebserverApplication webserverApplication = new WebserverApplication();
        webserverApplication.start();
    }
}
