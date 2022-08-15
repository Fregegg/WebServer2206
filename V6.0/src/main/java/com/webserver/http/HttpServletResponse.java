package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServletResponse {
    private Socket socket;

    public HttpServletResponse(Socket socket) {
        this.socket = socket;
    }

    public void sendRedirect(File file){
        try {
            OutputStream os = socket.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            String line = "HTTP/1.1 200 OK";
            byte[] data = line.getBytes(StandardCharsets.ISO_8859_1);
            os.write(data);
            os.write(13);
            os.write(10);
            line = "Content-Type: text/html";
            data = line.getBytes(StandardCharsets.ISO_8859_1);
            os.write(data);
            os.write(13);
            os.write(10);
            line = "Content-Length: "+file.length();
            data = line.getBytes(StandardCharsets.ISO_8859_1);
            os.write(data);
            os.write(13);
            os.write(10);
            os.write(13);
            os.write(10);
            byte[] html = new byte[1024*10];
            int length = 0;
            while((length=fis.read(html))!=-1){
                os.write(html,0,length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
