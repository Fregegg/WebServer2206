package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServletResponse {
    private Socket socket;
    private File contentFile;
    private String statusDescription = "OK";
    private int statusCode = 200;

    public HttpServletResponse(Socket socket) {
        this.socket = socket;
    }

    public void sendRedirect() throws IOException {
        sendStatusLine();
        sendHeaders();
        sendContent();

    }

    //状态行
    private void sendStatusLine() throws IOException {
        println("HTTP/1.1\\s" + statusCode + "\\s" + statusDescription);
    }

    //响应头
    private void sendHeaders() throws IOException {
        println("Content-Type: text/html");
        println("Content-Length:\\s" + contentFile.length());
        println("");
    }

    //响应正文
    private void sendContent() throws IOException {
        OutputStream os = socket.getOutputStream();
        FileInputStream fis = new FileInputStream(contentFile);
        byte[] html = new byte[1024 * 10];
        int length = 0;
        while ((length = fis.read(html)) != -1) {
            os.write(html, 0, length);
        }
    }

    private void println(String line) throws IOException {
        OutputStream os = socket.getOutputStream();
        byte[] data = line.getBytes(StandardCharsets.ISO_8859_1);
        os.write(data);
        os.write(13);
        os.write(10);
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public File getContentFile() {
        return contentFile;
    }

    public void setContentFile(File contentFile) {
        this.contentFile = contentFile;
    }
}
