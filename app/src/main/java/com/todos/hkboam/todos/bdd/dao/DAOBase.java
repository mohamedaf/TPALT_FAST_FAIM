package com.todos.hkboam.todos.bdd.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.todos.hkboam.todos.bdd.DatabaseHandler;


/**
 * Created by mohamedamin on 18/10/2015.
 */
public abstract class DAOBase {

    // Nous sommes à la première version de la base
    // Si je décide de la mettre à jour, il faudra changer cet attribut
    protected final static int VERSION = 1;
    // Le nom du fichier qui représente ma base
    protected final static String NOM = "todos.db";

    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;
    protected Context pContext = null;

    public DAOBase(Context pContext) {
        this.pContext = pContext;
        this.mHandler = new DatabaseHandler(pContext, NOM, null, VERSION);
    }

    public void open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getWritableDatabase();
    }

    public void close() {
        mHandler.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }
}
