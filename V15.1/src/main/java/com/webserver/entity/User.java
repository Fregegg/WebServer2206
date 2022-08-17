package com.webserver.entity;

/**
 * @author Freg
 * @time 2022/8/17  16:15
 */
public class User {
    private String username;
    private String nickname;
    private String password;
    private int age;

    public User(String username, String nickname, String password, int age) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
