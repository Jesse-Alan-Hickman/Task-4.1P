package com.example.task41;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDB extends SQLiteOpenHelper {

    //Database version
    private static final int DB_VERSION = 1;

    //Database name
    private static final String DB_NAME = "TaskManagerDB";

    //Table name
    private static final String TABLE_NAME = "tasks";
    //Column Names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DUE_DATE = "due_date";

    //Table creation
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_TIME + " TEXT," +
                    COLUMN_DUE_DATE + " TEXT)";

    //Table deletion
    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SQLiteDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL(SQL_DELETE_TABLE);
        //Create tables again
        onCreate(db);
    }

    //Inserting new data into the database
    public long insertData(String title, String description, String time, String dueDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_DUE_DATE, dueDate);
        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public long updateData(long id, String title, String description, String time, String dueDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_DESCRIPTION, description);
        contentValues.put(COLUMN_TIME, time);
        contentValues.put(COLUMN_DUE_DATE, dueDate);
        return db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    //Reading data from the database
    public Cursor readData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_TIME, COLUMN_DUE_DATE};
        return db.query(TABLE_NAME, projection, null, null, null, null, null);
    }

//    public int editData(int id, String title, String description, String time, String dueDate) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TITLE, title);
//        values.put(COLUMN_DESCRIPTION, description);
//        values.put(COLUMN_TIME, time);
//        values.put(COLUMN_DUE_DATE, dueDate);
//        String selection = COLUMN_ID + " = ?";
//        String[] selectionArgs = {String.valueOf(id)};
//        int count = db.update(TABLE_NAME, values, selection, selectionArgs);
//        db.close();
//        return count;
//    }

    public int deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        int deletedRows = db.delete(TABLE_NAME,selection, selectionArgs);
        db.close();
        return deletedRows;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = readData();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                @SuppressLint("Range") String dueDate = cursor.getString(cursor.getColumnIndex(COLUMN_DUE_DATE));

                Task task = new Task(id, title, description, time, dueDate);
                taskList.add(task);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return taskList;
    }
}