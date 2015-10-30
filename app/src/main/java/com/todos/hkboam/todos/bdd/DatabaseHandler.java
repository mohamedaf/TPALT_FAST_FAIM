package com.todos.hkboam.todos.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mohamedamin on 18/10/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    /**
     * USER
     */
    public static final String USER_KEY = "id";
    public static final String USER_USERNAME = "username";
    public static final String USER_MAIL = "mail";
    public static final String USER_PASSWORD = "password";
    public static final String USER_TABLE_NAME = "user";


    /**
     * MEMO
     */
    public static final String MEMO_KEY = "id";
    public static final String MEMO_CONTENT = "content";
    public static final String MEMO_TABLE_NAME = "memo";

    /**
     * USER_MEMO
     */
    public static final String USER_MEMO_KEY = "id";
    public static final String USER_MEMO_USER = "user";
    public static final String USER_MEMO_MEMO = "memo";
    public static final String USER_MEMO_TABLE_NAME = "userMemo";

    /**
     * USER
     */
    public static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_USERNAME + " TEXT NOT NULL, " +
                    USER_MAIL + " TEXT NOT NULL, " +
                    USER_PASSWORD + " TEXT NOT NULL);";
    public static final String USER_TABLE_DROP = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";

    /**
     * MEMO
     */
    public static final String MEMO_TABLE_CREATE =
            "CREATE TABLE " + MEMO_TABLE_NAME + " (" +
                    MEMO_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MEMO_CONTENT + " TEXT NOT NULL);";
    public static final String MEMO_TABLE_DROP = "DROP TABLE IF EXISTS " + MEMO_TABLE_NAME + ";";

    /**
     * USER_MEMO
     */
    public static final String USER_MEMO_TABLE_CREATE =
            "CREATE TABLE " + USER_MEMO_TABLE_NAME + " (" +
                    USER_MEMO_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_MEMO_USER + " INTEGER NOT NULL, " +
                    USER_MEMO_MEMO + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + USER_MEMO_USER + ") REFERENCES "+ USER_TABLE_NAME +"("+ USER_KEY +"), " +
                    "FOREIGN KEY (" + USER_MEMO_MEMO + ") REFERENCES "+ MEMO_TABLE_NAME +"("+ MEMO_KEY +"));";
    public static final String USER_MEMO_TABLE_DROP = "DROP TABLE IF EXISTS " + USER_MEMO_TABLE_NAME + ";";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * Creation de la table des utilisateurs de l'application
         */
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(MEMO_TABLE_CREATE);
        db.execSQL(USER_MEMO_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**
         * Mise a jour de la table
         */
        db.execSQL(USER_TABLE_DROP);
        db.execSQL(MEMO_TABLE_DROP);
        db.execSQL(USER_MEMO_TABLE_DROP);
        onCreate(db);
    }
}
