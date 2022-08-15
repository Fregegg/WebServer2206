package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpServletRequest {
    private Socket socket;
    private String method;//请求方式
    private String uri;//抽象路径
    private String protocol;//协议版本
    private Map<String,String>headers = new HashMap<>();

    public HttpServletRequest(Socket socket) throws IOException {
        this.socket = socket;
        parseRequestLine();
        parseHeaders();
        parseContent();
    }

    //解析请求行
    private void parseRequestLine() throws IOException {
        String line = readLine();
        String[] data = line.split("\\s");
        method = data[0];
        uri = data[1];
        protocol = data[2];
        System.out.println("请求行"+line);
        System.out.println("请求方式：" + method + '\n' + "抽象路径：" + uri + '\n' + "协议版本：" + protocol);
    }
    //解析消息头
    private void parseHeaders() throws IOException {
        String line;
        while (true) {
            line = readLine();
            if (line.isEmpty()){ break; }
            String[] datas = line.split(":\\s");
            headers.put(datas[0], datas[1]);
        }
        System.out.println("headers:"+headers);
        Set<Map.Entry<String,String>> headersEntry = headers.entrySet();
            for (Map.Entry<String, String> e : headersEntry) {
                System.out.println("名字： "+e.getKey()+'\t'+"值： "+e.getValue());
            }
        }
    //解析消息正文
    private void parseContent(){

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

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHeaders(String name) {
        return headers.get(name);
    }
}
