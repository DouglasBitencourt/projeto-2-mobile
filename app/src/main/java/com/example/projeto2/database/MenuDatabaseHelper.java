package com.example.projeto2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MenuDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "menu.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_MENU_TABLE = "CREATE TABLE " +
            MenuContract.MenuEntry.TABLE_NAME + " (" +
            MenuContract.MenuEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MenuContract.MenuEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            MenuContract.MenuEntry.COLUMN_DESCRIPTION + " TEXT, " +
            MenuContract.MenuEntry.COLUMN_PRICE + " REAL, " +
            MenuContract.MenuEntry.COLUMN_HAS_GLUTEN + " INTEGER DEFAULT 0, " +
            MenuContract.MenuEntry.COLUMN_CALORIES + " INTEGER)";

    private static final String SQL_DELETE_MENU_TABLE = "DROP TABLE IF EXISTS " +
            MenuContract.MenuEntry.TABLE_NAME;

    public MenuDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MENU_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_MENU_TABLE);
        onCreate(db);
    }
}
