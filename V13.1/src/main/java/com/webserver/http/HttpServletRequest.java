package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Freg
 * @time 2022/8/16  18:40
 *
 * 解析请求
 */
public class HttpServletRequest {
    private Socket socket;
    private String method;
    private String protocol;
    private String uri;
    private String requestURI;
    private String queryString;
    private Map<String,String > headers = new HashMap<>();
    private Map<String,String > parameters = new HashMap<>();

    public String getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUri() {
        return uri;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpServletRequest(Socket socket) throws IOException, EmptyRequestException {
        this.socket = socket;
        parseRequestLine();
        parseHeader();
        parseContent();
    }

    private void parseRequestLine() throws IOException, EmptyRequestException {
        String line = readLine();
        if (line.isEmpty()){
            throw new EmptyRequestException();
        }
        String[]data = line.split("\\s");
        method = data[0];
        uri = data[1];
        protocol = data[2];
        parseURI();
        System.out.println("请求行："+line);
    }

    private void parseURI(){
        if (uri.contains("\\?")){
            String[] path_1 = uri.split("\\?");
            requestURI = path_1[0];
            queryString = path_1[1];
            String[] path_2 = queryString.split("&");
            for (int i = 0; i < path_2.length; i++) {
                String[] path_3 = path_2[i].split("=");
                parameters.put(path_3[0],path_3.length>1?path_3[1]:"");
            }
        } else {
            requestURI = uri;
        }
    }

    private void parseHeader() throws IOException {
        while(true){
            String line = readLine();
            if (line.isEmpty()){
                break;
            }
            System.out.println("消息头："+line);
            String[] data = line.split(":\\s");
            headers.put(data[0],data[1]);
        }
    }

    private void parseContent(){}

    private String readLine() throws IOException {
        InputStream is = socket.getInputStream();
        int d;
        char cur='a',pre='a';
        StringBuilder builder = new StringBuilder();
        while((d = is.read())!=-1){
            cur = (char)d;
            if (cur==10&&pre==10){
                break;
            }
            pre=cur;
            builder.append(cur);
        }
        return builder.toString().trim();
    }
}

