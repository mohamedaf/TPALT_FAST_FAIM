package com.todos.hkboam.todos.bdd.modal;

import com.todos.hkboam.todos.Utils.Util;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class User {
    private long id;
    private String username;
    private String mail;
    private String password;

    public User(long id, String username, String mail, String password) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return Util.MD5(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
