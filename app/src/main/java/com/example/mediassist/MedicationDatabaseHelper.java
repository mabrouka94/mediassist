package com.example.mediassist;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class MedicationDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "medications.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "medications";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_FREQUENCY = "frequency";
    public static final String COLUMN_DOSAGE = "dosage";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_IMAGE_PATH = "image_path";

    public MedicationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_TYPE + " TEXT," +
                        COLUMN_FREQUENCY + " TEXT," +
                        COLUMN_DOSAGE + " TEXT," +
                        COLUMN_TIME + " TEXT," +
                        COLUMN_IMAGE_PATH + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertMedication(String name, String type, String frequency, String dosage, String time, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_FREQUENCY, frequency);
        values.put(COLUMN_DOSAGE, dosage);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_IMAGE_PATH, imagePath);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void markAsRead(int id) {

    }
}
