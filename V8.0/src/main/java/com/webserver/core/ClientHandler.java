package com.webserver.core;

import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;

/**
 * 与客户端完成一次HTTP的交互
 * 按照HTTP协议要求，与客户端完成一次交互流程为一问一答
 * 因此，这里分为三步完成该工作:
 * 1:解析请求  目的:将浏览器发送的请求内容读取并整理
 * 2:处理请求  目的:根据浏览器的请求进行对应的处理工作
 * 3:发送响应  目的:将服务端的处理结果回馈给浏览器
 */
public class ClientHandler implements Runnable {
    private static File dir;
    private static File staticDir;

    /*
    本版本测试,将resources下的static目录中的index.html页面响应给浏览器
    实际虚拟机执行是查看的是target/class目录下的内容
    maven项目编译后会将src/main/java和src/main/resources下的内容合并放在target/classes下
    因此我们实际要定位的是target/classes/static/index.html
    类加载路径
    定位环境变量ClassPath中"."的位置
    在idea中执行项目时，类加载路径是从target/classes开始的
    */
    static {
        //定位classes目录
        try {
            dir = new File(
                    ClientHandler.class.getClassLoader().getResource(".").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //定位target/classes/static目录
        staticDir = new File(dir, "static");
    }

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //1解析请求，实例化请求对象的过程就是解析的过程
            HttpServletRequest request = new HttpServletRequest(socket);
            HttpServletResponse response = new HttpServletResponse(socket);
            //2处理请求
            String path = request.getUri();
            System.out.println("请求的抽象路径" + path);
            File file = new File(staticDir, path);
            if (!file.isFile()) {
                response.setStatusCode(404);
                response.setStatusDescription("NotFound");
                file = new File(staticDir,"/root/404.html");
            }
            response.setContentFile(file);
            //3发送响应
            response.sendRedirect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}