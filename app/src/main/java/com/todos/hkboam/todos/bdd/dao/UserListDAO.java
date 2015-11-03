package com.todos.hkboam.todos.bdd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.todos.hkboam.todos.bdd.DatabaseHandler;
import com.todos.hkboam.todos.bdd.modal.UserList;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class UserListDAO extends DAOBase{
    public static final String KEY = DatabaseHandler.USER_LIST_KEY;
    public static final String USER = DatabaseHandler.USER_LIST_USER;
    public static final String LIST = DatabaseHandler.USER_LIST_LIST;
    public static final String RIGHTS = DatabaseHandler.USER_LIST_RIGHTS;
    public static final String TABLE_NAME = DatabaseHandler.USER_LIST_TABLE_NAME;

    public UserListDAO(Context pContext) {
        super(pContext);
    }

    /**
     * @param ul le métier à ajouter à la base
     */
    public void ajouter(UserList ul) {
        ContentValues value = new ContentValues();
        value.put(USER, ul.getUser());
        value.put(LIST, ul.getList());
        value.put(RIGHTS, ul.getRights());
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
    public void modifier(UserList ul) {
        ContentValues value = new ContentValues();
        value.put(USER, ul.getUser());
        value.put(LIST, ul.getList());
        value.put(RIGHTS, ul.getRights());
        mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(ul.getId())});
    }

    /**
     * @param id l'identifiant du métier à récupérer
     */
    public UserList selectionner(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id=?", new String[]{String.valueOf(id)});
        int colKey = c.getColumnIndex(KEY);
        int colUser = c.getColumnIndex(USER);
        int colList = c.getColumnIndex(LIST);
        int colRights = c.getColumnIndex(RIGHTS);
        if(c.moveToNext()){
            UserList um = new UserList(c.getLong(colKey), c.getLong(colUser), c.getLong(colList), c.getLong(colRights));
            c.close();
            return um;
        }
        else return null;
    }
}
