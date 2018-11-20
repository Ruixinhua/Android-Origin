package com.origin.rxh.origin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.origin.rxh.origin.Question;
import com.origin.rxh.origin.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class DBService {
    MyDBHelper dbhelper;

    public DBService(Context context) {
        dbhelper = new MyDBHelper(context);
    }

    public ContentValues userInfoToContentValues(UserInfo userInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", userInfo.getUsername());
        contentValues.put("password", userInfo.getPassword());
        contentValues.put("grades", userInfo.getGrade());
        List<Question> corrects = userInfo.getCorrectQue();
        String correct = "";
        for (Question q : corrects) {
            correct += (q.getQuestionNo() + "-");
        }
        List<Question> wrong = userInfo.getWrongQue();
        String w = "";
        for (Question q : wrong) {
            correct += (q.getQuestionNo() + "-");
        }
        contentValues.put("correct", correct);
        contentValues.put("wrong", w);
        return contentValues;
    }

    public ContentValues questionToContentValues(Question question) {
        ContentValues contentValues = new ContentValues();
        String[] content = {"questionNo", "question", "answer"};
        contentValues.put("questionNo", question.getQuestionNo());
        contentValues.put("question", question.getQuestion());
        String answer = "";
        String answers[] = question.getAnswer();
        for (int i = 0; i < answer.length(); i++) {
            answer += (answers[i] + "-");
        }
        contentValues.put("answer", answer);
        return contentValues;
    }

    public void saveQestion(Question question) {
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        ContentValues values = questionToContentValues(question);
        dbhelper.createTable(database);
        Cursor cursor = database.query(MyDBHelper.TABLE_QUESTION, null, "questionNo=?", new String[]{question.getQuestionNo()}, null, null, null);
        if (cursor.getCount() == 0)
            database.insert(MyDBHelper.TABLE_QUESTION, null, values);
        else
            database.update(MyDBHelper.TABLE_QUESTION, values, "questionNo=?", new String[]{question.getQuestionNo()});
        database.close();
    }

    public Question getQuestion(String id) {
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        dbhelper.createTable(database);
        Cursor cursor = database.query(MyDBHelper.TABLE_QUESTION, null, "questionNo=?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.move(0);
            return new Question(cursor.getString(0), cursor.getString(1), cursor.getString(2).split("-"));
        }
        return null;
    }

    // save the user
    public void saveUser(UserInfo userInfo) {
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        ContentValues values = userInfoToContentValues(userInfo);
        dbhelper.createTable(database);
        Cursor cursor = database.query(MyDBHelper.TABLE_USER, null, "username=?", new String[]{userInfo.getUsername()}, null, null, null);
        if (cursor.getCount() == 0)
            database.insert(MyDBHelper.TABLE_USER, null, values);
        else
            database.update(MyDBHelper.TABLE_USER, values, "username=?", new String[]{userInfo.getUsername()});
        database.close();
    }

    // get the user
    public UserInfo getUser(String id) {
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        dbhelper.createTable(database);
        Cursor cursor = database.query(MyDBHelper.TABLE_USER, null, "username=?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.move(0);
            List<Question> correct = new ArrayList<>();
            List<Question> wrong = new ArrayList<>();
            for(String s:cursor.getString(3).split("-")){
                correct.add(getQuestion(s));
            }
            for(String s:cursor.getString(4).split("-")){
                wrong.add(getQuestion(s));
            }
            return new UserInfo(cursor.getString(0),cursor.getString(1),cursor.getInt(2),correct,wrong);
        }
        return null;
    }

}
