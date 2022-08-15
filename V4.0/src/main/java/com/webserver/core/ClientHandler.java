package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * 与客户端完成一次HTTP的交互
 * 按照HTTP协议要求，与客户端完成一次交互流程为一问一答
 * 因此，这里分为三步完成该工作:
 * 1:解析请求  目的:将浏览器发送的请求内容读取并整理
 * 2:处理请求  目的:根据浏览器的请求进行对应的处理工作
 * 3:发送响应  目的:将服务端的处理结果回馈给浏览器
 *
 */
public class ClientHandler implements Runnable {
    private Socket socket;
    private Map<String,String>headers = new HashMap<>();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //输出请求行
            String line = readLine();
            System.out.println("请求行:" + line);

            //拆分请求行并输出
            String[] data = line.split("\\s");
            String method = data[0];
            String uri = data[1];
            String protocol = data[2];
            System.out.println("请求方式：" + method + '\n' + "抽象路径：" + uri + '\n' + "协议版本：" + protocol);

            //读取并输出消息头
            while (true) {
                line = readLine();
                if (line.isEmpty()){
                    break;
                }
                String[] datas = line.split(":\\s");
                headers.put(datas[0], datas[1]);
                System.out.println(line);
            }

            //按key和value输出消息头
            Set<Map.Entry<String,String>>headersEntry = headers.entrySet();
            for (Map.Entry<String, String> e : headersEntry) {
                System.out.println("名字： "+e.getKey()+'\t'+"值： "+e.getValue());
            }

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