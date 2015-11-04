package com.todos.hkboam.todos.persistent;

import com.todos.hkboam.todos.bdd.modal.User;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by HK-Lab on 04/11/2015.
 */
public class CurrentUser implements Serializable {
    private static CurrentUser instance;
    private User user;

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private CurrentUser() {
        super();
    }
}
