package com.cristaldo.kharon.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {
    // Declaración de variables para utilizar DB
    private static final String DATABASE_NAME = "banco.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor de clase DBHelper
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}