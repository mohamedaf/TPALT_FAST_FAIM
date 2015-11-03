package com.todos.hkboam.todos.bdd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.todos.hkboam.todos.bdd.DatabaseHandler;
import com.todos.hkboam.todos.bdd.modal.ListItem;

import java.util.ArrayList;

/**
 * Created by zomboris on 11/3/15.
 */
public class ListItemDAO extends  DAOBase {
    public static final String KEY = DatabaseHandler.LIST_ITEM_KEY;
    public static final String MODIFICATION_DATE = DatabaseHandler.LIST_ITEM_MODIFICATION_DATE;
    public static final String LIST = DatabaseHandler.LIST_ITEM_LIST;
    public static final String AUTHOR = DatabaseHandler.LIST_ITEM_AUTHOR;
    public static final String CONTENT = DatabaseHandler.LIST_ITEM_CONTENT;
    public static final String TABLE_NAME = DatabaseHandler.LIST_ITEM_TABLE_NAME;



    public ListItemDAO(Context pContext) {
        super(pContext);
    }

    /**
     * @param l la liste à ajouter à la base
     */
    public void ajouter(ListItem li) {
        ContentValues value = new ContentValues();
        value.put(MODIFICATION_DATE, li.getModification_date());
        value.put(LIST, li.getList());
        value.put(AUTHOR, li.getAuthor());
        value.put(CONTENT, li.getContent());
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
    public void modifier(ListItem l) {
        ContentValues value = new ContentValues();
        value.put(MODIFICATION_DATE, l.getModification_date());
        value.put(CONTENT, l.getContent());
        mDb.update(TABLE_NAME, value, KEY + " = ?", new String[]{String.valueOf(l.getId())});
    }

    /**
     * @param id l'identifiant du métier à récupérer
     */
    public ListItem selectionner(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id=?", new String[]{String.valueOf(id)});
        int colKey = c.getColumnIndex(KEY);
        int colModDate = c.getColumnIndex(MODIFICATION_DATE);
        int colList = c.getColumnIndex(LIST);
        int colAuthor = c.getColumnIndex(AUTHOR);
        int colContent = c.getColumnIndex(CONTENT);
        if (c.moveToNext()) {
            ListItem m = new ListItem(c.getLong(colKey), c.getString(colContent));
            m.setModification_date(c.getLong(colModDate));
            m.setList(c.getLong(colList));
            m.setAuthor(c.getLong(colAuthor));
            c.close();
            return m;
        } else return null;
    }

    public ArrayList<ListItem> toutSelectionner() {
        ArrayList<ListItem> res = new ArrayList<ListItem>();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, new String[0]);
        int colKey = c.getColumnIndex(KEY);
        int colModDate = c.getColumnIndex(MODIFICATION_DATE);
        int colList = c.getColumnIndex(LIST);
        int colAuthor = c.getColumnIndex(AUTHOR);
        int colContent = c.getColumnIndex(CONTENT);
        while (c.moveToNext()) {
            ListItem m = new ListItem(c.getLong(colKey), c.getString(colContent));
            m.setModification_date(c.getLong(colModDate));
            m.setList(c.getLong(colList));
            m.setAuthor(c.getLong(colAuthor));
            res.add(m);
        }
        c.close();
        return res;
    }
}
