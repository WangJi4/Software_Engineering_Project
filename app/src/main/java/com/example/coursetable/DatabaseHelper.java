package com.example.coursetable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    public static final String CREATE_COURSES = "create table courses(" +
            "course_code text primary key," +
            "course_name text," +
            "teacher text," +
            "class_room text," +
            "day integer," +
            "class_start integer," +
            "class_end integer)";

    public static final String CREATE_WEEK = "create table week(" +
            "week_no integer," +
            "course_code text," +
            "affair_code text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COURSES);
        db.execSQL(CREATE_WEEK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
