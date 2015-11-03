package com.todos.hkboam.todos.bdd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.todos.hkboam.todos.bdd.DatabaseHandler;
import com.todos.hkboam.todos.bdd.modal.UserTodoList;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class UserTodoListDAO extends DAOBase{
    public static final String KEY = DatabaseHandler.USER_TODOLIST_KEY;
    public static final String USER = DatabaseHandler.USER_TODOLIST_USER;
    public static final String LIST = DatabaseHandler.USER_TODOLIST_LIST;
    public static final String RIGHTS = DatabaseHandler.USER_TODOLIST_RIGHTS;
    public static final String TABLE_NAME = DatabaseHandler.USER_TODOLIST_TABLE_NAME;

    public UserTodoListDAO(Context pContext) {
        super(pContext);
    }

    /**
     * @param ul le métier à ajouter à la base
     */
    public void ajouter(UserTodoList ul) {
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
    public void modifier(UserTodoList ul) {
        ContentValues value = new ContentValues();
        value.put(USER, ul.getUser());
        value.put(LIST, ul.getList());
        value.put(RIGHTS, ul.getRights());
        mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(ul.getId())});
    }

    /**
     * @param id l'identifiant du métier à récupérer
     */
    public UserTodoList selectionner(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id=?", new String[]{String.valueOf(id)});
        int colKey = c.getColumnIndex(KEY);
        int colUser = c.getColumnIndex(USER);
        int colList = c.getColumnIndex(LIST);
        int colRights = c.getColumnIndex(RIGHTS);
        if(c.moveToNext()){
            UserTodoList um = new UserTodoList(c.getLong(colKey), c.getLong(colUser), c.getLong(colList), c.getLong(colRights));
            c.close();
            return um;
        }
        else return null;
    }
}
