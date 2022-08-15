package com.webserver.http;

import com.webserver.core.ClientHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class HttpServletResponse {
    private Socket socket;
    private static File dir;
    private static File staticDir;

    static {
        try {
            dir = new File(
                    ClientHandler.class.getClassLoader().getResource(".").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        staticDir = new File(dir, "static");
    }

    public HttpServletResponse(Socket socket) {
        this.socket = socket;
    }

    public void sendRedirect(File file){
        try {
            OutputStream os = socket.getOutputStream();
            String line;
            if (!file.isFile()) {
                line = "HTTP/1.1 404 NotFound";
                file = new File(staticDir,"/root/404.html");
            } else {
                line = "HTTP/1.1 200 OK";
            }
            //状态行
            println(line);
            //响应头
            line = "Content-Type: text/html";
            println(line);
            line = "Content-Length: " + file.length();
            println(line);
            println("");
            //响应正文
            FileInputStream fis = new FileInputStream(file);
            byte[] html = new byte[1024 * 10];
            int length = 0;
            while ((length = fis.read(html)) != -1) {
                os.write(html, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void println(String line) throws IOException {
        OutputStream os = socket.getOutputStream();
        byte[] data = line.getBytes(StandardCharsets.ISO_8859_1);
        os.write(data);
        os.write(13);
        os.write(10);
    }
}
