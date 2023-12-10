package com.mhmc.feriaiot.DataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataHandler_Sqlite extends SQLiteOpenHelper {

    public DataHandler_Sqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE eventos (id INTEGER PRIMARY KEY, timestamp TIMESTAMP, sensor TEXT, ubicacionN TEXT,ubicacionS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS eventos");
        sqLiteDatabase.execSQL("CREATE TABLE eventos (id INTEGER PRIMARY KEY, timestamp TIMESTAMP, sensor TEXT, ubicacionN TEXT,ubicacionS TEXT)");
    }
}
