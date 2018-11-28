package com.origin.rxh.origin.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "origin.db";
    public static final String TABLE_USER = "user_info";
    public static final String TABLE_QUESTION = "question_table_test";
    public static final String TABLE_USER_NOW = "user_now";

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql_user_table = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (username TEXT PRIMARY KEY, password TEXT, "
                + "grades INTEGER, correct TEXT, wrong TEXT);";
        String sql_question_table = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + " (questionNo TEXT PRIMARY KEY, "
                + "question TEXT, answer TEXT, correct INTEGER);";
        String sql_user_now_table = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_NOW + " (id INTEGER PRIMARY KEY, username TEXT);";
        sqLiteDatabase.execSQL(sql_user_table);
        sqLiteDatabase.execSQL(sql_question_table);
        sqLiteDatabase.execSQL(sql_user_now_table);
    }

    //create table if the table is not exist
    public void createTable(SQLiteDatabase sqLiteDatabase) {
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onCreate(sqLiteDatabase);
    }

}