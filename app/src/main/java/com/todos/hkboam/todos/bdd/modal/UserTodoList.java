package com.todos.hkboam.todos.bdd.modal;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class UserTodoList {
    // Notez que l'identifiant est un long
    private long id;
    private long user;
    private long list;
    private long rights;

    public UserTodoList(long id, long user, long list, long rights) {
        this.id = id;
        this.user = user;
        this.list = list;
        this.rights = rights;
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

    public long getList() {
        return list;
    }

    public void setlist(long list) {
        this.list = list;
    }

    public long getRights() {
        return rights;
    }

    public void setRights(long rights) {
        this.rights = rights;
    }
}
