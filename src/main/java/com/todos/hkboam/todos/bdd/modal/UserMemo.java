package com.todos.hkboam.todos.bdd.modal;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class UserMemo {
    // Notez que l'identifiant est un long
    private long id;
    private long user;
    private long memo;

    public UserMemo(long id, long user, long memo) {
        this.id = id;
        this.user = user;
        this.memo = memo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getMemo() {
        return memo;
    }

    public void setMemo(long memo) {
        this.memo = memo;
    }
}
