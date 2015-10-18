package com.todos.hkboam.todos.bdd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.todos.hkboam.todos.bdd.modal.Memo;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class MemoDAO extends DAOBase{
    public static final String KEY = "id";
    public static final String CONTENT = "content";
    public static final String TABLE_NAME = "memo";

    public MemoDAO(Context pContext) {
        super(pContext);
    }

    /**
     * @param m le métier à ajouter à la base
     */
    public void ajouter(Memo m) {
        ContentValues value = new ContentValues();
        value.put(CONTENT, m.getContent());
        mDb.insert(TABLE_NAME, null, value);
    }

    /**
     * @param id l'identifiant du métier à supprimer
     */
    public void supprimer(long id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    /**
     * @param m le métier modifié
     */
    public void modifier(Memo m) {
        ContentValues value = new ContentValues();
        value.put(CONTENT, m.getContent());
        mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(m.getId())});
    }

    /**
     * @param id l'identifiant du métier à récupérer
     */
    public Memo selectionner(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id=?", new String[]{String.valueOf(id)});
        if(c.moveToNext()){
            Memo m = new Memo(c.getLong(0), c.getString(1));
            c.close();
            return m;
        }
        else return null;
    }
}
