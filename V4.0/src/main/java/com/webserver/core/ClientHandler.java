package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Map<String,String>headers = new HashMap<>();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            String line = readLine();
            System.out.println("请求行:" + line);
            String[] data = line.split("\\s");
            String method = data[0];
            String uri = data[1];
            String protocol = data[2];
            System.out.println("请求方式：" + method + '\n' + "抽象路径：" + uri + '\n' + "协议版本：" + protocol);
            //读取
            while (true) {
                line = readLine();
                if (line.isEmpty()){
                    break;
                }
                System.out.println(line);
            }
            //把消息头根据： 拆成key和value
            line=readLine();
            String key;
            String value;







            System.out.println("headers"+headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //按行读取，遇到CRLF结束
    private String readLine() throws IOException {
        //对同一个socket调用多次getInputStream方法时，调用的始终是同一个流
        InputStream in = socket.getInputStream();
        int d;
        StringBuilder builder = new StringBuilder();
        char pre = 'a', cur = 'a';
        while ((d = in.read()) != -1) {
            cur = (char) d;
            if (pre == 13 && cur == 10) {
                break;
            }
            builder.append(cur);
            pre = cur;
        }
        return builder.toString().trim();
    }

}