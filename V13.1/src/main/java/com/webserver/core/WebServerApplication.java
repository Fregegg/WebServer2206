package com.webserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Freg
 * @time 2022/8/16  18:39
 */
public class WebServerApplication {
    private ServerSocket serverSocket ;

    public WebServerApplication(){
        try {
            serverSocket = new ServerSocket(8088);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        try {
            while (true) {
                Socket socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(socket);
            Thread thread = new Thread(clientHandler);
            thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebServerApplication webServerApplication = new WebServerApplication();
        webServerApplication.start();
    }
}
