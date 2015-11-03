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
     * LIST
     */
    public static final String LIST_KEY = "id";
    public static final String LIST_MODIFICATION_DATE = "modification_date";
    public static final String LIST_AUTHOR = "author";
    public static final String LIST_TITLE = "title";
    public static final String LIST_TABLE_NAME = "list";

    /**
     * USER_LIST
     */
    public static final String USER_LIST_KEY = "id";
    public static final String USER_LIST_USER = "user";
    public static final String USER_LIST_LIST = "list";
    public static final String USER_LIST_RIGHTS = "rights";
    public static final String USER_LIST_TABLE_NAME = "userList";

    /**
     * LIST_ITEM
     */
    public static final String LIST_ITEM_KEY = "id";
    public static final String LIST_ITEM_MODIFICATION_DATE = "modification_date";
    public static final String LIST_ITEM_LIST = "list";
    public static final String LIST_ITEM_AUTHOR = "author";
    public static final String LIST_ITEM_CONTENT = "content";
    public static final String LIST_ITEM_TABLE_NAME = "listItem";

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
    public static final String LIST_TABLE_CREATE =
            "CREATE TABLE " + LIST_TABLE_NAME + " (" +
                    LIST_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LIST_MODIFICATION_DATE + " INTEGER NOT NULL, " +
                    LIST_AUTHOR + " INTEGER NOT NULL, " +
                    LIST_TITLE + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + LIST_AUTHOR + ") REFERENCES " + USER_TABLE_NAME + "(" + USER_KEY + "));";
    public static final String MEMO_TABLE_DROP = "DROP TABLE IF EXISTS " + LIST_TABLE_NAME + ";";

    /**
     * USER_MEMO
     */
    public static final String USER_LIST_TABLE_CREATE =
            "CREATE TABLE " + USER_LIST_TABLE_NAME + " (" +
                    USER_LIST_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_LIST_USER + " INTEGER NOT NULL, " +
                    USER_LIST_LIST + " INTEGER NOT NULL, " +
                    USER_LIST_RIGHTS + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + USER_LIST_USER + ") REFERENCES " + USER_TABLE_NAME + "(" + USER_KEY + "), " +
                    "FOREIGN KEY (" + USER_LIST_LIST + ") REFERENCES " + LIST_TABLE_NAME + "(" + LIST_KEY + "));";
    public static final String USER_MEMO_TABLE_DROP = "DROP TABLE IF EXISTS " + USER_LIST_TABLE_NAME + ";";

    public static final String LIST_ITEM_TABLE_CREATE =
            "CREATE TABLE " + LIST_ITEM_TABLE_NAME + " (" +
                    LIST_ITEM_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LIST_ITEM_MODIFICATION_DATE + " INTEGER NOT NULL, " +
                    LIST_ITEM_LIST + " INTEGER NOT NULL, " +
                    LIST_ITEM_AUTHOR + " INTEGER NOT NULL, " +
                    LIST_ITEM_CONTENT + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + LIST_ITEM_AUTHOR + ") REFERENCES " + USER_TABLE_NAME + "(" + USER_KEY + "), " +
                    "FOREIGN KEY (" + LIST_ITEM_LIST + ") REFERENCES " + LIST_TABLE_NAME + "(" + LIST_KEY + "));";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * Creation de la table des utilisateurs de l'application
         */
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(LIST_TABLE_CREATE);
        db.execSQL(USER_LIST_TABLE_CREATE);
        db.execSQL(LIST_ITEM_TABLE_CREATE);
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
