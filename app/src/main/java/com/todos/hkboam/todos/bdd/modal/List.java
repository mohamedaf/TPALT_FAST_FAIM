package com.todos.hkboam.todos.bdd.modal;

import java.io.Serializable;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class List implements Serializable {
    private long id;
    private long modification_date;
    private long author;
    private String title;

    /*public List(String content) {

        String[] tab = content.split(" ");
        if (tab.length == 1) {
            this.title = tab[0];
        } else if (tab.length > 1) {

            this.title = tab[0] + " " + tab[1];
        } else title = "";

    }*/

    public List(String title){
        this.title = title;
    }

    public List(long id, String title) {
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

}
