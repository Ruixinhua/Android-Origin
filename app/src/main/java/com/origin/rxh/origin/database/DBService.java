package com.origin.rxh.origin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.origin.rxh.origin.general.Question;
import com.origin.rxh.origin.general.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBService {
    private MyDBHelper dbhelper;
    private SQLiteDatabase database;
    private ContentValues values;
    public DBService(Context context) {
        dbhelper = new MyDBHelper(context);
        dbhelper.createTable(dbhelper.getWritableDatabase());
    }

    public ContentValues userInfoToContentValues(UserInfo userInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", userInfo.getUsername());
        contentValues.put("password", userInfo.getPassword());
        contentValues.put("grades", userInfo.getGrade());
        Map<String,Question>  corrects = userInfo.getCorrectQue();
        String correct = "";
        for (Question q : corrects.values()) {
            correct += (q.getQuestionNo() + "-");
        }
        Map<String,Question>  wrong = userInfo.getWrongQue();
        String w = "";
        for (Question q : wrong.values()) {
            w += (q.getQuestionNo() + "-");
        }
        contentValues.put("correct", correct);
        contentValues.put("wrong", w);
        return contentValues;
    }

    public ContentValues questionToContentValues(Question question) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("questionNo", question.getQuestionNo());
        contentValues.put("question", question.getQuestion());
        String answer = "";
        String answers[] = question.getAnswer();
        for (int i = 0; i < answer.length(); i++) {
            answer += (answers[i] + "-");
        }
        contentValues.put("answer", answer);
        contentValues.put("correct", question.getCorrectAnswer());
        return contentValues;
    }

    public void saveQuestion(Question question) {
        database = dbhelper.getWritableDatabase();
        values = questionToContentValues(question);
        Cursor cursor = database.query(MyDBHelper.TABLE_QUESTION, null, "questionNo=?", new String[]{question.getQuestionNo()}, null, null, null);
        if (cursor.getCount() == 0)
            database.insert(MyDBHelper.TABLE_QUESTION, null, values);
        else
            database.update(MyDBHelper.TABLE_QUESTION, values, "questionNo=?", new String[]{question.getQuestionNo()});
        database.close();
    }

    public Question getQuestion(String id) {
        database = dbhelper.getWritableDatabase();
        Cursor cursor = database.query(MyDBHelper.TABLE_QUESTION, null, "questionNo=?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.move(0);
            return new Question(cursor.getString(0), cursor.getString(1), cursor.getString(2).split("-"),cursor.getInt(3));
        }
        database.close();
        return null;
    }

    // save the user
    public void saveUser(UserInfo userInfo) {
        database = dbhelper.getWritableDatabase();
        values = userInfoToContentValues(userInfo);
        Cursor cursor = database.query(MyDBHelper.TABLE_USER, null, "username=?", new String[]{userInfo.getUsername()}, null, null, null);
        if (cursor.getCount() == 0)
            database.insert(MyDBHelper.TABLE_USER, null, values);
        else
            database.update(MyDBHelper.TABLE_USER, values, "username=?", new String[]{userInfo.getUsername()});
        database.close();
    }

    public void saveUserTemp(String username){
        database = dbhelper.getWritableDatabase();
        values = new ContentValues();
        values.put("id", 1);
        values.put("username", username);
        Cursor cursor = database.query(MyDBHelper.TABLE_USER_NOW, null, "id=1", null, null, null, null);
        if (cursor.getCount() == 0)
            database.insert(MyDBHelper.TABLE_USER_NOW, null, values);
        else
            database.update(MyDBHelper.TABLE_USER_NOW, values, null,null);
        database.close();
    }

    public String getUserTemp(){
        database = dbhelper.getWritableDatabase();
        Cursor cursor = database.query(MyDBHelper.TABLE_USER_NOW, null, "id=?", new String[]{"1"}, null, null, null);
        if(cursor.getCount() == 0){
            database.close();
            return null;
        }else {
            cursor.moveToFirst();
            String username = cursor.getString(1);
            database.close();
            return username;

        }

    }

    public boolean deleteUserTemp(){
        database = dbhelper.getWritableDatabase();
        boolean isDelete = database.delete(MyDBHelper.TABLE_USER_NOW, "id=1",null) > 0;
        database.close();
        return isDelete;
    }

    // get the user
    public UserInfo getUser(String id) {
        database = dbhelper.getWritableDatabase();
        Cursor cursor = database.query(MyDBHelper.TABLE_USER, null, "username=?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.move(0);
            Map<String,Question> correct = new HashMap<>();
            Map<String,Question> wrong = new HashMap<>();
            for(String s:cursor.getString(3).split("-")){
                Question q = getQuestion(s);
                if(q != null){
                    correct.put(s,q);
                }
            }
            for(String s:cursor.getString(4).split("-")){
                Question q = getQuestion(s);
                if(q != null){
                    wrong.put(s,q);
                }
            }
            return new UserInfo(cursor.getString(0),cursor.getString(1),cursor.getInt(2),correct,wrong);
        }
        database.close();
        return null;
    }

}
