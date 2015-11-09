package com.todos.hkboam.todos.bdd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.todos.hkboam.todos.bdd.DatabaseHandler;
import com.todos.hkboam.todos.bdd.modal.User;
import com.todos.hkboam.todos.persistent.CurrentUser;

import java.util.ArrayList;


/**
 * Created by mohamedamin on 18/10/2015.
 */
public class UserDAO extends DAOBase {
    public static final String KEY = DatabaseHandler.USER_KEY;
    public static final String USERNAME = DatabaseHandler.USER_USERNAME;
    public static final String MAIL = DatabaseHandler.USER_MAIL;
    public static final String PASSWORD = DatabaseHandler.USER_PASSWORD;
    public static final String TABLE_NAME = DatabaseHandler.USER_TABLE_NAME;

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
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where " + KEY + "=?",
                new String[]{String.valueOf(id)});
        int colKey = c.getColumnIndex(KEY);
        int colUsername = c.getColumnIndex(USERNAME);
        int colMail = c.getColumnIndex(MAIL);
        int colPassword = c.getColumnIndex(PASSWORD);
        if (c.moveToNext()) {
            User u = new User(c.getLong(colKey), c.getString(colUsername), c.getString(colMail),
                    c.getString(colPassword));
            c.close();
            return u;
        } else return null;
    }

    /**
     * @param mail l'email de l'utilisateur à récupérer
     * @param mdp  le mot de passe de l'utilisateur à récupérer
     */
    public User selectionner(String mail, String mdp) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where mail=? and password=?",
                new String[]{mail, mdp});
        int colKey = c.getColumnIndex(KEY);
        int colUsername = c.getColumnIndex(USERNAME);
        int colMail = c.getColumnIndex(MAIL);
        int colPassword = c.getColumnIndex(PASSWORD);
        if (c.moveToNext()) {
            User u = new User(c.getLong(colKey), c.getString(colUsername), c.getString(colMail),
                    c.getString(colPassword));
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

    /**
     *
     */
    public ArrayList<User> getAllExceptCurrent() {
        ArrayList<User> res = new ArrayList<>();

        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where " + KEY + "!=?",
                new String[]{String.valueOf(CurrentUser.getInstance().getUser().getId())});


        int colKey = c.getColumnIndex(KEY);
        int colUsername = c.getColumnIndex(USERNAME);
        int colMail = c.getColumnIndex(MAIL);
        int colPassword = c.getColumnIndex(PASSWORD);
        while (c.moveToNext()) {
            User u = new User(c.getInt(colKey), c.getString(colUsername), c.getString(colMail),
                    c.getString(colPassword));
            res.add(u);
        }
        c.close();
        return res;
    }
}
