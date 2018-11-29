package com.bichi.storeimageindb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    private String DATABASE_MARKSTABLE = "FOODS";
    private static final String ID = "id";
    public static final String IMAGE = "image";
    private static final String DATABASE_NAME = "list";
    private static final String NAME = "name";
    static String TAG ="DatabaseHandler";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + DATABASE_MARKSTABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +NAME + " TEXT," +IMAGE + " BLOB"+")";

        String query1 = " CREATE TABLE " + DATABASE_MARKSTABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +NAME + " TEXT "+")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_MARKSTABLE);
        onCreate(db);
    }

    public long insertData(String name, byte[] image){
        Log.d(TAG, "insertData: ");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name); // Contact Name
        values.put(IMAGE, image); // Contact Phone
        long l=db.insert(DATABASE_MARKSTABLE, null, values);
        db.close();
        return l;
    }
    public Cursor getData(){
        String selectQuery = "SELECT * FROM "+DATABASE_MARKSTABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }


    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
