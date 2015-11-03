package com.todos.hkboam.todos.bdd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.todos.hkboam.todos.bdd.modal.User;


/**
 * Created by mohamedamin on 18/10/2015.
 */
public class UserDAO extends DAOBase {
    public static final String KEY = "id";
    public static final String USERNAME = "username";
    public static final String MAIL = "mail";
    public static final String PASSWORD = "password";
    public static final String TABLE_NAME = "user";

    public UserDAO(Context pContext) {
        super(pContext);
    }

    /**
     * @param u le métier à ajouter à la base
     */
    public void ajouter(User u) {
        ContentValues value = new ContentValues();
        value.put(USERNAME, u.getUsername());
        value.put(MAIL, u.getMail());
        value.put(PASSWORD, u.getPassword());
        mDb.insert(TABLE_NAME, null, value);
    }

    /**
     * @param id l'identifiant du métier à supprimer
     */
    public void supprimer(long id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    /**
     * @param u le métier modifié
     */
    public void modifier(User u) {
        ContentValues value = new ContentValues();
        value.put(USERNAME, u.getUsername());
        value.put(MAIL, u.getMail());
        value.put(PASSWORD, u.getPassword());
        mDb.update(TABLE_NAME, value, KEY + " = ?", new String[]{String.valueOf(u.getId())});
    }

    /**
     * @param id l'identifiant du métier à récupérer
     */
    public User selectionner(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id=?", new String[]{String.valueOf(id)});
        if (c.moveToNext()) {
            User u = new User(c.getLong(0), c.getString(1), c.getString(2), c.getString(3));
            c.close();
            return u;
        } else return null;
    }

    /**
     * @param mail l'email de l'utilisateur à récupérer
     * @param mdp  le mot de passe de l'utilisateur à récupérer
     */
    public User selectionner(String mail, String mdp) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where mail=? and password=?", new String[]{mail, mdp});
        if (c.moveToNext()) {
            User u = new User(c.getLong(0), c.getString(1), c.getString(2), c.getString(3));
            c.close();
            return u;
        } else return null;
    }

    /**
     * @param mail l'email de l'utilisateur à récupérer
     */
    public User selectionner(String mail) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where mail=?", new String[]{mail});
        if (c.moveToNext()) {
            User u = new User(c.getLong(0), c.getString(1), c.getString(2), c.getString(3));
            c.close();
            return u;
        } else return null;
    }
}
