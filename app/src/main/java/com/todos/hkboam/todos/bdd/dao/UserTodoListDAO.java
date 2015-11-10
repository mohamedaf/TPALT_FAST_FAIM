package com.todos.hkboam.todos.bdd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.todos.hkboam.todos.bdd.DatabaseHandler;
import com.todos.hkboam.todos.bdd.modal.User;
import com.todos.hkboam.todos.bdd.modal.UserTodoList;

import java.util.ArrayList;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class UserTodoListDAO extends DAOBase {
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
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    /**
     * @param list l'identifiant de la liste à supprimer
     */
    public int deleteInList(long list) {
        return mDb.delete(TABLE_NAME, LIST + " = ?", new String[]{String.valueOf(list)});
    }

    /**
     * @param list l'identifiant de la liste à supprimer
     */
    public int deleteUserFromList(long user, long list) {
        return mDb.delete(TABLE_NAME, LIST + " = ? AND " + USER + " = ?", new String[]{String.valueOf(list), String.valueOf(user)});
    }

    /**
     * @param ul le métier modifié
     */
    public void modifier(UserTodoList ul) {
        ContentValues value = new ContentValues();
        value.put(USER, ul.getUser());
        value.put(LIST, ul.getList());
        value.put(RIGHTS, ul.getRights());
        mDb.update(TABLE_NAME, value, KEY + " = ?", new String[]{String.valueOf(ul.getId())});
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

        if (c.moveToNext()) {
            UserTodoList um = new UserTodoList(c.getLong(colKey), c.getLong(colUser), c.getLong(colList), c.getLong(colRights));
            c.close();
            return um;
        } else return null;
    }

    public ArrayList<UserTodoList> getByUserId(long userId) {
        ArrayList<UserTodoList> res = new ArrayList<UserTodoList>();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where " + USER + "=?", new String[]{String.valueOf(userId)});
        int colKey = c.getColumnIndex(KEY);
        int colUser = c.getColumnIndex(USER);
        int colList = c.getColumnIndex(LIST);
        int colRights = c.getColumnIndex(RIGHTS);
        while (c.moveToNext()) {
            UserTodoList m = new UserTodoList(colKey, colUser, colList, colRights);
            res.add(m);
        }
        c.close();
        return res;
    }

    public ArrayList<UserTodoList> getByListId(long listId) {
        ArrayList<UserTodoList> res = new ArrayList<UserTodoList>();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where " + LIST + "=?", new String[]{String.valueOf(listId)});
        int colKey = c.getColumnIndex(KEY);
        int colUser = c.getColumnIndex(USER);
        int colList = c.getColumnIndex(LIST);
        int colRights = c.getColumnIndex(RIGHTS);
        while (c.moveToNext()) {
            UserTodoList m = new UserTodoList(c.getLong(colKey), c.getLong(colUser),
                    c.getLong(colList), c.getInt(colRights));
            res.add(m);
        }
        c.close();
        return res;
    }

}
