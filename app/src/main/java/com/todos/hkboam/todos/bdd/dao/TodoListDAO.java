package com.todos.hkboam.todos.bdd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.todos.hkboam.todos.bdd.DatabaseHandler;
import com.todos.hkboam.todos.bdd.modal.TodoList;

import java.util.ArrayList;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class TodoListDAO extends DAOBase {
    public static final String KEY = DatabaseHandler.TODOLIST_KEY;
    public static final String MODIFICATION_DATE = DatabaseHandler.TODOLIST_MODIFICATION_DATE;
    public static final String AUTHOR = DatabaseHandler.TODOLIST_AUTHOR;
    public static final String TITLE = DatabaseHandler.TODOLIST_TITLE;
    public static final String TABLE_NAME = DatabaseHandler.TODOLIST_TABLE_NAME;

    public TodoListDAO(Context pContext) {
        super(pContext);
    }

    /**
     * @param l la liste à ajouter à la base
     */
    public void ajouter(TodoList l) {
        ContentValues value = new ContentValues();
        value.put(MODIFICATION_DATE, l.getModification_date());
        value.put(AUTHOR, l.getAuthor());
        value.put(TITLE, l.getTitle());
        mDb.insert(TABLE_NAME, null, value);
    }

    /**
     * @param id l'identifiant de la liste à supprimer
     */
    public void supprimer(long id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    /**
     * @param l la liste modifié
     */
    public void modifier(TodoList l) {
        ContentValues value = new ContentValues();
        value.put(TITLE, l.getTitle());
        value.put(MODIFICATION_DATE, l.getModification_date());
        mDb.update(TABLE_NAME, value, KEY + " = ?", new String[]{String.valueOf(l.getId())});
    }

    /**
     * @param id l'identifiant du métier à récupérer
     */
    public TodoList selectionner(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id=?", new String[]{String.valueOf(id)});
        int colKey = c.getColumnIndex(KEY);
        int colModDate = c.getColumnIndex(MODIFICATION_DATE);
        int colAuthor = c.getColumnIndex(AUTHOR);
        int colTitle = c.getColumnIndex(TITLE);
        if (c.moveToNext()) {
            TodoList m = new TodoList(c.getLong(colKey), c.getString(colTitle));
            m.setModification_date(c.getLong(colModDate));
            m.setAuthor(c.getLong(colAuthor));
            c.close();
            return m;
        } else return null;
    }

    public ArrayList<TodoList> toutSelectionner() {
        ArrayList<TodoList> res = new ArrayList<TodoList>();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, new String[0]);
        int colKey = c.getColumnIndex(KEY);
        int colModDate = c.getColumnIndex(MODIFICATION_DATE);
        int colAuthor = c.getColumnIndex(AUTHOR);
        int colTitle = c.getColumnIndex(TITLE);
        while (c.moveToNext()) {
            TodoList m = new TodoList(c.getLong(colKey), c.getString(colTitle));
            m.setModification_date(c.getLong(colModDate));
            m.setAuthor(c.getLong(colAuthor));
            res.add(m);
        }
        c.close();
        return res;
    }
}
