package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/*
    与客户端完成一次HTTP的交互
 */
public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            /*
                获取浏览器发来的请求
             */
            int d;
            while((d = in.read())!=-1){
                System.out.print((char) d);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println((int)'\r');
    }
}
