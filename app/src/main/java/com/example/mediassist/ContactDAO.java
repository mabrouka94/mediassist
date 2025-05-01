package com.example.mediassist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ContactDAO {

    private final DatabaseHelper dbHelper;

    public ContactDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void ajouterContact(String name, String phone, String imageUri) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_NAME, name);
        values.put(DatabaseHelper.COL_PHONE, phone);
        values.put(DatabaseHelper.COL_IMAGE_URI, imageUri);

        db.insert(DatabaseHelper.TABLE_CONTACTS, null, values);
        db.close();
    }
}
