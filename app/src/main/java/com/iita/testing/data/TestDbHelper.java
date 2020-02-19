package com.iita.testing.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iita.testing.data.TestContract.LabOneTestEntry;

public class TestDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "test.db";

    public static final int DATABASE_VERSION = 1;

    public TestDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TESTS_TABLE = "CREATE TABLE " + LabOneTestEntry.TABLE_NAME + "("
                + LabOneTestEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LabOneTestEntry.COLUMN_PRODUCT_LABEL + " TEXT NOT NULL, "
                + LabOneTestEntry.COLUMN_SAMPLE_WEIGHT + " REAL NOT NULL DEFAULT 0, "
                + LabOneTestEntry.COLUMN_LUTEIN + " INTEGER NOT NULL DEFAULT 0, "
                + LabOneTestEntry.COLUMN_LUTEIN_RESULT + " REAL NOT NULL DEFAULT 0, "
                + LabOneTestEntry.COLUMN_ZEAXANTHIN + " INTEGER NOT NULL DEFAULT 0, "
                + LabOneTestEntry.COLUMN_ZEAXANTHIN_RESULT + " REAL NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_TESTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor raw() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + LabOneTestEntry.TABLE_NAME , new String[]{});

        return res;
    }
}
