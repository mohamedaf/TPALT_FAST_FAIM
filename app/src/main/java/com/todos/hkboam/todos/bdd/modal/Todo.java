package com.todos.hkboam.todos.bdd.modal;

import android.util.Log;

/**
 * Created by zomboris on 11/3/15.
 */
public class Todo {
    private long id;
    private long modification_date;
    private long list;
    private long author;
    private String content;
    private int done;

    public Todo(String content) {
        this.content = content;
    }

    public Todo(long id, long modification_date, long list, long author, String content) {
        this.id = id;
        this.modification_date = modification_date;
        this.list = list;
        this.author = author;
        this.content = content;
        this.done = 0;
    }

    public Todo(long modification_date, long list, long author, String content) {
        this.modification_date = modification_date;
        this.list = list;
        this.author = author;
        this.content = content;
        this.done = 0;
    }

    public Todo(long id, long modification_date, long list, long author, String content, int done) {

        this.id = id;
        this.modification_date = modification_date;
        this.list = list;
        this.author = author;
        this.content = content;
        this.done = done;
    }

    public Todo(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getDone() {
        Log.i("Item getDone : " + id, "done : " + (done == 1));
        return done;
    }

    public void setDone(int done) {
        Log.i("Item setDone : " + id, "done : " + (done == 1));
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getModification_date() {
        return modification_date;
    }

    public void setModification_date(long modification_date) {
        this.modification_date = modification_date;
    }

    public long getList() {
        return list;
    }

    public void setList(long list) {
        this.list = list;
    }

    public long getAuthor() {
        return author;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
