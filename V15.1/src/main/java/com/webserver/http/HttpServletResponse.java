package com.webserver.http;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Freg
 * @time 2022/8/17  16:15
 */
public class HttpServletResponse {
    private Socket socket;
    private int statusCode = 200;
    private String statusReason = "OK";
    private File contentFile;
    private Map<String,String>headers = new HashMap<>();

    public HttpServletResponse(Socket socket) {
        this.socket = socket;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public File getContentFile() {
        return contentFile;
    }

    public void setContentFile(File contentFile) {
        this.contentFile = contentFile;
        try {
            String contentType = Files.probeContentType(contentFile.toPath());
            if (contentType!=null){
                addHeader("Content-Type",contentType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        addHeader("Content-Length",contentFile.length()+"");
    }

    public void addHeader(String key,String value){headers.put(key,value);}

    public void sendRedirect(String path){
        statusCode = 302;
        statusReason = "Moved Temporarily";
        addHeader("Location",path);
    }

    private void sendStatusLine() throws IOException {
        println("HTTP/1.1"+"\\S"+statusCode+"\\s"+statusReason);
    }

    private void sendHeaders() throws IOException {
        Set<Map.Entry<String, String>> e = headers.entrySet();
        for (Map.Entry<String, String> es : e) {
            println(es.getKey()+": "+es.getValue());
        }
        println("");

    }

    private void sendContent() throws IOException {
        OutputStream os = socket.getOutputStream();
        if (contentFile!=null){
            FileInputStream fis = new FileInputStream(contentFile);
            byte[] data = new byte[1024*10];
            int len = 0;
            while((len = fis.read(data))!=-1){
                os.write(data,0,len);
            }
        }
    }

    public void response() throws IOException {
        sendStatusLine();
        sendHeaders();
        sendContent();
    }

    private void println(String line) throws IOException {
        OutputStream os = socket.getOutputStream();
        byte[] data = line.getBytes(StandardCharsets.ISO_8859_1);
        os.write(data);
        os.write(13);
        os.write(10);
    }


}
