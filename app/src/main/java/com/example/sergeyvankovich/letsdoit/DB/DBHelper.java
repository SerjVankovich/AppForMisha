package com.example.sergeyvankovich.letsdoit.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String BASE_NAME = "TasksBase";
    public static final int BASE_VERSION = 5;
    public static final String TASKS_TABLE = "tableTasks";
    public static final String COMPLETED_TASKS = "completedTasks";
    public static final String TASK_NAME = "taskName";
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";
    public static final String KEY_ID = "_id";
    public static final String IMPORTANT = "important";


    public DBHelper(Context context) {
        super(context, BASE_NAME, null, BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TASKS_TABLE + "(" + KEY_ID + " integer primary key,"
                + TASK_NAME + " text," + YEAR + " integer," + MONTH + " integer," + IMPORTANT + " text,"
                + DAY + " integer);");

        db.execSQL("create table " + COMPLETED_TASKS + "(" + KEY_ID + " integer primary key,"
                + TASK_NAME + " text," + YEAR + " integer," + MONTH + " integer," + IMPORTANT + " text,"
                + DAY + " integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TASKS_TABLE);
        onCreate(db);
    }
}
