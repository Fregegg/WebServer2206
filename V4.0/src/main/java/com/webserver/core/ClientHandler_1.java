package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Freg
 * @date 2022/8/14 - 20:34
 */
public class ClientHandler_1 implements Runnable{
    private Socket socket;
    private Map<String,String> headers = new HashMap<>();
    public void ClientHandler_1(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        try {
            String line = readLine();
            String[] data = line.split("\\s");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readLine() throws IOException {
        InputStream is = socket.getInputStream();
        int d;
        StringBuilder line = new StringBuilder();
        char cur = 'q',pre = 'q';
        while((d=is.read())!=-1){
            cur = (char) d;
            if (cur==10&&pre==13){
                break;
            }
            line.append(cur);
            pre=cur;
        }
        return line.toString().trim();
    }
}
