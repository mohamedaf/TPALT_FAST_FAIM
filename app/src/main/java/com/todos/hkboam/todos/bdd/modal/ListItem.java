package com.todos.hkboam.todos.bdd.modal;

/**
 * Created by zomboris on 11/3/15.
 */
public class ListItem {
    private long id;
    private long modification_date;
    private long list;
    private long author;
    private String content;

    public ListItem(String content){
        this.content = content;
    }

    public ListItem(long id, String content){
        this.id = id;
        this.content = content;
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
