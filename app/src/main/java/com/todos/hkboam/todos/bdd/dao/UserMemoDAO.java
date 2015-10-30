package com.todos.hkboam.todos.bdd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.todos.hkboam.todos.bdd.modal.Memo;
import com.todos.hkboam.todos.bdd.modal.UserMemo;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class UserMemoDAO extends DAOBase{
    public static final String KEY = "id";
    public static final String USER = "user";
    public static final String MEMO = "memo";
    public static final String TABLE_NAME = "userMemo";

    public UserMemoDAO(Context pContext) {
        super(pContext);
    }

    /**
     * @param um le métier à ajouter à la base
     */
    public void ajouter(UserMemo um) {
        ContentValues value = new ContentValues();
        value.put(USER, um.getUser());
        value.put(MEMO, um.getMemo());
        mDb.insert(TABLE_NAME, null, value);
    }

    /**
     * @param id l'identifiant du métier à supprimer
     */
    public void supprimer(long id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    /**
     * @param um le métier modifié
     */
    public void modifier(UserMemo um) {
        ContentValues value = new ContentValues();
        value.put(USER, um.getUser());
        value.put(MEMO, um.getMemo());
        mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(um.getId())});
    }

    /**
     * @param id l'identifiant du métier à récupérer
     */
    public UserMemo selectionner(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id=?", new String[]{String.valueOf(id)});
        if(c.moveToNext()){
            UserMemo um = new UserMemo(c.getLong(0), c.getLong(1), c.getLong(2));
            c.close();
            return um;
        }
        else return null;
    }
}
