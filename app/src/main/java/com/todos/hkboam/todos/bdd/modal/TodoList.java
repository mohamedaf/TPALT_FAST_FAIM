package com.todos.hkboam.todos.bdd.modal;

import java.io.Serializable;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class TodoList implements Serializable, Comparable<TodoList> {
    private long id;
    private long modification_date;
    private long author;
    private String title;

    public TodoList(String title) {
        this.title = title;
    }

    public TodoList(long id, long modification_date, long author, String title) {
        this.id = id;
        this.modification_date = modification_date;
        this.author = author;
        this.title = title;
    }

    public TodoList(long modification_date, long author, String title) {
        this.modification_date = modification_date;
        this.author = author;
        this.title = title;
    }

    public TodoList(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getModification_date() {
        return modification_date;
    }

    public void setModification_date(long modification_date) {
        this.modification_date = modification_date;
    }

    public long getAuthor() {
        return author;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(TodoList another) {
        if (another.getModification_date() < this.modification_date) {
            return -1;
        }
        if (another.getModification_date() > this.modification_date) {
            return 1;
        }
        return 0;
    }
}
