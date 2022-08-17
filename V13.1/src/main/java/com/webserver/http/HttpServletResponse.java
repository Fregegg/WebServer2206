package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Freg
 * @time 2022/8/16  18:40
 *
 * 响应对象
 * 该类的每一个实例用于表示一个HTTP协议要求的响应内容
 * 每个响应由三部分构成:
 * 状态行,响应头,响应正文
 */
public class HttpServletResponse {
    private Socket socket;
    private int statusCode = 200;
    private String statusDescription = "OK";
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

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public File getContentFile() {
        return contentFile;
    }

    public void setContentFile(File contentFile) {
        this.contentFile = contentFile;
        try {
            String contentType = Files.probeContentType(contentFile.toPath());
            if (contentType!=null){
                addHeaders("ContentType",contentType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        addHeaders("Content-Length",contentFile.length()+"");
    }

    public void response() throws IOException {
        sendStatusLine();
        sendHeader();
        sendContent();
    }

    private void sendStatusLine() throws IOException {
        println("HTTP/1.1"+" "+statusCode+" "+statusDescription);
    }

    private void sendHeader() throws IOException {
        Set<Map.Entry<String, String>> e = headers.entrySet();
        for (Map.Entry<String, String> es : e) {
//            println(es.getKey()+" "+es.getValue());
            String name = es.getKey();
            String value = es.getValue();
            println(name + ": " + value);
        }
        println("");
    }

    private void sendContent() throws IOException {
        OutputStream os = socket.getOutputStream();
        FileInputStream fis = new FileInputStream(contentFile);
        byte[] data = new byte[1024*10];
        int line =0;
        while((line = fis.read(data))!=-1){
            os.write(data,0,line);
        }
    }

    private void addHeaders(String name,String value){
        headers.put(name,value);
    }

    private void println(String line) throws IOException {
        OutputStream os = socket.getOutputStream();
        byte[] data = line.getBytes(StandardCharsets.ISO_8859_1);
        os.write(data);
        os.write(13);
        os.write(10);

    }
}
