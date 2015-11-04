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
     * TODOLIST
     */
    public static final String TODOLIST_KEY = "id";
    public static final String TODOLIST_MODIFICATION_DATE = "modification_date";
    public static final String TODOLIST_AUTHOR = "author";
    public static final String TODOLIST_TITLE = "title";
    public static final String TODOLIST_TABLE_NAME = "todoList";

    /**
     * USER_TODOLIST
     */
    public static final String USER_TODOLIST_KEY = "id";
    public static final String USER_TODOLIST_USER = "user";
    public static final String USER_TODOLIST_LIST = "todoList";
    public static final String USER_TODOLIST_RIGHTS = "rights";
    public static final String USER_TODOLIST_TABLE_NAME = "userTodoList";

    /**
     * TODO
     */
    public static final String TODO_KEY = "id";
    public static final String TODO_MODIFICATION_DATE = "modification_date";
    public static final String TODO_LIST = "todoList";
    public static final String TODO_AUTHOR = "author";
    public static final String TODO_CONTENT = "content";
    public static final String TODO_DONE = "done";
    public static final String TODO_TABLE_NAME = "todo";

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
    public static final String TODOLIST_TABLE_CREATE =
            "CREATE TABLE " + TODOLIST_TABLE_NAME + " (" +
                    TODOLIST_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TODOLIST_MODIFICATION_DATE + " INTEGER NOT NULL, " +
                    TODOLIST_AUTHOR + " INTEGER NOT NULL, " +
                    TODOLIST_TITLE + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + TODOLIST_AUTHOR + ") REFERENCES " + USER_TABLE_NAME + "(" + USER_KEY + "));";
    public static final String TODOLIST_TABLE_DROP = "DROP TABLE IF EXISTS " + TODOLIST_TABLE_NAME + ";";

    /**
     * USER_MEMO
     */
    public static final String USER_TODOLIST_TABLE_CREATE =
            "CREATE TABLE " + USER_TODOLIST_TABLE_NAME + " (" +
                    USER_TODOLIST_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_TODOLIST_USER + " INTEGER NOT NULL, " +
                    USER_TODOLIST_LIST + " INTEGER NOT NULL, " +
                    USER_TODOLIST_RIGHTS + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + USER_TODOLIST_USER + ") REFERENCES " + USER_TABLE_NAME + "(" + USER_KEY + "), " +
                    "FOREIGN KEY (" + USER_TODOLIST_LIST + ") REFERENCES " + TODOLIST_TABLE_NAME + "(" + TODOLIST_KEY + "));";
    public static final String USER_TODOLIST_TABLE_DROP = "DROP TABLE IF EXISTS " + USER_TODOLIST_TABLE_NAME + ";";

    public static final String TODO_TABLE_CREATE =
            "CREATE TABLE " + TODO_TABLE_NAME + " (" +
                    TODO_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TODO_MODIFICATION_DATE + " INTEGER NOT NULL, " +
                    TODO_LIST + " INTEGER NOT NULL, " +
                    TODO_AUTHOR + " INTEGER NOT NULL, " +
                    TODO_CONTENT + " TEXT NOT NULL, " +
                    TODO_DONE + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + TODO_AUTHOR + ") REFERENCES " + USER_TABLE_NAME + "(" + USER_KEY + "), " +
                    "FOREIGN KEY (" + TODO_LIST + ") REFERENCES " + TODOLIST_TABLE_NAME + "(" + TODOLIST_KEY + "));";
    public static final String TODO_TABLE_DROP = "DROP TABLE IF EXISTS " + TODO_TABLE_NAME + ";";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * Creation de la table des utilisateurs de l'application
         */
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(TODOLIST_TABLE_CREATE);
        db.execSQL(USER_TODOLIST_TABLE_CREATE);
        db.execSQL(TODO_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**
         * Mise a jour de la table
         */
        db.execSQL(USER_TABLE_DROP);
        db.execSQL(TODOLIST_TABLE_DROP);
        db.execSQL(USER_TODOLIST_TABLE_DROP);
        onCreate(db);
    }
}
