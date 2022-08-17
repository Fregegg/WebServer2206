package com.webserver.core;

import com.webserver.controller.UserController;
import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @author Freg
 * @time 2022/8/17  16:15
 */
public class DispatcherServlet {
    private static DispatcherServlet Instance = new DispatcherServlet();
    private static File dir ;
    private static File staticDir;
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
        return Instance;
    }

    public void service(HttpServletRequest request, HttpServletResponse response){
        UserController controller = new UserController();
        switch (request.getRequestURI()){
            case "/regUser":
                controller.reg(request,response);
                break;
            case "/loginUser":
                controller.login(request, response);
                break;
            default:
                File file = new File(staticDir,request.getRequestURI());
                if (file.isFile()){
                    response.setContentFile(file);
                } else {
                    response.setStatusCode(404);
                    response.setStatusReason("NotFound");
                    file = new File(staticDir,"/root/404.html");
                    response.setContentFile(file);
                }
        }
    }
}
