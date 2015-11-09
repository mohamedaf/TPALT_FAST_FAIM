package com.todos.hkboam.todos.bdd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.todos.hkboam.todos.bdd.DatabaseHandler;
import com.todos.hkboam.todos.bdd.modal.Todo;

import java.util.ArrayList;

/**
 * Created by zomboris on 11/3/15.
 */
public class ListItemDAO extends DAOBase {
    public static final String KEY = DatabaseHandler.TODO_KEY;
    public static final String MODIFICATION_DATE = DatabaseHandler.TODO_MODIFICATION_DATE;
    public static final String LIST = DatabaseHandler.TODO_LIST;
    public static final String AUTHOR = DatabaseHandler.TODO_AUTHOR;
    public static final String CONTENT = DatabaseHandler.TODO_CONTENT;
    public static final String DONE = DatabaseHandler.TODO_DONE;
    public static final String TABLE_NAME = DatabaseHandler.TODO_TABLE_NAME;


    public ListItemDAO(Context pContext) {
        super(pContext);
    }

    /**
     * @param li la liste à ajouter à la base
     */
    public void ajouter(Todo li) {
        ContentValues value = new ContentValues();
        value.put(MODIFICATION_DATE, li.getModification_date());
        value.put(LIST, li.getList());
        value.put(AUTHOR, li.getAuthor());
        value.put(CONTENT, li.getContent());
        value.put(DONE, li.getDone());
        mDb.insert(TABLE_NAME, null, value);
    }

    /**
     * @param id l'identifiant de l'item à supprimer
     */
    public int supprimer(long id) {
        return mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    /**
     * @param list l'identifiant de la liste à supprimer
     */
    public int deleteInList(long list) {
        return mDb.delete(TABLE_NAME, LIST + " = ?", new String[]{String.valueOf(list)});
    }

    /**
     * @param l la liste modifié
     */
    public void modifier(Todo l) {
        ContentValues value = new ContentValues();
        value.put(MODIFICATION_DATE, l.getModification_date());
        value.put(CONTENT, l.getContent());
        value.put(DONE, l.getDone());
        mDb.update(TABLE_NAME, value, KEY + " = ?", new String[]{String.valueOf(l.getId())});
    }

    /**
     * @param id l'identifiant du métier à récupérer
     */
    public Todo selectionner(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id=?", new String[]{String.valueOf(id)});
        int colKey = c.getColumnIndex(KEY);
        int colModDate = c.getColumnIndex(MODIFICATION_DATE);
        int colList = c.getColumnIndex(LIST);
        int colAuthor = c.getColumnIndex(AUTHOR);
        int colContent = c.getColumnIndex(CONTENT);
        int colDone = c.getColumnIndex(DONE);
        if (c.moveToNext()) {
            Todo m = new Todo(c.getLong(colKey), c.getLong(colModDate), c.getLong(colList),
                    c.getLong(colAuthor), c.getString(colContent), c.getInt(colDone));
            c.close();
            return m;
        } else return null;
    }

    public ArrayList<Todo> toutSelectionner() {
        ArrayList<Todo> res = new ArrayList<Todo>();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, new String[0]);
        int colKey = c.getColumnIndex(KEY);
        int colModDate = c.getColumnIndex(MODIFICATION_DATE);
        int colList = c.getColumnIndex(LIST);
        int colAuthor = c.getColumnIndex(AUTHOR);
        int colContent = c.getColumnIndex(CONTENT);
        int colDone = c.getColumnIndex(DONE);
        while (c.moveToNext()) {
            Todo m = new Todo(c.getLong(colKey), c.getLong(colModDate), c.getLong(colList),
                    c.getLong(colAuthor), c.getString(colContent), c.getInt(colDone));
            res.add(m);
        }
        c.close();
        return res;
    }

    public ArrayList<Todo> getItemsByListId(long listId) {
        ArrayList<Todo> res = new ArrayList<Todo>();

        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where " + LIST + "=?", new String[]{String.valueOf(listId)});
        int colKey = c.getColumnIndex(KEY);
        int colModDate = c.getColumnIndex(MODIFICATION_DATE);
        int colList = c.getColumnIndex(LIST);
        int colAuthor = c.getColumnIndex(AUTHOR);
        int colContent = c.getColumnIndex(CONTENT);
        int colDone = c.getColumnIndex(DONE);
        while (c.moveToNext()) {
            Todo m = new Todo(c.getLong(colKey), c.getLong(colModDate), c.getLong(colList),
                    c.getLong(colAuthor), c.getString(colContent), c.getInt(colDone));
            res.add(m);
        }
        c.close();
        return res;
    }
}
