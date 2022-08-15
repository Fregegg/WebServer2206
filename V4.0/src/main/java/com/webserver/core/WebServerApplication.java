package com.webserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * WebServer web容器
 * 用于实现Tomcat基本功能。
 */
public class WebServerApplication {
    private ServerSocket serverSocket;

    public WebServerApplication() {
        try {
            serverSocket = new ServerSocket(8088);
            System.out.println("待连接···");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        try {
            Socket socket = serverSocket.accept();
            System.out.println("一个客户端连接了");
            ClientHandler handler = new ClientHandler(socket);
            Thread thread = new Thread(handler);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebServerApplication webServerApplication = new WebServerApplication();
        webServerApplication.start();
    }
}
