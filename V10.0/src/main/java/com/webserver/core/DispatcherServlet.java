package com.webserver.core;

import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @author Freg
 * @time 2022/8/16  10:45
 * <p>
 * 此类用于完成一个http交互流程中处理请求的环节
 * 这个类是Spring MVC框架提供的一个核心的类，用于和Web(Tomcat)容器整合
 * 使得处理请求的环节可以由SpringMVC框架完成
 */
public class DispatcherServlet {//调度服务器
    private static File dir;
    private static File staticDir;
    private static DispatcherServlet dispatcherServlet = new DispatcherServlet();

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

    private DispatcherServlet() {}

    public static DispatcherServlet getInstance() {
        return dispatcherServlet;
    }

    /**
     * @param request  请求对象，获取浏览器提交的内容
     * @param response 响应对象，将处理结果发送给浏览器
     */
    public void service(HttpServletRequest request, HttpServletResponse response) {
        File file = new File(staticDir, request.getUri());
        if (!file.isFile()) {
            response.setStatusCode(404);
            response.setStatusDescription("NotFound");
            file = new File(staticDir, "/root/404.html");
        }
        //添加响应正文
        response.setContentFile(file);
        //添加响应头
        response.addHeaders("Content-Type","text/html" );
        response.addHeaders("Content-Length",file.length()+"");

    }
}
