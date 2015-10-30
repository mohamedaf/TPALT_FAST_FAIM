package com.todos.hkboam.todos.bdd.modal;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class Memo {
    private long id;
    private String content;

    public Memo(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
