package com.webserver.controller;

import com.webserver.entity.User;
import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

import java.io.*;

/**
 * @author Freg
 * @time 2022/8/17  16:14
 */
public class UserController {
    private static File userDir;

    static{
        userDir = new File("./users");
        if (!userDir.exists()){
            userDir.mkdirs();
        }
    }

    public void reg(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameters("username");
        String nickname = request.getParameters("nickname");
        String password = request.getParameters("password");
        String ageStr = request.getParameters("age");

        if (username==null||username.trim().isEmpty()||
                nickname==null||nickname.trim().isEmpty()||
                password==null||password.trim().isEmpty()||
                ageStr==null||!ageStr.matches("[0-9]+")
        ) {
            response.sendRedirect("/reg_info_error.html");
            return;
        }

        int age = Integer.valueOf(ageStr);
        User user = new User(username,nickname,password,age);

        File file = new File(userDir,username+".obj");
        if (file.exists()){
            response.sendRedirect("/have_user.html");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))
        ) {
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.sendRedirect("reg_success.html");
    }

    public void login(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameters("username");
        String password = request.getParameters("password");
        if (username==null||username.trim().isEmpty()||password==null||password.trim().isEmpty()){
            response.sendRedirect("/login_fail.html");
            return;
        }

        File file = new File(userDir,username+".obj");
        if (file.exists()){
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                User user = (User) ois.readObject();
                if (password.equals(user.getPassword())) {
                    response.sendRedirect("login_success.html");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("login_info_error.html");
    }
}
