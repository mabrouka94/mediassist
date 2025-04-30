package com.example.mediassist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PrescriptionDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "prescriptions.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "prescriptions";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FILENAME = "filename";
    public static final String COLUMN_FILE_URI = "file_uri";

    public PrescriptionDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Création de la table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_FILENAME + " TEXT," +
                COLUMN_FILE_URI + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    // En cas de mise à jour du schéma
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
