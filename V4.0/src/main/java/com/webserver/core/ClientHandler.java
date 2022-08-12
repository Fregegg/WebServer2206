package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/*
    与客户端完成一次HTTP的交互
    一问一答，分为三步完成
    1：解析请求 目的：将浏览器发送的请求内容读取并整理
    2：处理请求 目的：根据浏览器的请求进行对应的处理工作
    3：发送响应 目的：将服务器端的处理结果回馈给浏览器
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
            StringBuilder builder = new StringBuilder();
            char pre='a',cur='a';
            while((d = in.read())!=-1){
                cur=(char) d;
                if (pre==13&&cur==10){
                    break;
                }
                builder.append(cur);
                pre=cur;
            }
            String line = builder.toString().trim();
            System.out.println("请求行"+line);
            //请求行相关信息
            String method;//请求方式
            String uri;//抽象路径
            String protocol;//协议版本
            //'\s'正则表达式  去空白字符
            String[] data = line.split("\\s");
            method = data[0];uri=data[1];protocol=data[2];
            System.out.println("请求方式："+method+'\n'+"抽象路径"+uri+'\n'+"协议版本"+protocol);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}