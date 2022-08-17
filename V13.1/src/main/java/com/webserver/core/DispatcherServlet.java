package com.webserver.core;

import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @author Freg
 * @time 2022/8/16  18:40
 *
 * 处理请求
 * 用于完成一个http交互流程中处理请求的环节工作.
 * 实际上这个类是Spring MVC框架提供的一个核心的类,用于和Web容器(Tomcat)整合,
 * 使得处理请求的环节可以由Spring MVC框架完成.
 */
public class DispatcherServlet {
    private static File dir ;
    private static File staticDir;
    private static DispatcherServlet instance = new DispatcherServlet();

    static{
        try {
            dir = new File(DispatcherServlet.class.getClassLoader().getResource(".").toURI());
            staticDir = new File(dir,"static");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private DispatcherServlet() {}

    public static DispatcherServlet getInstance(){
        return instance;
    }

    public void service(HttpServletRequest request, HttpServletResponse response){
        File file = new File(staticDir,request.getUri());
        System.out.println("请求的路径："+request.getUri());
        if (file.isFile()){
            response.setContentFile(file);
        } else {
            response.setStatusCode(404);
            response.setStatusDescription("NotFound");
            file = new File(staticDir,"/root/404.html");
            response.setContentFile(file);
        }
    }
}
