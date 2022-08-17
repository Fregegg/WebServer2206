package com.webserver.core;

import com.webserver.http.EmptyRequestException;
import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Freg
 * @time 2022/8/17  16:15
 */
public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //解析请求
        try {
            HttpServletRequest request = new HttpServletRequest(socket);
            HttpServletResponse response = new HttpServletResponse(socket);
            DispatcherServlet.getInstance().service(request,response);
            response.response();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmptyRequestException e) {

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
