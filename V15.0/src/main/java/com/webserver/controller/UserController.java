package com.webserver.controller;

import com.webserver.entity.User;
import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

import java.io.*;

/**
 * @author Freg
 * @time 2022/8/17  10:37
 */
public class UserController {
    private static File userDir;

    static {
        //判断用户文件夹是否存在并创建
        userDir = new File("./users");
        if (!userDir.exists()){
            userDir.mkdirs();
        }
    }

    public UserController() {}

    public void reg(HttpServletRequest request, HttpServletResponse response) {
        //获取表单信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String ageStr = request.getParameter("age");
        System.out.println(username+"\t"+nickname+"\t"+ageStr+"\t"+password);

        //判断注册信息是否合法，并设置响应正文文件
        if (username==null||username.isEmpty()||
                password==null||password.isEmpty()||
                nickname==null||nickname.isEmpty()||
                ageStr==null||!ageStr.matches("[0-9]+")
        ){
            response.sendRedirect("/reg_info_error.html");
            return;
        }

        int age = Integer.parseInt(ageStr);
        User user = new User(username,password,nickname,age);

        //判断用户已经存在
        File file = new File(userDir,username+".obj");
        if (file.exists()){
            response.sendRedirect("have_user.html");
            return;
        }

        //保存用户信息
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./users/"+username+".obj"));
        ){
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/reg_success.html");
    }

    public void login(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username==null||username.trim().isEmpty()||password==null||password.trim().isEmpty()){
            response.sendRedirect("/login_fail.html");
            return;
        }
        File file = new File(userDir,username+".obj");
        if (file.exists()){
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            ){
                User user = (User) ois.readObject();
                if (password.equals(user.getPassword())){
                    response.sendRedirect("login_success.html");
                    return;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("login_info_error.html");
    }
}
